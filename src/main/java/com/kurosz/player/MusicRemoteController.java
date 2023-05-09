package com.kurosz.player;

import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

//@Component
public class MusicRemoteController implements Initializable {

    @FXML
    private AnchorPane musicRemotePane;

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
}
