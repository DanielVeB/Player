package com.kurosz.player;


public class PlayerConfig {

    public MusicPlayerService musicPlayerService(){
        return new MusicPlayerService();
    }

    public MusicRemoteController musicRemoteController(){
        return new MusicRemoteController(musicPlayerService());
    }

}
