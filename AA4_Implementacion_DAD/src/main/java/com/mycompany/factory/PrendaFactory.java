/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.factory;

import com.mycompany.model.*;

/**
 * Factory básica para crear prendas concretas según el tipo.
 */
public class PrendaFactory {

    public static Prenda crearPrenda(String tipo, String ref, String color, String marca,
            String talla, double valor, Object... extras) {
        switch (tipo.toLowerCase()) {
            case "vestidodama":
                return new VestidoDama(ref, color, marca, talla, valor,
                        (boolean) extras[0], (String) extras[1], (short) extras[2]);
            case "trajecaballero":
                return new TrajeCaballero(ref, color, marca, talla, valor,
                        (String) extras[0], (String) extras[1]);
            case "disfraz":
                return new Disfraz(ref, color, marca, talla, valor, (String) extras[0]);
            default:
                throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        }
    }
}
