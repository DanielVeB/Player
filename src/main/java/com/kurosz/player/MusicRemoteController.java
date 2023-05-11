package com.kurosz.player;

import com.jfoenix.controls.JFXSlider;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.fxml.FxmlEvent;
import com.kurosz.events.fxml.SongSelectedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


@SuppressWarnings("unused")
public class MusicRemoteController implements Initializable, FxmlEventHandler<FxmlEvent> {

    @FXML
    private AnchorPane musicRemotePane;

    @FXML
    private Label title, author;

    private String songPath;

    private JFXSlider musicSlider, volumeSlider;

    private final MusicPlayerService playerService;

    public MusicRemoteController(MusicPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void prev(ActionEvent actionEvent) {
    }

    @FXML
    void next(ActionEvent actionEvent) {
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
    }
}
