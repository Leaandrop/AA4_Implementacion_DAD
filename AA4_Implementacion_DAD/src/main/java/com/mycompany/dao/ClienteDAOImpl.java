/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Cliente;
import com.mycompany.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia para Cliente (usa tablas persona + cliente).
 */
public class ClienteDAOImpl implements ClienteDAO {

    private final Connection conn;

    public ClienteDAOImpl() throws Exception {
        this.conn = ConexionBD.getInstancia().getConnection();
    }

    @Override
    public boolean guardar(Cliente c) throws Exception {
        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO persona(id,nombre,direccion,telefono) VALUES(?,?,?,?)")) {
                ps.setString(1, c.getId());
                ps.setString(2, c.getNombre());
                ps.setString(3, c.getDireccion());
                ps.setString(4, c.getTelefono());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cliente(id,mail) VALUES(?,?)")) {
                ps.setString(1, c.getId());
                ps.setString(2, c.getMail());
                ps.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public Cliente buscar(String id) throws Exception {
        String sql = """
            SELECT p.id, p.nombre, p.direccion, p.telefono, c.mail
            FROM persona p JOIN cliente c ON p.id=c.id
            WHERE p.id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Cliente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("mail")
                );
            }
        }
    }

    @Override
    public List<Cliente> listar() throws Exception {
        String sql = """
            SELECT p.id, p.nombre, p.direccion, p.telefono, c.mail
            FROM persona p JOIN cliente c ON p.id=c.id
        """;
        List<Cliente> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Cliente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("mail")
                ));
            }
        }
        return out;
    }
}
