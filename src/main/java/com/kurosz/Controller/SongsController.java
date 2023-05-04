package com.kurosz.Controller;

import com.kurosz.Model.Song;
import javafx.collections.FXCollections;
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

public class SongsController implements Initializable {

    @FXML
    private TableView<Song> songsTable;

    @FXML
    private TableColumn<Song, String> title, artist, album, year, track;

    @FXML
    private TableColumn<Song, Integer> rate;

    private ObservableList<Song> songs;

//    private final PlayerController playerController;

    private final static Logger logger = LoggerFactory.getLogger(SongsController.class);

    public SongsController() {
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
