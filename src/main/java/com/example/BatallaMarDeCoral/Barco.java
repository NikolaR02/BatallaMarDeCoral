package com.example.BatallaMarDeCoral;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import java.util.Objects;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;

public class Barco {
    private String nombre;
    private int vida;
    private int velocidad;
    private int sonar;
    private int potenciaFuego;
    private ImageView imagenDelBarco;
    private double direccion;

    private Timeline moverse;
    private ArrayList<Barco> barcos;
    private int recagarDisparo;
    private String equipo;
    private boolean modoDisparo;
    private long tiempoDeRecarga;
    private AnchorPane fondo;
    public Barco(String nombre, String equipo, ImageView imagenBarco, ArrayList<Barco> barcos, AnchorPane ventana) {
        this.nombre = nombre;
        this.imagenDelBarco = imagenBarco;
        this.barcos = barcos;
        this.fondo = ventana;
        this.modoDisparo = false;
        this.direccion = 45;

        this.equipo = equipo;
        this.recagarDisparo = 0;

        if (nombre.contains("lancha")) {
            imagenBarco.setFitHeight(28);
            imagenBarco.setFitWidth(80);
            velocidad = 10;
            potenciaFuego = 20;
            vida = 10;
            sonar = 75;
            tiempoDeRecarga = 2000;

        } else if (nombre.contains("acorazado")) {
            imagenBarco.setFitHeight(90);
            imagenBarco.setFitWidth(140);
            velocidad = 3;
            potenciaFuego = 80;
            vida = 120;
            sonar = 200;
            tiempoDeRecarga = 8000;

        } else if (nombre.contains("submarino")) {
            imagenBarco.setFitHeight(30);
            imagenBarco.setFitWidth(90);
            velocidad = 2;
            potenciaFuego = 60;
            vida = 30;
            sonar = 130;
            tiempoDeRecarga = 4000;
        } else if (nombre.contains("destructor")) {

            imagenBarco.setFitHeight(50);
            imagenBarco.setFitWidth(120);
            velocidad = 5;
            potenciaFuego = 50;
            vida = 80;
            sonar = 155;
            tiempoDeRecarga = 6000;
        }
        moverse = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            acabarJuego();
            if (!modoDisparo) {
                detectarBarcosCercanos();
                detectarParedes();
                moverBarco();
            }
            barcoMuerto();

        }));
        moverse.setCycleCount(Timeline.INDEFINITE);
        moverse.play();
    }
    public synchronized void setModoDisparo(boolean modoDisparo) {
        this.modoDisparo = modoDisparo;
    }
    public synchronized void pararBarcos(Barco barco1, Barco barco2) {
        barco1.setModoDisparo(true);
        barco2.setModoDisparo(true);
    }
    public String getEquipo() {
        return equipo;
    }
    public synchronized void acabarJuego() {
        int barcoRojo = 0;
        int barcoAzul = 0;

        for (Barco barco : barcos) {
            if (barco.getVida() > 0) {
                if (barco.getEquipo().equals("Rojo")) {
                    barcoRojo++;
                }
                if (barco.getEquipo().equals("Azul")) {
                    barcoAzul++;
                }
            }
        }
        if (barcoRojo >= 1 && barcoAzul == 0) {
            moverse.stop();
        }
        if (barcoAzul >= 1 && barcoRojo == 0) {
            moverse.stop();
        }
    }
    private long tiempoUltimoDisparo = 0;
    public synchronized boolean recargando() {
        long tiempoActual = System.currentTimeMillis();
        return tiempoActual < tiempoUltimoDisparo + tiempoDeRecarga;
    }
    public void animacionDisparo(Barco barco1, Barco barco2) {
        ImageView bola = new ImageView((new Image((getClass().getResourceAsStream("imagenes/bala.png")))));
        bola.setFitWidth(20);
        bola.setFitHeight(15);
        fondo.getChildren().add(bola);
        double barco1X = barco1.getImagenDelBarco().getBoundsInParent().getMinX() + barco1.getImagenDelBarco().getBoundsInParent().getWidth() / 2;
        double barco1Y = barco1.getImagenDelBarco().getBoundsInParent().getMinY() + barco1.getImagenDelBarco().getBoundsInParent().getHeight() / 2;
        if (barco1.getNombre().equals("lancha") || barco1.getNombre().equals("destructor")) {
            barco1X -= 6;
            barco1Y -= 6;
        }
        double barco2X = barco2.getImagenDelBarco().getBoundsInParent().getMinX() + barco2.getImagenDelBarco().getBoundsInParent().getWidth() / 2;
        double barco2Y = barco2.getImagenDelBarco().getBoundsInParent().getMinY() + barco2.getImagenDelBarco().getBoundsInParent().getHeight() / 2;
        if (barco2.getNombre().equals("lancha") || barco2.getNombre().equals("destructor")) {
            barco2X -= 6;
            barco2Y -= 6;
        }
        Timeline animacion = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bola.xProperty(), barco1X),
                        new KeyValue(bola.yProperty(), barco1Y)),
                new KeyFrame(Duration.seconds(2), new KeyValue(bola.xProperty(), barco2X),
                        new KeyValue(bola.yProperty(), barco2Y))
        );
        animacion.setOnFinished(e -> {
            int ultimoIndex = fondo.getChildren().size() - 1;
            fondo.getChildren().remove(ultimoIndex);
            barco1.setModoDisparo(false);
            barco2.setModoDisparo(false);
            cambiarImagenMuerte(barco2);
        });
        animacion.play();
    }
    public synchronized void detectarBarcosCercanos() { //----- pr no estr
        if (recargando() || getVida() <= 0) {
            return;
        }
        for (Barco barco : barcos) {
            if (barco == this) {
                continue;
            }
            double distancia = Math.sqrt(Math.pow(barco.getImagenDelBarco().getLayoutX() - this.getImagenDelBarco().getLayoutX(), 2) +
                    Math.pow(barco.getImagenDelBarco().getLayoutY() - this.getImagenDelBarco().getLayoutY(), 2));
            if (barco.getNombre().contains("submarino")) {
                distancia -= 50;
            }
            if (distancia < getSonar() && !Objects.equals(this.getEquipo(), barco.getEquipo()) && barco.getVida() > 0) {
                pararBarcos(this, barco);
                tiempoUltimoDisparo = System.currentTimeMillis();
                int disparar = this.disparar();
                barco.setVida(barco.getVida() - disparar);
                animacionDisparo(this, barco);
                break;
            }
        }
    }
    public synchronized void moverBarco() {
        double x = this.getImagenDelBarco().getLayoutX();
        double y = this.getImagenDelBarco().getLayoutY();
        double velocidad = this.getVelocidad();
        double direccion = Math.toRadians(this.getDireccion());
        x += velocidad * Math.cos(direccion);
        y += velocidad * Math.sin(direccion);
        this.getImagenDelBarco().setLayoutX(x);
        this.getImagenDelBarco().setLayoutY(y);
        this.getImagenDelBarco().setRotate(this.getDireccion());
    }
    public synchronized void cambiarImagenMuerte(Barco barco) {
        if (barco.getVida() <= 0) {
            barco.moverse.stop();
            ImageView muerto = new ImageView(new Image(getClass().getResourceAsStream("imagenes/muerte.png")));
            barco.imagenDelBarco.setImage(muerto.getImage());
            barco.imagenDelBarco.setRotate(0);
            barco.imagenDelBarco.setFitHeight(30);
            barco.imagenDelBarco.setFitWidth(30);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event ->
                    barco.fondo.getChildren().remove(barco.getImagenDelBarco())));
            timeline.play();
            barco.vida = 0;
        }
    }
    public synchronized void barcoMuerto() {
        if (this.getVida() <= 0) {
            moverse.stop();
            this.vida = 0;
        }
    }
    public synchronized void detectarParedes() {
        Colisiones.detectarColision(this);
    }
    public synchronized int disparar() {
        Random rand = new Random();
        int random = rand.nextInt(101);
        if (random < 25) {
            return 0;
        } else if (random < 50) {
            return potenciaFuego / 2;
        } else {
            return potenciaFuego;
        }
    }
    //getters y setters
    public double getDireccion() {
        return direccion;
    }
    public void setDireccion(double direccion) {
        this.direccion = direccion;
    }
    public ImageView getImagenDelBarco() {
        return imagenDelBarco;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public int getVelocidad() {
        return velocidad;
    }
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    public int getSonar() {
        return sonar;
    }
    public void setSonar(int sonar) {
        this.sonar = sonar;
    }
    public int getPotenciaFuego() {
        return potenciaFuego;
    }
    public void setPotenciaFuego(int potenciaFuego) {
        this.potenciaFuego = potenciaFuego;
    }
}

