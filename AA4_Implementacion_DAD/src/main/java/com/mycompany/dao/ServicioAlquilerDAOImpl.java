/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.ServicioAlquiler;
import com.mycompany.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia para la cabecera de Servicio + métodos de consulta.
 */
public class ServicioAlquilerDAOImpl implements ServicioAlquilerDAO {

    private final Connection conn;

    public ServicioAlquilerDAOImpl() throws Exception {
        this.conn = ConexionBD.getInstancia().getConnection();
    }

    @Override
    public boolean guardar(ServicioAlquiler s) throws Exception {
        String sql = """
            INSERT INTO servicio_alquiler(numero, fechaSolic, fechaAlqui, cliente_id, empleado_id)
            VALUES(?,?,?,?,?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getNumero());
            ps.setDate(2, new java.sql.Date(s.getFechaSolic().getTime()));
            ps.setDate(3, new java.sql.Date(s.getFechaAlqui().getTime()));
            ps.setString(4, s.getCliente().getId());
            ps.setString(5, s.getEmpleado().getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public ServicioAlquiler buscar(Integer numero) throws Exception {
        String sql = "SELECT numero,fechaSolic,fechaAlqui FROM servicio_alquiler WHERE numero=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                ServicioAlquiler s = new ServicioAlquiler();
                s.setNumero(rs.getInt("numero"));
                s.setFechaSolic(new java.util.Date(rs.getDate("fechaSolic").getTime()));
                s.setFechaAlqui(new java.util.Date(rs.getDate("fechaAlqui").getTime()));
                return s;
            }
        }
    }

    @Override
    public List<ServicioAlquiler> listar() throws Exception {
        String sql = "SELECT numero,fechaSolic,fechaAlqui FROM servicio_alquiler ORDER BY fechaAlqui DESC";
        List<ServicioAlquiler> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ServicioAlquiler s = new ServicioAlquiler();
                s.setNumero(rs.getInt("numero"));
                s.setFechaSolic(new java.util.Date(rs.getDate("fechaSolic").getTime()));
                s.setFechaAlqui(new java.util.Date(rs.getDate("fechaAlqui").getTime()));
                out.add(s);
            }
        }
        return out;
    }

    @Override
    public List<ServicioAlquiler> listarPorCliente(String idCliente) throws Exception {
        String sql = """
            SELECT numero,fechaSolic,fechaAlqui
            FROM servicio_alquiler
            WHERE cliente_id=?
            ORDER BY fechaAlqui DESC
        """;
        List<ServicioAlquiler> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServicioAlquiler s = new ServicioAlquiler();
                    s.setNumero(rs.getInt("numero"));
                    s.setFechaSolic(new java.util.Date(rs.getDate("fechaSolic").getTime()));
                    s.setFechaAlqui(new java.util.Date(rs.getDate("fechaAlqui").getTime()));
                    out.add(s);
                }
            }
        }
        return out;
    }

    @Override
    public boolean guardarDetalle(int numeroServicio, List<String> refsPrendas) throws Exception {
        String sql = "INSERT INTO servicio_prenda(numero, ref) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String ref : refsPrendas) {
                ps.setInt(1, numeroServicio);
                ps.setString(2, ref);
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        }
    }
}
