/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author alejo
 */
public class Cliente extends Persona {

    private String mail;

    public Cliente() {
        super();
    }

    public Cliente(String id, String nombre, String direccion, String telefono, String mail) {
        super(id, nombre, direccion, telefono);
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
