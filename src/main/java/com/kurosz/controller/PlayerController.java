package com.kurosz.controller;


import com.jfoenix.controls.JFXListView;
import com.kurosz.model.*;
import com.kurosz.player.MusicRemoteController;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class PlayerController implements Initializable, Observer {
    private LinkedList<Song> songs = new LinkedList<>();

    @FXML
    private Button musicbutton, moviebutton, exit, displayAlbums, displayArtists, displaySongsButton,
            searchButton, addSongsButton, playlistCreateButton;

    @FXML
    private StackPane musicStackPane;

    @FXML
    private AnchorPane moviePane, musicPane, musicMenu, movieMenu,
            displayWithInfo, displayAlbumsArtists, displaySongs, mainMusicPane,
            createPlaylistPane, musicBar, newPlaylistPane, songsPane,musicRemotePane;

    @FXML
    private ListView<Button> playlisty;
    @FXML
    private ListView<DisplayArtistAlbum> AAView;
    @FXML
    private Label info, printSongInfo, numberOfSongs, additionalInfo;

    @FXML
    private JFXListView<Button> genresListView, moodsListView;

    @FXML
    private TableView<Song> tableOfSongs, songsOfPlaylist;
    @FXML
    private TableColumn<Song, String> title, artist, album, year, track,
            titleP, artistP, albumP, yearP, trackP;
    @FXML
    private TableColumn<Song, Integer> rate, rateP;
    @FXML
    private TextField searchField, playlistName;
    @FXML
    private TextArea playlistDescription;
    @FXML
    private ImageView playlistImage, imageView;
    @FXML
    private Pane songPane;
    @FXML
    private Button replaySong;


    private final MusicRemoteController musicRemoteController;
    private final SongsController songsController;
    private final NewPlaylistController newPlaylistController;

    private final static Logger logger = LoggerFactory.getLogger(PlayerController.class);


    public PlayerController(MusicRemoteController musicRemoteController,
                            NewPlaylistController newPlaylistController,
                            SongsController songsController) {
        logger.info("Initialize main player controller");
        this.newPlaylistController = newPlaylistController;
        this.musicRemoteController = musicRemoteController;
        this.songsController = songsController;

        this.songsController.addHandler(musicRemoteController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader nestedLoader = new FXMLLoader(getClass().getResource("/musicRemote.fxml"));
            nestedLoader.setController(musicRemoteController);
            AnchorPane m = nestedLoader.load();
            musicRemotePane.getChildren().add(m);

        } catch (IOException e) {
            e.printStackTrace();
        }


        musicBar.setVisible(true);
        mp3player = new Mp3player();
        mp3player.register(this);

    }

    @FXML
    private void search() {
        String regex = searchField.getText();
    }

    Mp3player mp3player = null;


    public void displaySongsPane(){
        if(songsPane.getChildren().size() == 0){
            try {
                FXMLLoader nestedLoader = new FXMLLoader(getClass().getResource("/songs.fxml"));
                nestedLoader.setController(songsController);
                AnchorPane m = nestedLoader.load();
                songsPane.getChildren().add(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayPlaylistPane(){
        if(newPlaylistPane.getChildren().size() == 0){
            try {
                FXMLLoader nestedLoader = new FXMLLoader(getClass().getResource("/newPlaylist.fxml"));
                nestedLoader.setController(newPlaylistController);
                AnchorPane m = nestedLoader.load();
                newPlaylistPane.getChildren().add(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleButton(ActionEvent event) {

        if (event.getTarget() == musicbutton) {
            fadeOut(musicPane, moviePane);
            movieMenu.setVisible(false);
            musicMenu.setVisible(true);
        } else if (event.getTarget() == moviebutton) {
            fadeOut(moviePane, musicPane);
            movieMenu.setVisible(true);
            musicMenu.setVisible(false);
        } else if (event.getTarget() == exit) {
            Platform.exit();
            System.exit(0);
        } else if (event.getTarget() == displaySongsButton) {
            displaySongsPane();
            switchPane(songsPane);
        }
    }

    private void switchPane(AnchorPane pane) {
        int index = musicStackPane.getChildren().indexOf(pane);
        if (index >= 0) {
            IntStream.range(0, musicStackPane.getChildren().size())
                    .filter(i -> i != index)
                    .forEach(i -> musicStackPane.getChildren().get(i).setVisible(false));
            musicStackPane.getChildren().get(index).setVisible(true);
            musicStackPane.getChildren().get(index).toFront();
        }
    }

    @FXML
    private void createPlaylist() {
        displayPlaylistPane();
        switchPane(newPlaylistPane);
    }


    @FXML
    private void loadPlaylistImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpeg", "*.jpg"));
        File file = fileChooser.showOpenDialog(new Stage());
        String path = null;
        path = file.getAbsolutePath();
        FileInputStream inputstream = null;
        try {
            System.out.println(path);
            inputstream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
        }
        Image iv = new Image(inputstream);

        playlistImage.setImage(iv);

    }

    public Stage showEditSongWindow(Song s, String fxml) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        fxml
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditSongController controller = loader.getController();
        controller.initData(s);
        stage.show();

        return stage;
    }


    private void editSong(Song song) {
        showEditSongWindow(song, "/editSong.fxml");
    }



    @FXML
    private void addSongs() throws InvalidDataException, UnsupportedTagException, IOException {
        List<File> files = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add new songs");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.3gp", "*.flac"));
        files = fileChooser.showOpenMultipleDialog(new Stage());
        if (files != null) {
            JDBCConnector.addSongs(files);
        }

    }

    private void fadeIN(AnchorPane node, AnchorPane node1) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(200));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

    }

    private void fadeOut(AnchorPane node, AnchorPane node1) {
        System.out.println(node.getOpacity());
        System.out.println(node1.getOpacity());
        if (node1.getOpacity() != 0) {
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(200));
            fadeTransition.setNode(node1);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);

            fadeTransition.setOnFinished((ActionEvent event) -> {
                fadeIN(node, node1);
            });
            fadeTransition.play();
        }
    }

    @Override
    public void update(int name) {
        tableOfSongs.getSelectionModel().select(name);
        tableOfSongs.scrollTo(name);
    }


}


