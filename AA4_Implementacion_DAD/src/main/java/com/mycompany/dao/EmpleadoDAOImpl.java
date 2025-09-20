/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Empleado;
import com.mycompany.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia para Empleado (usa tablas persona + empleado).
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    private final Connection conn;

    public EmpleadoDAOImpl() throws Exception {
        this.conn = ConexionBD.getInstancia().getConnection();
    }

    @Override
    public boolean guardar(Empleado e) throws Exception {
        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO persona(id,nombre,direccion,telefono) VALUES(?,?,?,?)")) {
                ps.setString(1, e.getId());
                ps.setString(2, e.getNombre());
                ps.setString(3, e.getDireccion());
                ps.setString(4, e.getTelefono());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO empleado(id,cargo) VALUES(?,?)")) {
                ps.setString(1, e.getId());
                ps.setString(2, e.getCargo());
                ps.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public Empleado buscar(String id) throws Exception {
        String sql = """
            SELECT p.id, p.nombre, p.direccion, p.telefono, e.cargo
            FROM persona p JOIN empleado e ON p.id=e.id
            WHERE p.id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Empleado(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("cargo")
                );
            }
        }
    }

    @Override
    public List<Empleado> listar() throws Exception {
        String sql = """
            SELECT p.id, p.nombre, p.direccion, p.telefono, e.cargo
            FROM persona p JOIN empleado e ON p.id=e.id
        """;
        List<Empleado> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Empleado(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("cargo")
                ));
            }
        }
        return out;
    }
}
