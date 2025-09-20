/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.ServicioAlquiler;
import java.util.List;

public interface ServicioAlquilerDAO extends GenericDAO<ServicioAlquiler, Integer> {

    List<ServicioAlquiler> listarPorCliente(String idCliente) throws Exception;

    boolean guardarDetalle(int numeroServicio, java.util.List<String> refsPrendas) throws Exception;
}
