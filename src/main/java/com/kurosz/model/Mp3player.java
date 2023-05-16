package com.kurosz.model;

import com.jfoenix.controls.JFXSlider;
import com.kurosz.domain.MediaPlayerService;
import com.kurosz.domain.SongStatus;
import com.kurosz.events.FxmlEventDispatcher;
import com.kurosz.events.FxmlEventHandler;
import com.kurosz.events.incoming.*;
import com.kurosz.events.outcoming.OutcomingEvent;
import com.kurosz.events.outcoming.SongCurrentTimeChangedEvent;
import com.kurosz.events.outcoming.SongStatusUpdatedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;


public class Mp3player extends FxmlEventDispatcher<IncomingEvent> implements Subject, FxmlEventHandler<OutcomingEvent> {
    @FXML
    AnchorPane anchorPane;

    private Label titleAndArtist;
    private ImageView imageView;
    private JFXSlider musicSlider, volumeSlider;
    MediaPlayerService playerService;

    private LinkedList<SongDto> songs = new LinkedList<>();
    private int index;
    private int length;
    private boolean autoreplay = false;

    private SongStatus currentStatus;

    private static final Logger logger = LoggerFactory.getLogger(Mp3player.class);

    public Mp3player() {
        System.out.println("Init Mp3 player");
        logger.info("Init Mp3 player - logger");

        playerService = new MediaPlayerService();
        playerService.addHandler(this);

        this.addHandler(playerService);
    }

    public void loadBar(AnchorPane pane) {
        logger.info("Load bar");

        anchorPane = pane;
        titleAndArtist = new Label();
        titleAndArtist.getStyleClass().add("titleAndArtist");
        AnchorPane.setTopAnchor(titleAndArtist, 25.0);
        AnchorPane.setLeftAnchor(titleAndArtist, 150.0);
        musicSlider = new JFXSlider();

        musicSlider.setMinWidth(800);
        AnchorPane.setTopAnchor(musicSlider, 100.);
        AnchorPane.setLeftAnchor(musicSlider, 20.);

        musicSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (musicSlider.isValueChanging()) {
                logger.info("Music slider changed");
                dispatchEvent(new SongTimeChangedEvent(newValue.doubleValue() / 100.));
            }
            if (!musicSlider.isValueChanging()) {
                if (Math.abs(newValue.doubleValue() / 100. - oldValue.doubleValue() / 100.) > 0.005) {
                    logger.info("Music slider changed");
                    dispatchEvent(new SongTimeChangedEvent(newValue.doubleValue() / 100.));
                }
            }
        });

        volumeSlider = new JFXSlider(0, 100, 10);
        volumeSlider.setMajorTickUnit(10);
        volumeSlider.setMinorTickCount(0);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.setMinWidth(100);
        AnchorPane.setTopAnchor(volumeSlider, 100.);
        AnchorPane.setLeftAnchor(volumeSlider, 840.);

        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (volumeSlider.isValueChanging()) {
                logger.info("Volume slider changed");
                dispatchEvent(new VolumeChangedEvent(newValue.doubleValue() / 100.));
            }
            if (!volumeSlider.isValueChanging()) {
                if (Math.abs(newValue.doubleValue() / 100. - oldValue.doubleValue() / 100.) > 0.005) {
                    logger.info("Volume slider changed v2");
                    dispatchEvent(new VolumeChangedEvent(newValue.doubleValue() / 100.));
                }
            }
        });
        anchorPane.getChildren().addAll(titleAndArtist, musicSlider, volumeSlider);

    }
    public void loadSongs(LinkedList<SongDto> songs) {
        this.songs = songs;
        length = songs.size();
    }

    public boolean setCurrentSong(int i) throws NullPointerException {
        if (i < 0) i = songs.size() - 1;
        if (i >= songs.size()) i = 0;
//        if (player != null) player.stop();
        this.index = i;
        titleAndArtist.setText(songs.get(index).getTitle() + "\n" + songs.get(index).getArtist());
        Image image = new Image("file:" + songs.get(index).getImage());
        imageView = new ImageView(image);
        imageView.setFitWidth(64.);
        imageView.setFitHeight(64.);
        AnchorPane.setTopAnchor(imageView, 25.0);
        AnchorPane.setLeftAnchor(imageView, 50.0);
        anchorPane.getChildren().add(imageView);
        File file = new File(songs.get(index).getPath());
        boolean exists = file.exists();
        if (!exists) {
            if (length == 0) throw new NullPointerException();
            length--;
            next();
            return false;
        }
        String path = file.toURI().toASCIIString();
        this.dispatchEvent(new NewSongEvent(path,true));
        return true;
    }
    public void next() {
        setCurrentSong(++index);
    }

    public void prev() {
        setCurrentSong(--index);
    }

    public boolean setAutoreplay() {
        if (autoreplay) autoreplay = false;
        else autoreplay = true;
        return autoreplay;
    }

    public void play_pause() {
//        switch (currentStatus){
//            case PLAYING -> {
//                dispatchEvent(new PauseSongEvent());
//                break;
//            }
//            case PAUSE -> dispatchEvent(new PlaySongEvent());
//        }

        switch (currentStatus){
            case PLAYING:
                dispatchEvent(new PauseSongEvent());
                break;
            case PAUSE:
                dispatchEvent(new PlaySongEvent());
                break;
        }

    }

    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void register(Observer o) {
        logger.info("Register  new observer {}", o.toString());
        observers.add(o);
    }

    public void watchYoutube() {
//        songs.get(index).watchOnYoutube();
//        player.pause();
    }

    @Override
    public void unregister(Observer o) {
        logger.info("Unregister observer {}", o.toString());
        observers.remove(o);
    }

    @Override
    public void notifyAllObservers(int s) {
        for (Observer observer : observers) {
            observer.update(s);
        }
    }

    @Override
    public void handle(OutcomingEvent event) {
        if (event instanceof SongCurrentTimeChangedEvent) {
            handle((SongCurrentTimeChangedEvent) event);
        } else if (event instanceof SongStatusUpdatedEvent) {
            handle((SongStatusUpdatedEvent) event);
        }
    }

    private void handle(SongCurrentTimeChangedEvent event) {
        musicSlider.setValue(event.getSongPercentageElapsed());
    }

    private void handle(SongStatusUpdatedEvent event) {
        currentStatus = event.getStatus();
    }
}
