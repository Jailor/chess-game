package com.andrei;

import com.andrei.game.ChessBoard;
import com.andrei.game.Images;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    public static final boolean TEST_SETUP = false;
    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("app_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        window.setTitle("ChessGame");
        window.getIcons().add(new Image(Images.ICON));
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}