/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.dao.*;
import com.mycompany.model.*;
import com.mycompany.lavanderia.LavanderiaQueue;

import java.sql.Date;
import java.util.List;

/**
 * Fachada del negocio. Desde acá el UI llama todo. Implementación simple con
 * DAOs y una cola prioritaria para lavandería.
 */
public class NegocioAlquiler {

    private static NegocioAlquiler instancia;

    private final ClienteDAO clienteDAO;
    private final EmpleadoDAO empleadoDAO;
    private final PrendaDAO prendaDAO;
    private final ServicioAlquilerDAO servicioDAO;

    private final LavanderiaQueue colaLavanderia = new LavanderiaQueue();

    private NegocioAlquiler() throws Exception {
        this.clienteDAO = new ClienteDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
        this.prendaDAO = new PrendaDAOImpl();
        this.servicioDAO = new ServicioAlquilerDAOImpl();
    }

    public static synchronized NegocioAlquiler getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new NegocioAlquiler();
        }
        return instancia;
    }

    // Registro básico
    public boolean registrarCliente(Cliente c) throws Exception {
        return clienteDAO.guardar(c);
    }

    public boolean registrarEmpleado(Empleado e) throws Exception {
        return empleadoDAO.guardar(e);
    }

    public boolean registrarPrenda(Prenda p) throws Exception {
        return prendaDAO.guardar(p);
    }

    // Crear servicio (cabecera + detalle)
    public boolean crearServicioAlquiler(int numero, String idCliente, String idEmpleado,
            List<String> refsPrendas, Date fechaAlqui) throws Exception {
        Cliente c = clienteDAO.buscar(idCliente);
        Empleado e = empleadoDAO.buscar(idEmpleado);
        if (c == null || e == null) {
            throw new IllegalArgumentException("Cliente/Empleado no existe");
        }

        for (String ref : refsPrendas) {
            if (prendaDAO.buscar(ref) == null) {
                throw new IllegalArgumentException("Prenda no existe: " + ref);
            }
        }

        ServicioAlquiler s = new ServicioAlquiler();
        s.setNumero(numero);
        s.setFechaSolic(new java.util.Date(System.currentTimeMillis()));
        s.setFechaAlqui(new java.util.Date(fechaAlqui.getTime()));
        s.setCliente(c);
        s.setEmpleado(e);

        boolean okCab = servicioDAO.guardar(s);
        boolean okDet = servicioDAO.guardarDetalle(numero, refsPrendas);
        return okCab && okDet;
    }

    public List<ServicioAlquiler> consultarServicioPorCliente(String idCliente) throws Exception {
        return servicioDAO.listarPorCliente(idCliente);
    }

    public List<Prenda> listarPrendasDisponibles(Date fecha) throws Exception {
        return prendaDAO.listarDisponiblesPorFecha(fecha);
    }

    // Lavandería
    public void registrarLavanderia(String ref, int prioridad) throws Exception {
        Prenda p = prendaDAO.buscar(ref);
        if (p == null) {
            throw new IllegalArgumentException("Prenda no existe");
        }
        colaLavanderia.encolar(p, prioridad);
    }

    public List<Prenda> enviarLavanderia(int cantidad) {
        return colaLavanderia.extraer(cantidad);
    }

    public List<String> mostrarLavanderia() {
        return colaLavanderia.asListDescripcion();
    }
}
