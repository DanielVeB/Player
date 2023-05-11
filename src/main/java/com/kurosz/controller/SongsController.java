package com.kurosz.controller;

import com.kurosz.events.EventDispatcher;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.fxml.FxmlEvent;
import com.kurosz.events.fxml.SongSelectedEvent;
import com.kurosz.events.fxml.SongSelection;
import com.kurosz.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SongsController extends EventDispatcher<FxmlEvent> implements Initializable {

    @FXML
    private TableView<Song> songsTable;

    @FXML
    private TableColumn<Song, String> title, artist, album, year, track;

    @FXML
    private TableColumn<Song, Integer> rate;

    private ObservableList<Song> songs;

    private final static Logger logger = LoggerFactory.getLogger(SongsController.class);

    public SongsController() {
        logger.info("Songs Controller constructor");
        songs = FXCollections.observableArrayList();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Initialize Songs Controller");
        initTableColumns();

        songs.add(new Song.SongBuilder("Test").title("Test").artist("Test").
                album("Test").year("Test").rate(6).
                track("Test").text("Test").image("Test").build());
        songsTable.setItems(songs);


        songsTable.setOnMouseClicked(click -> {
            if(click.getButton()== MouseButton.SECONDARY){
//
            }else if(click.getClickCount()==2){
                dispatchEvent(new SongSelectedEvent("Songs Titile", "Songs Author","", SongSelection.SONGS));

            }
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
}
