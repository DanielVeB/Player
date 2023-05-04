package com.kurosz.Controller;

import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPlaylistController implements Initializable {

//    private final PlayerController playerController;
    private final static Logger logger = LoggerFactory.getLogger(NewPlaylistController.class);


    public NewPlaylistController() {
//        this.playerController = playerController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("New playlist controller");
    }
}
