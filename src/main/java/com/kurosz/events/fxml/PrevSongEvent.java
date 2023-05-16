package com.kurosz.events.fxml;

import lombok.Data;

@Data
public class PrevSongEvent extends FxmlEvent{
    private final SongSelection songSelection;
}
