package com.kurosz.events.fxml;

import lombok.Data;

@Data
public class NextSongEvent extends FxmlEvent{
    private final SongSelection songSelection;
}
