package com.example.BatallaMarDeCoral;

import java.util.Random;
public class Colisiones {
    public static void detectarColision(Barco barco) {
        double x = barco.getImagenDelBarco().getLayoutX();
        double y = barco.getImagenDelBarco().getLayoutY();
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        if (barco.getNombre().equals("destructor")) {
            if (randomNumber <= 5) {
                if (x < 0 || x > Costantes.ANCHO_VENTANA_Destructor) {
                    barco.setDireccion(180 + barco.getDireccion());
                }
                if (y < 0 || y > Costantes.ALTO_VENTANA_Destructor) {
                    barco.setDireccion(180 + barco.getDireccion());
                }
            } else {
                if (x < 0 || x > Costantes.ANCHO_VENTANA_Destructor) {
                    barco.setDireccion(-180 + barco.getDireccion());
                }
                if (y < 0 || y > Costantes.ALTO_VENTANA_Destructor) {
                    barco.setDireccion(-barco.getDireccion());
                }
            }
        } else {
            if (barco.getNombre().equals("submarino")) {
                if (randomNumber >= 5) {
                    if (x < 0 || x > Costantes.ANCHO_VENTANA_Submarino) {
                        barco.setDireccion(180 + barco.getDireccion());
                    }
                    if (y < 0 || y > Costantes.ALTO_VENTANA_Submarino) {
                        barco.setDireccion(180 + barco.getDireccion());
                    }
                } else {
                    if (x < 0 || x > Costantes.ANCHO_VENTANA_Submarino) {
                        barco.setDireccion(180 - barco.getDireccion());
                    }
                    if (y < 0 || y > Costantes.ALTO_VENTANA_Submarino) {
                        barco.setDireccion(-barco.getDireccion());
                    }
                }
            } else {
                if (barco.getNombre().equals("lancha")) {
                    if (randomNumber <= 5) {
                        if (x < 0 || x > Costantes.ANCHO_VENTANA_lancha) {
                            barco.setDireccion(180 + barco.getDireccion());
                        }
                        if (y < 0 || y > Costantes.ALTO_VENTANA_lancha) {
                            barco.setDireccion(180 + barco.getDireccion());
                        }
                    } else {
                        if (x < 0 || x > Costantes.ANCHO_VENTANA_lancha) {
                            barco.setDireccion(180 - barco.getDireccion());
                        }
                        if (y < 0 || y > Costantes.ALTO_VENTANA_lancha) {
                            barco.setDireccion(-barco.getDireccion());
                        }
                    }
                } else {
                    if (randomNumber <= 5) {
                        if (x < 0 || x > Costantes.ANCHO_VENTANA_acorazado) {
                            barco.setDireccion(180 + barco.getDireccion());
                        }
                        if (y < 0 || y > Costantes.ALTO_VENTANA_acorazado) {
                            barco.setDireccion(180 + barco.getDireccion());
                        }
                    } else {
                        if (x < 0 || x > Costantes.ANCHO_VENTANA_acorazado) {
                            barco.setDireccion(180 - barco.getDireccion());
                        }
                        if (y < 0 || y > Costantes.ALTO_VENTANA_acorazado) {
                            barco.setDireccion(-barco.getDireccion());
                        }
                    }
                }
            }
        }
    }
}