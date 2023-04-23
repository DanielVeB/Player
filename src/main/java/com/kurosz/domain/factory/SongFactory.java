package com.kurosz.domain.factory;

import com.kurosz.domain.Song;
import com.kurosz.domain.SongFormat;

import java.io.File;
import java.io.IOException;

public class SongFactory {

    public static Song createSong(String filePath) {
        File file = new File(filePath);
        SongFormat format = getFormat(file);

        return new Song();
    }

    private static SongFormat getFormat(File file) {
        String contentType;
        try {
            contentType = file.toURI().toURL().openConnection().getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SongFormat.fromContentType(contentType);
    }
}
