package com.example.BatallaMarDeCoral;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
public class PestanaRojo extends Stage {
    ControlDeJuego control;
    ProgressBar destructor;
    ProgressBar acorazado;
    ProgressBar lancha;
    ProgressBar submarino;
    Label txtVidaDestructor;
    Label txtVidaAcorazado;
    Label txtVidaLancha;
    Label txtVidaSubmarino;
    Label txtBarcosRestantes;
    public PestanaRojo() {
        cargarInterfaz();
        destructor.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0d) {
                destructor.getStyleClass().add("green-progress-bar");
            } else if (newValue.doubleValue() <= 0.99d && newValue.doubleValue() >= 0.50d) {
                destructor.getStyleClass().add("green-progress-bar");
            } else {
                destructor.getStyleClass().add("green-progress-bar");
            }
        });
        acorazado.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0d) {
                acorazado.getStyleClass().add("green-progress-bar");
            } else if (newValue.doubleValue() <= 0.99d && newValue.doubleValue() >= 0.50d) {
                acorazado.getStyleClass().add("green-progress-barr");
            } else {
                acorazado.getStyleClass().add("green-progress-bar");
            }
        });
        submarino.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0d) {
                submarino.getStyleClass().add("green-progress-bar");
            } else if (newValue.doubleValue() <= 0.99d && newValue.doubleValue() >= 0.50d) {
                submarino.getStyleClass().add("green-progress-bar");
            } else {
                submarino.getStyleClass().add("green-progress-bar");
            }
        });
        lancha.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0d) {
                lancha.getStyleClass().add("green-progress-bar");
            } else if (newValue.doubleValue() <= 0.99d && newValue.doubleValue() >= 0.50d) {
                lancha.getStyleClass().add("green-progress-bar");
            } else {
                lancha.getStyleClass().add("green-progress-bar");
            }
        });
        this.destructor.setProgress(1);
        this.lancha.setProgress(1);
        this.submarino.setProgress(1);
        this.acorazado.setProgress(1);
        Timeline moverse = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            cambiarBarcosRestantes();
            for (Barco barco : this.control.getBarcos()) {
                if (barco.getNombre().equals("destructor") && barco.getEquipo().equals("Rojo")) {
                    txtVidaDestructor.setText(String.valueOf(barco.getVida()));
                    destructor.setProgress((barco.getVida() / 80f));
                }
                if (barco.getNombre().equals("acorazado") && barco.getEquipo().equals("Rojo")) {
                    txtVidaAcorazado.setText(String.valueOf(barco.getVida()));
                    acorazado.setProgress((barco.getVida() / 120f));
                }
                if (barco.getNombre().equals("lancha") && barco.getEquipo().equals("Rojo")) {
                    txtVidaLancha.setText(String.valueOf(barco.getVida()));
                    lancha.setProgress((barco.getVida() / 10f));
                }
                if (barco.getNombre().equals("submarino") && barco.getEquipo().equals("Rojo")) {
                    txtVidaSubmarino.setText(String.valueOf(barco.getVida()));
                    submarino.setProgress((barco.getVida() / 30f));
                }
            }
        }));
        moverse.setCycleCount(Timeline.INDEFINITE);
        moverse.play();
    }
    public void cambiarBarcosRestantes() {

        Platform.runLater(() -> {
            int barcos = 0;
            if (Integer.parseInt(txtVidaAcorazado.getText()) > 0) {
                barcos++;
            }
            if (Integer.parseInt(txtVidaDestructor.getText()) > 0) {
                barcos++;
            }
            if (Integer.parseInt(txtVidaLancha.getText()) > 0) {
                barcos++;
            }
            if (Integer.parseInt(txtVidaSubmarino.getText()) > 0) {
                barcos++;
            }
            txtBarcosRestantes.setText(String.valueOf(barcos));
        });
    }
    public synchronized void getControl(ControlDeJuego control) {
        this.control = control;
    }

    private void cargarInterfaz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("barcosRojos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 255, 768);

            destructor = (ProgressBar) loader.getNamespace().get("pbVidaDestructor");
            txtVidaDestructor = (Label) loader.getNamespace().get("vidaDestructor");
            txtVidaDestructor.setText("80");

            acorazado = (ProgressBar) loader.getNamespace().get("pbVidaAcorazado");
            txtVidaAcorazado = (Label) loader.getNamespace().get("vidaAcorazado");
            txtVidaAcorazado.setText("120");

            lancha = (ProgressBar) loader.getNamespace().get("pbVidaLancha");

            txtVidaLancha = (Label) loader.getNamespace().get("vidaLancha");
            txtVidaLancha.setText("10");

            submarino = (ProgressBar) loader.getNamespace().get("pbVidaSubmarino");
            ;
            txtVidaSubmarino = (Label) loader.getNamespace().get("vidaSubmarino");
            txtVidaSubmarino.setText("30");

            destructor = (ProgressBar) loader.getNamespace().get("pbVidaDestructor");
            txtVidaDestructor = (Label) loader.getNamespace().get("vidaDestructor");
            txtVidaDestructor.setText("80");

            txtBarcosRestantes = (Label) loader.getNamespace().get("barcosrestantes");

            setScene(scene);
            setX(0);
            setY(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

