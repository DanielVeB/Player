package com.kurosz.player;


import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class PlayerConfig {

    public MusicPlayerService musicPlayerService(){
        return new MusicPlayerService();
    }

    public MusicRemoteController musicRemoteController(){
        return new MusicRemoteController(musicPlayerService());
    }

}
