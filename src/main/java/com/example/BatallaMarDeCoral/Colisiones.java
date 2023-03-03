package com.example.BatallaMarDeCoral;

import java.util.Random;
public class Colisiones {
    public static void detectarColision(Barco barco) {
        double x = barco.getImagenDelBarco().getLayoutX();
        double y = barco.getImagenDelBarco().getLayoutY();
        if (barco.getNombre().equals("destructor")) {
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
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
                Random random = new Random();
                int randomNumber = random.nextInt(10) + 1;
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
                    Random random = new Random();
                    int randomNumber = random.nextInt(10) + 1;
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
                    Random random = new Random();
                    int randomNumber = random.nextInt(10) + 1;
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