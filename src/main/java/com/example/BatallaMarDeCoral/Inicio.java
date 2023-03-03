package com.example.BatallaMarDeCoral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Inicio extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inicio.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500,700 );
        stage.setTitle("Batalla!");

        stage.setX(0);
        stage.setY(0);

        stage.getIcons().add(new Image(this.getClass().getResource("imagenes/icono.jpeg").toString()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}