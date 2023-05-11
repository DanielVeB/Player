package com.kurosz;

import com.kurosz.controller.NewPlaylistController;
import com.kurosz.controller.PlayerController;
import com.kurosz.controller.SongsController;
import com.kurosz.model.JDBCConnector;
import com.kurosz.player.MusicRemoteController;
import com.kurosz.player.PlayerConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Player extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(final Stage primaryStage) throws IOException {
        PlayerConfig playerConfig = new PlayerConfig();
        MusicRemoteController musicRemoteController = playerConfig.musicRemoteController();
        NewPlaylistController newPlaylistController = new NewPlaylistController();
        SongsController songsController = new SongsController();


        primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/player.fxml"));
        mainLoader.setController(new PlayerController(musicRemoteController, newPlaylistController, songsController));

        Parent root = mainLoader.load();

        prepareScene(root,primaryStage);
    }

    private void prepareScene(final Parent root, final Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        try {
            JDBCConnector.connect();
            System.out.println("Connected");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBCConnector.disconnect();
    }
}