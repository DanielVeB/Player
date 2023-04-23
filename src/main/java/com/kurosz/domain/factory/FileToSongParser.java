package com.kurosz.domain.factory;

import com.kurosz.domain.Song;

import java.io.File;

interface FileToSongParser {

    public Song parse(File file);
}
