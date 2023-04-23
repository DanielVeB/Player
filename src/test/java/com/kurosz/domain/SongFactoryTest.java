package com.kurosz.domain;

import com.kurosz.domain.factory.SongFactory;
import org.junit.jupiter.api.Test;

class SongFactoryTest {


    @Test
    void shouldReturnSongObjectFromMp3File(){
        Song song = SongFactory.createSong("C:\\Users\\Daniel\\projects\\Player\\src\\test\\resources\\test.mp3");

    }

}