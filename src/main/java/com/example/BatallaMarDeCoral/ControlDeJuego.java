package com.example.BatallaMarDeCoral;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class ControlDeJuego {
    ArrayList<Barco> barcos;
    String winner = "";
    DialogPane dialog;


    public synchronized ArrayList<Barco> getBarcos() {
        return barcos;
    }


    public ControlDeJuego() {
        barcos = new ArrayList<>();
        dialog = new DialogPane();

    }

    public synchronized void addBarco(Barco barco) {
        barcos.add(barco);
    }

    Timeline ganador;
    public void ganador() {
        ganador = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            int barcosRojos = 0;
            int barcosAzules = 0;
            for (Barco barco : barcos) {
                if (barco.getVida() > 0) {
                    if (barco.getEquipo().equals("Rojo")) {
                        barcosRojos++;
                    }
                    if (barco.getEquipo().equals("Azul")) {

                        barcosAzules++;
                    }
                }
            }
            if (barcosRojos == 0 && barcosAzules >= 1) {
                winner = "Azul";
                mostrarEquipoGanador(winner);
                ganador.stop();
            }
            if (barcosAzules == 0 && barcosRojos >= 1) {

                winner = "Rojo";
                mostrarEquipoGanador(winner);
                ganador.stop();
            }
        }));
        ganador.setCycleCount(Timeline.INDEFINITE);
        ganador.play();
    }

    public void mostrarEquipoGanador(String nombreEquipo) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la simulación");
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("imagenes/icono.jpeg").toString()));
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> Platform.runLater(() -> {
            dialog = alert.getDialogPane();
            dialog.getStyleClass().add("dialog");
            alert.setContentText("El equipo ganador es el: " + nombreEquipo);
            alert.showAndWait().ifPresent(response -> System.exit(0)
            );
        }));
        pause.play();
    }
}
