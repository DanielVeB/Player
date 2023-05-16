package com.kurosz.controller;

import com.kurosz.events.FxmlEventDispatcher;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.fxml.*;
import com.kurosz.model.SongDto;
import com.kurosz.songs.SongsService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SongsController extends FxmlEventDispatcher<FxmlEvent> implements Initializable, FxmlEventHandler<FxmlEvent> {

    private final SongsService songsService;
    @FXML
    private TableView<SongDto> songsTable;

    @FXML
    private TableColumn<SongDto, String> title, artist, album, year, track;

    @FXML
    private TableColumn<SongDto, Integer> rate;

    private ObservableList<SongDto> songs;
    private SongDto selectedSong;

    private final static Logger logger = LoggerFactory.getLogger(SongsController.class);

    public SongsController(SongsService songsService) {
        this.songsService = songsService;
        logger.info("Songs Controller constructor");
        songs = FXCollections.observableArrayList();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Initialize Songs Controller");
        initTableColumns();

        songsService.getSongs().forEach( it->
                songs.add(it.toDto())
        );
        songsTable.setItems(songs);

        songsTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super SongDto>) change -> {
            selectedSong = change.getList().get(0);
            dispatchEvent(new SongSelectedEvent(selectedSong.getTitle(), selectedSong.getArtist(),selectedSong.getPath(), SongSelection.SONGS));
        });

    }

    private void initTableColumns(){
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<>("album"));
        track.setCellValueFactory(new PropertyValueFactory<>("track"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
    }

    @Override
    public void handle(FxmlEvent fxmlEvent) {
        if(fxmlEvent instanceof NextSongEvent && SongSelection.SONGS.equals(((NextSongEvent) fxmlEvent).getSongSelection())){
            nextSong();
        }else if(fxmlEvent instanceof PrevSongEvent && SongSelection.SONGS.equals(((PrevSongEvent) fxmlEvent).getSongSelection())){
            prevSong();
        }
    }

    private void nextSong(){
        var selectionModel = songsTable.getSelectionModel();
        if (selectionModel.getSelectedIndex() < songsTable.getItems().size() - 1) {
            selectionModel.selectNext();
        } else {
            selectionModel.selectFirst();
        }
        selectedSong = selectionModel.getSelectedItem();
        dispatchEvent(new SongSelectedEvent(selectedSong.getTitle(), selectedSong.getArtist(),selectedSong.getPath(), SongSelection.SONGS));
    }

    private void prevSong(){
        var selectionModel = songsTable.getSelectionModel();
        if (selectionModel.getSelectedIndex() == 0) {
            selectionModel.selectLast();
        } else {
            selectionModel.selectPrevious();
        }
        selectedSong = selectionModel.getSelectedItem();
        dispatchEvent(new SongSelectedEvent(selectedSong.getTitle(), selectedSong.getArtist(),selectedSong.getPath(), SongSelection.SONGS));
    }
}
