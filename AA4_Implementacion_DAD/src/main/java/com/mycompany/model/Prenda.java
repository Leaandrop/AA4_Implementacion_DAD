/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 * Producto base para los tipos de prenda.
 */
public abstract class Prenda {

    private String ref;
    private String color;
    private String marca;
    private String talla;
    private double valorAlquiler;

    public Prenda(String ref, String color, String marca, String talla, double valor) {
        this.ref = ref;
        this.color = color;
        this.marca = marca;
        this.talla = talla;
        this.valorAlquiler = valor;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public double getValorAlquiler() {
        return valorAlquiler;
    }

    public void setValorAlquiler(double v) {
        this.valorAlquiler = v;
    }

    public abstract String descripcion();
}
