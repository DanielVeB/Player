package com.kurosz.events.incoming;

public class SongTimeChangedEvent extends IncomingEvent{

    private final double songDuration;

    public SongTimeChangedEvent(double songDuration) {
        this.songDuration = songDuration;
    }

    public double getSongDuration() {
        return songDuration;
    }
}
