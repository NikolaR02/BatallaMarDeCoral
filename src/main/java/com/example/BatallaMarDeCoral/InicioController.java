package com.example.BatallaMarDeCoral;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;
// instancia
public class InicioController {
    @FXML
    private AnchorPane ventana;
    Barco barcoDesRojo;
    Barco barcoLanRojo;
    Barco barcoAcoRojo;
    Barco barcoSubRojo;
    Barco barcoDesAzul;
    Barco barcoLanAzul;
    Barco barcoAcoAzul;
    Barco barcoSubAzul;
    ControlDeJuego control;
    @FXML
    private AnchorPane rojo;
    @FXML
    private AnchorPane azul;
    public void initialize() {
        Image fondo = new Image(getClass().getResourceAsStream("imagenes/agua.jpg"));
        ImageView back = new ImageView(fondo);
        ventana.setBackground(new Background(new BackgroundImage(back.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        instanciarBarcos();
    }
    List<Integer> numbers = new ArrayList<>();
    public void asignarPosicionRojo(ImageView imagen, int num) {
        if (num == 1) {
            imagen.setLayoutX(28);
            imagen.setLayoutY(371);
        }
        if (num == 2) {
            imagen.setLayoutX(28);
            imagen.setLayoutY(75);
        }
        if (num == 3) {
            imagen.setLayoutX(28);
            imagen.setLayoutY(149);
        }
        if (num == 4) {
            imagen.setLayoutX(28);
            imagen.setLayoutY(575);
        }
    }
    public void asignarPosicionAzul(ImageView imagen, int num) {
        if (num == 1) {
            imagen.setLayoutX(682);
            imagen.setLayoutY(371);
        }
        if (num == 2) {
            imagen.setLayoutX(676);
            imagen.setLayoutY(75);
        }
        if (num == 3) {
            imagen.setLayoutX(676);
            imagen.setLayoutY(147);
        }
        if (num == 4) {
            imagen.setLayoutX(676);
            imagen.setLayoutY(575);
        }
    }
    public void instanciarBarcos() {
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        Collections.shuffle(numbers);
        control = new ControlDeJuego();


        ImageView destructorImg = new ImageView();
        destructorImg.setImage(new Image(getClass().getResourceAsStream("imagenes/destructor_rojo.png")));
        asignarPosicionRojo(destructorImg, numbers.remove(0));
        control.addBarco(barcoDesRojo = new Barco("destructor", "Rojo", destructorImg, control.getBarcos() ,ventana));

        ImageView acorazadoImg = new ImageView();
        acorazadoImg.setImage(new Image(getClass().getResourceAsStream("imagenes/acorazado_rojo.png")));
        asignarPosicionRojo(acorazadoImg, numbers.remove(0));
        control.addBarco(barcoAcoRojo = new Barco("acorazado", "Rojo", acorazadoImg, control.getBarcos(),ventana));

        ImageView lanchaImg = new ImageView();
        lanchaImg.setImage(new Image(getClass().getResourceAsStream("imagenes/lancha_roja.png")));
        asignarPosicionRojo(lanchaImg, numbers.remove(0));
        control.addBarco(barcoLanRojo = new Barco("lancha", "Rojo", lanchaImg, control.getBarcos(),ventana));

        ImageView submarinoImg = new ImageView();
        submarinoImg.setImage(new Image(getClass().getResourceAsStream("imagenes/submarino_rojo.png")));
        asignarPosicionRojo(submarinoImg, numbers.remove(0));
        control.addBarco(barcoSubRojo = new Barco("submarino", "Rojo", submarinoImg, control.getBarcos(),ventana));

        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        Collections.shuffle(numbers);

        ImageView destructorImg2 = new ImageView();
        destructorImg2.setImage(new Image(getClass().getResourceAsStream("imagenes/destructor_azul.png")));

        asignarPosicionAzul(destructorImg2, numbers.remove(0));
        control.addBarco(barcoDesAzul = new Barco("destructor", "Azul", destructorImg2, control.getBarcos(),ventana));

        ImageView acorazadoImg2 = new ImageView();
        acorazadoImg2.setImage(new Image(getClass().getResourceAsStream("imagenes/acorazado_azul.png")));
        asignarPosicionAzul(acorazadoImg2, numbers.remove(0));
        control.addBarco(barcoAcoAzul = new Barco("acorazado", "Azul", acorazadoImg2, control.getBarcos(),ventana));

        ImageView lanchaImg2 = new ImageView();
        lanchaImg2.setImage(new Image(getClass().getResourceAsStream("imagenes/lancha_azul.png")));
        asignarPosicionAzul(lanchaImg2, numbers.remove(0));
        control.addBarco(barcoLanAzul = new Barco("lancha", "Azul", lanchaImg2, control.getBarcos(),ventana));

        ImageView submarinoImg2 = new ImageView();
        submarinoImg2.setImage(new Image(getClass().getResourceAsStream("imagenes/submarino_azul.png")));
        asignarPosicionAzul(submarinoImg2, numbers.remove(0));
        control.addBarco(barcoSubAzul = new Barco("submarino", "Azul", submarinoImg2, control.getBarcos(),ventana));

        PestanaRojo pestRojo = new PestanaRojo();
        pestRojo.getControl(control);

        PestanaAzul pestAzul = new PestanaAzul();
        pestAzul.getControl(control);

        rojo.getChildren().add(pestRojo.getScene().getRoot());
        azul.getChildren().add(pestAzul.getScene().getRoot());

        ventana.getChildren().addAll(barcoDesRojo.getImagenDelBarco(), barcoDesAzul.getImagenDelBarco(),
                barcoAcoRojo.getImagenDelBarco(), barcoAcoAzul.getImagenDelBarco(), barcoLanRojo.getImagenDelBarco(),
                barcoLanAzul.getImagenDelBarco(), barcoSubRojo.getImagenDelBarco(), barcoSubAzul.getImagenDelBarco());
        control.ganador();

    }


}