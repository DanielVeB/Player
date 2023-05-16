package com.kurosz.player;

import com.jfoenix.controls.JFXSlider;
import com.kurosz.events.FxmlEventDispatcher;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.fxml.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


@SuppressWarnings("unused")
public class MusicRemoteController extends FxmlEventDispatcher<FxmlEvent> implements Initializable, FxmlEventHandler<FxmlEvent>{

    @FXML
    private AnchorPane musicRemotePane;

    @FXML
    private Label title, author;

    private String songPath;

    private JFXSlider musicSlider, volumeSlider;

    private final MusicPlayerService playerService;

    private volatile SongSelection selection;

    public MusicRemoteController(MusicPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void prev(ActionEvent actionEvent) {
        dispatchEvent(new PrevSongEvent(selection));
    }

    @FXML
    void next(ActionEvent actionEvent) {
        dispatchEvent(new NextSongEvent(selection));
    }

    @FXML
    void play(ActionEvent actionEvent) {
    }

    @FXML
    void autoreplay(ActionEvent actionEvent) {
    }

    @FXML
    void watchOnYoutube(ActionEvent actionEvent) {
    }

    @Override
    public void handle(FxmlEvent fxmlEvent) {
        if(fxmlEvent instanceof SongSelectedEvent){
            handle((SongSelectedEvent) fxmlEvent);
        }
    }

    private void handle(SongSelectedEvent event){
        title.setText(event.getTitle());
        author.setText(event.getAuthor());

        songPath = event.getPath();
        selection = event.getSelectedFrom();
    }
}
