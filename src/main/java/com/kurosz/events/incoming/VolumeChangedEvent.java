package com.kurosz.events.incoming;

public class VolumeChangedEvent extends IncomingEvent{

    private final double volume;

    public VolumeChangedEvent(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }
}
