package com.kurosz.events.outcoming;

public class SongCurrentTimeChangedEvent extends OutcomingEvent {
    private final double songPercentageElapsed;

    public SongCurrentTimeChangedEvent(double songPercentageElapsed) {
        this.songPercentageElapsed = songPercentageElapsed;
    }

    public double getSongPercentageElapsed() {
        return songPercentageElapsed;
    }


}
