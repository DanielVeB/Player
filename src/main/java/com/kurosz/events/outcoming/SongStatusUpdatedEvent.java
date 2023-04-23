package com.kurosz.events.outcoming;

import com.kurosz.domain.SongStatus;

public class SongStatusUpdatedEvent extends OutcomingEvent{

    private final SongStatus status;

    public SongStatusUpdatedEvent(SongStatus status) {
        this.status = status;
    }
    public SongStatus getStatus(){
        return this.status;
    }
}
