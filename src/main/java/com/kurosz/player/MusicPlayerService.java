package com.kurosz.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MusicPlayerService {

    private final static Logger logger = LoggerFactory.getLogger(MusicPlayerService.class);
    public MusicPlayerService(){
        logger.debug("Init music player service");
    }

    public void play(){

    }

    public void stop(){

    }
}
