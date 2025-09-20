/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 * Subtipo de Prenda para trajes de caballero.
 */
public class TrajeCaballero extends Prenda {

    private String tipo;     // frac, convencional, etc.
    private String aderezo;  // corbata, corbatín...

    public TrajeCaballero(String ref, String color, String marca, String talla,
            double valor, String tipo, String aderezo) {
        super(ref, color, marca, talla, valor);
        this.tipo = tipo;
        this.aderezo = aderezo;
    }

    @Override
    public String descripcion() {
        return "Traje " + tipo + " con " + aderezo;
    }
}
