/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Prenda;
import java.util.List;

public interface PrendaDAO extends GenericDAO<Prenda, String> {

    /**
     * Devuelve las prendas que no están alquiladas en esa fecha.
     */
    List<Prenda> listarDisponiblesPorFecha(java.sql.Date fecha) throws Exception;
}
