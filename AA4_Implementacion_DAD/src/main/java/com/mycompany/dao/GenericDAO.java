/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import java.util.List;

/**
 * Interfaz genérica para CRUD básico.
 */
public interface GenericDAO<T, ID> {

    boolean guardar(T obj) throws Exception;

    T buscar(ID id) throws Exception;

    List<T> listar() throws Exception;
}
