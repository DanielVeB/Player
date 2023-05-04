package com.kurosz.controller;

import com.kurosz.model.Genres;
import com.kurosz.model.JDBCConnector;
import com.kurosz.model.Moods;
import com.kurosz.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class EditSongController implements Initializable {
    @FXML
    private TextField titleSong, artistSong, albumSong;

    @FXML
    private ListView<String> genresList, moodsList;
    @FXML
    private ImageView imageSong;
//    @FXML
//    private JFXTextArea lirycsSong;
    @FXML
    private Button saveButton;
    private String path = "";
    private Song s;



    private static final Logger logger = LoggerFactory.getLogger(EditSongController.class);


    void initData(Song s) {
        logger.info("INIT DATA IN EDIT SONG CONTROLLER");
        this.s = s;
        path = s.getPath();
        List<String> genreList = new LinkedList<String>(Genres.genres.keySet());
        List<String> moodList = new LinkedList<String>(Moods.moods.keySet());
        if( s.getTitle() != null) titleSong.setText(s.getTitle());
        if( s.getArtist() != null) artistSong.setText(s.getArtist());
        if( s.getAlbum() != null) albumSong.setText(s.getAlbum());
//        if( s.getText() != null) lirycsSong.setText(s.getText());

        String[] genres = JDBCConnector.returnGenreMood(s.getPath(), "genre");
        if (genres != null) {
            for (String genre : genres) {
                int index = genreList.indexOf(genre);
                genresList.getSelectionModel().select(index);
            }
        }
        String[] moods = JDBCConnector.returnGenreMood(s.getPath(), "moods");
        if (moods != null) {
            for (String mood : moods) {
                int index = moodList.indexOf(mood);
                moodsList.getSelectionModel().select(index);
            }
        }
        logger.info("Apply image to song");
        Image image = new Image("file:" + s.getImage());
        imageSong.setImage(image);
        imageSong.setStyle("-fx-cursor: hand");
        logger.info("Apply setOnMouseClicked logic");

        imageSong.setOnMouseClicked((MouseEvent event) -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpeg", "*.jpg"));
                File file = fileChooser.showOpenDialog(new Stage());
                String path = file.getAbsolutePath();
                FileInputStream inputstream = new FileInputStream(path);
                Image iv = new Image(inputstream);
                s.setImage(path);
                imageSong.setImage(iv);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });

        logger.info("Finish initData");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Initialize EditSongController");
        for (String name : Moods.moods.keySet()) {
            moodsList.getItems().add(name);
        }
        for (String name : Genres.genres.keySet()) {
            genresList.getItems().add(name);
        }

//        moodsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        genresList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @FXML
    private void save() {
        String[] genres = genresList.getSelectionModel().getSelectedItems().toArray(new String[0]);
        String[] moods = moodsList.getSelectionModel().getSelectedItems().toArray(new String[0]);
        JDBCConnector.updateSong(titleSong.getText(), artistSong.getText(), albumSong.getText(), genres, moods,
                "", s.getImage(), path);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void find_lyrics() {
//        s.findLyrics("");
    }
}
