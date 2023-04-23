package com.kurosz.events.incoming;

public class NewSongEvent extends IncomingEvent{

    private final String path;
    private final boolean play;

    public NewSongEvent(String path, boolean play) {
        this.path = path;
        this.play = play;
    }

    public String getPath() {
        return path;
    }

    public boolean isPlay() {
        return play;
    }
}
