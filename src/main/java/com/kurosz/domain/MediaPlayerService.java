package com.kurosz.domain;

import com.kurosz.events.EventDispatcher;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.incoming.*;
import com.kurosz.events.outcoming.OutcomingEvent;
import com.kurosz.events.outcoming.SongCurrentTimeChangedEvent;
import com.kurosz.events.outcoming.SongStatusUpdatedEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerService extends EventDispatcher<OutcomingEvent> implements FxmlEventHandler<IncomingEvent> {


    private MediaPlayer mediaPlayer;

    private void play() {
        mediaPlayer.play();
        this.dispatchEvent(new SongStatusUpdatedEvent(SongStatus.PLAYING));
    }

    private void pause() {
        mediaPlayer.pause();
        this.dispatchEvent(new SongStatusUpdatedEvent(SongStatus.PAUSE));
    }

    private void applyPlayerBehaviour() {
        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) ->
                this.dispatchEvent(new SongCurrentTimeChangedEvent(t1.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100.0)));
    }

    @Override
    public void handle(IncomingEvent event) {
        if (event instanceof NewSongEvent) {
            if (mediaPlayer != null && !mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
                mediaPlayer.stop();
            }
            Media media = new Media(((NewSongEvent) event).getPath());
            mediaPlayer = new MediaPlayer(media);
            applyPlayerBehaviour();
            if (((NewSongEvent) event).isPlay()) {
                play();
            } else {
                this.dispatchEvent(new SongStatusUpdatedEvent(SongStatus.READY));
            }
        } else if (event instanceof SongTimeChangedEvent) {
            mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(((SongTimeChangedEvent) event).getSongDuration()));
        } else if (event instanceof VolumeChangedEvent) {
            mediaPlayer.setVolume(((VolumeChangedEvent) event).getVolume());
        } else if (event instanceof PlaySongEvent) {
            play();
        } else if (event instanceof PauseSongEvent) {
            pause();
        } else {
            throw new RuntimeException("Not supported event");
        }
    }

}
