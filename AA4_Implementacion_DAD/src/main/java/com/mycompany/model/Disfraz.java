/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 * Subtipo de Prenda para disfraces.
 */
public class Disfraz extends Prenda {

    private String nombre;

    public Disfraz(String ref, String color, String marca, String talla, double valor, String nombre) {
        super(ref, color, marca, talla, valor);
        this.nombre = nombre;
    }

    @Override
    public String descripcion() {
        return "Disfraz de " + nombre;
    }
}
