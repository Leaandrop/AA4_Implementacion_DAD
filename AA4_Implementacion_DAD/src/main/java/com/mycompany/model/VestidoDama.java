/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 * Subtipo de Prenda para vestidos de dama.
 */
public class VestidoDama extends Prenda {

    private boolean pedreria;
    private String altura;
    private short cantPiezas;

    public VestidoDama(String ref, String color, String marca, String talla,
            double valor, boolean pedreria, String altura, short cantPiezas) {
        super(ref, color, marca, talla, valor);
        this.pedreria = pedreria;
        this.altura = altura;
        this.cantPiezas = cantPiezas;
    }

    @Override
    public String descripcion() {
        return "Vestido dama " + getTalla() + " altura " + altura;
    }

    // Getters simples (por si los necesitas)
    public boolean isPedreria() {
        return pedreria;
    }

    public String getAltura() {
        return altura;
    }

    public short getCantPiezas() {
        return cantPiezas;
    }
}
