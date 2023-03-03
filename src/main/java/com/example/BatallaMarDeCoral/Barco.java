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

/**
 La clase Barco representa un barco en el juego BatallaMarDeCoral.
 Un objeto Barco tiene un nombre, una vida, una velocidad, un sonar y una potencia de fuego.
 Además, tiene una imagen de sí mismo, una dirección, un equipo y un tiempo de recarga para disparar.
 También puede estar en modo de disparo, lo que significa que no puede moverse.
 La clase tiene un método para detectar otros barcos cercanos, así como paredes cercanas.
 También puede disparar y su animación se maneja en un método separado.
 Hay un método para verificar si el barco está recargando, así como un método para detener el movimiento de dos barcos en modo de disparo.
 Finalmente, hay un método para verificar si el juego ha terminado.
 */
public class Barco {
    private String nombre;
    private int vida;
    private int velocidad;
    private int sonar;
    private int potenciaFuego;
    private final ImageView imagenDelBarco;
    private double direccion;

    private final Timeline moverse;
    private final ArrayList<Barco> barcos;
    private final String equipo;
    private boolean modoDisparo;
    private long tiempoDeRecarga;
    private final AnchorPane fondo;

    /**
     Constructor de la clase Barco que inicializa un barco con su nombre, equipo, imagen, lista de barcos,
     ventana y estadísticas según el tipo de barco especificado en el nombre.
     @param nombre el nombre del barco
     @param equipo el equipo al que pertenece el barco
     @param imagenBarco la imagen del barco
     @param barcos la lista de barcos del juego
     @param ventana el panel en el que se dibuja el juego
     */
    public Barco(String nombre, String equipo, ImageView imagenBarco, ArrayList<Barco> barcos, AnchorPane ventana) {
        this.nombre = nombre;
        this.imagenDelBarco = imagenBarco;
        this.barcos = barcos;
        this.fondo = ventana;
        this.modoDisparo = false;
        this.direccion = 45;
        this.equipo = equipo;

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
    /**
     Establece el modo de disparo del barco.
     @param modoDisparo un booleano que indica si el barco está en modo de disparo o no.
     */
    public synchronized void setModoDisparo(boolean modoDisparo) {
        this.modoDisparo = modoDisparo;
    }

    /**
     Detiene el movimiento y detección de colisiones de dos barcos específicos al activar el modo de disparo.
     @param barco1 el primer barco a detener.
     @param barco2 el segundo barco a detener.
     */
    public synchronized void pararBarcos(Barco barco1, Barco barco2) {
        barco1.setModoDisparo(true);
        barco2.setModoDisparo(true);
    }

    /**
     Retorna el equipo al que pertenece el barco.
     @return el nombre del equipo del barco.
     */
    public String getEquipo() {
        return equipo;
    }

    /**
     Verifica si el juego debe ser detenido porque uno de los equipos se quedó sin barcos vivos.
     Si un equipo no tiene barcos vivos y el otro sí, detiene el movimiento de los barcos.
     */
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

    /**
     Verifica si el barco está actualmente recargando.
     @return true si el barco está recargando, false en caso contrario.
     */
    public synchronized boolean recargando() {
        long tiempoActual = System.currentTimeMillis();
        return tiempoActual < tiempoUltimoDisparo + tiempoDeRecarga;
    }

    /**
     Realiza la animación de un disparo desde el barco1 hasta el barco2.
     Agrega una imagen de bala a la escena y la mueve desde la posición del barco1 hasta la posición del barco2 en un tiempo de 2 segundos.
     Después de que la animación termina, elimina la imagen de la bala y establece el modo de disparo de ambos barcos a falso.
     @param barco1 el primer barco que dispara
     @param barco2 el barco que recibe el disparo
     */
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
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(bola.xProperty(), barco2X),
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

    /**
     Método sincronizado que detecta barcos cercanos y dispara si encuentra uno a una distancia menor que el sonar del barco.
     Si el barco está recargando o tiene 0 puntos de vida no realizará ninguna acción.
     @return void
     */
    public synchronized void detectarBarcosCercanos() {
        if (!(recargando() || getVida() <= 0)) {
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
    }

    /**
     Mueve el barco en la dirección en la que está apuntando a una velocidad dada.
     La posición del barco se actualiza en función de la velocidad y dirección actuales.
     También se actualiza la rotación del barco para que apunte en la dirección en la que está moviéndose.
     */
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

    /**
     Cambia la imagen del barco por la imagen de muerte si su vida es menor o igual a cero.
     Detiene la animación del movimiento del barco y lo remueve del fondo después de 5 segundos.
     @param barco El barco que se va a cambiar de imagen si su vida es menor o igual a cero.
     */
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

    /**
     Método sincronizado que detiene el movimiento del barco si su vida es menor o igual a cero.
     Actualiza el valor de vida del barco a cero.
     */
    public synchronized void barcoMuerto() {
        if (this.getVida() <= 0) {
            moverse.stop();
            this.vida = 0;
        }
    }

    /**
     Método para detectar colisiones del barco con paredes en la escena.
     Se utiliza el método estático "detectarColision" de la clase Colisiones para realizar la detección
     de colisiones con las paredes y tomar las medidas necesarias en caso de que se produzca una colisión.
     */
    public synchronized void detectarParedes() {
        Colisiones.detectarColision(this);
    }

    /**

     Genera un disparo aleatorio según la potencia de fuego del barco.
     @return la cantidad de daño generado por el disparo.
     */
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

    /**

     Retorna la dirección actual del barco en grados
     @return la dirección actual del barco en grados
     */
    public double getDireccion() {
        return direccion;
    }
    /**

     Cambia la dirección del barco a la dirección especificada en grados
     @param direccion la nueva dirección del barco en grados
     */
    public void setDireccion(double direccion) {
        this.direccion = direccion;
    }
    /**

     Retorna la imagen del barco
     @return la imagen del barco
     */
    public ImageView getImagenDelBarco() {
        return imagenDelBarco;
    }
    /**

     Retorna el nombre del barco
     @return el nombre del barco
     */
    public String getNombre() {
        return nombre;
    }
    /**

     Cambia el nombre del barco al nombre especificado
     @param nombre el nuevo nombre del barco
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**

     Retorna la vida actual del barco
     @return la vida actual del barco
     */
    public int getVida() {
        return vida;
    }
    /**

     Cambia la vida del barco a la vida especificada
     @param vida la nueva vida del barco
     */
    public void setVida(int vida) {
        this.vida = vida;
    }
    /**

     Retorna la velocidad actual del barco
     @return la velocidad actual del barco
     */
    public int getVelocidad() {
        return velocidad;
    }
    /**

     Cambia la velocidad del barco a la velocidad especificada
     @param velocidad la nueva velocidad del barco
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    /**

     Retorna el alcance del sonar del barco
     @return el alcance del sonar del barco
     */
    public int getSonar() {
        return sonar;
    }
    /**

     Cambia el alcance del sonar del barco al alcance especificado
     @param sonar el nuevo alcance del sonar del barco
     */
    public void setSonar(int sonar) {
        this.sonar = sonar;
    }
    /**

     Retorna la potencia de fuego actual del barco
     @return la potencia de fuego actual del barco
     */
    public int getPotenciaFuego() {
        return potenciaFuego;
    }
    /**

     Cambia la potencia de fuego del barco a la potencia de fuego especificada
     @param potenciaFuego la nueva potencia de fuego del barco
     */
    public void setPotenciaFuego(int potenciaFuego) {
        this.potenciaFuego = potenciaFuego;
    }

}
