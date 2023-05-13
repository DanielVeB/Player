package com.kurosz.songs;

import com.kurosz.domain.Song;

import java.util.List;

public class SongsService {

    private List<Song> songs;

    public SongsService() {
        this.songs = List.of(
                new Song("Day Moon", "C:/Users/Daniel/Music/02 - Zoungla - Day Moon.mp3", "Zoungla"),
                new Song("Homage To The Dub Masters", "C:/Users/Daniel/Music/03 - Zoungla - Homage To The Dub Masters.mp3", "Zoungla"),
                new Song("Tune Out The World", "C:/Users/Daniel/Music/08 - Zoungla - Tune Out The World.mp3", "Zoungla"));

    }

    public List<Song> getSongs() {
        return songs;
    }
}
