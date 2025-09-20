/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cabecera del servicio de alquiler + lista de prendas.
 */
public class ServicioAlquiler {

    private int numero;
    private Date fechaSolic;
    private Date fechaAlqui;
    private Cliente cliente;
    private Empleado empleado;
    private List<Prenda> prendas = new ArrayList<>();

    public ServicioAlquiler() {
    }

    public ServicioAlquiler(int numero, Date fechaSolic, Date fechaAlqui,
            Cliente cliente, Empleado empleado, List<Prenda> prendas) {
        this.numero = numero;
        this.fechaSolic = fechaSolic;
        this.fechaAlqui = fechaAlqui;
        this.cliente = cliente;
        this.empleado = empleado;
        if (prendas != null) {
            this.prendas = prendas;
        }
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechaSolic() {
        return fechaSolic;
    }

    public void setFechaSolic(Date fechaSolic) {
        this.fechaSolic = fechaSolic;
    }

    public Date getFechaAlqui() {
        return fechaAlqui;
    }

    public void setFechaAlqui(Date fechaAlqui) {
        this.fechaAlqui = fechaAlqui;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}
