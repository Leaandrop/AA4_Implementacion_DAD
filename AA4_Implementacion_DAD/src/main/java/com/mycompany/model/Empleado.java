/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

public class Empleado extends Persona {

    private String cargo;

    public Empleado() {
        super();
    }

    public Empleado(String id, String nombre, String direccion, String telefono, String cargo) {
        super(id, nombre, direccion, telefono);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
