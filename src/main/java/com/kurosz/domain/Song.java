package com.kurosz.domain;

import com.kurosz.model.SongDto;

import java.io.File;

public class Song {

    private String title;
    private String path;
    private SongFormat format;
    private SongMetadata songMetadata;

    class SongMetadata {
        private String artist;
        private String album;
        private String year;
        private String text;
    }

    public Song(String title, String path, String artist){
        this.title = title;
        this.songMetadata = new SongMetadata();
        songMetadata.artist = artist;
        this.path = path;
    }




    String getText() {
        return "";
    }

    static Song fromFile(File file, String title) {
        return new Song("","","");
    }

    static Song fromFile(File file) {
        return fromFile(file, null);
    }


    public SongDto toDto(){
        return new SongDto.SongBuilder(path).title(title).artist(songMetadata.artist).build();
    }
}
