package com.kurosz.domain;

import java.io.File;

public class Song {

    private String title;
    private String path;
    private SongFormat format;
    private SongMetadata songMetadata;


    String getText() {
        return "";
    }

    static Song fromFile(File file, String title) {
        return new Song();
    }

    static Song fromFile(File file) {
        return fromFile(file, null);
    }


}
