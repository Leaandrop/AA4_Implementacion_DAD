/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Prenda;
import com.mycompany.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia para Prenda. Manejo simple por "tipo" en una sola tabla.
 */
public class PrendaDAOImpl implements PrendaDAO {

    private final Connection conn;

    public PrendaDAOImpl() throws Exception {
        this.conn = ConexionBD.getInstancia().getConnection();
    }

    @Override
    public boolean guardar(Prenda p) throws Exception {
        String sql = "INSERT INTO prenda(ref,color,marca,talla,valor,tipo) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getRef());
            ps.setString(2, p.getColor());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getTalla());
            ps.setDouble(5, p.getValorAlquiler());
            ps.setString(6, p.getClass().getSimpleName());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Prenda buscar(String ref) throws Exception {
        String sql = "SELECT ref,color,marca,talla,valor,tipo FROM prenda WHERE ref=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ref);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                final String tipo = rs.getString("tipo");
                final String r = rs.getString("ref");
                final String color = rs.getString("color");
                final String marca = rs.getString("marca");
                final String talla = rs.getString("talla");
                final double valor = rs.getDouble("valor");

                // Devuelvo una Prenda anónima con la descripción basada en 'tipo'.
                return new Prenda(r, color, marca, talla, valor) {
                    @Override
                    public String descripcion() {
                        return tipo + " " + getTalla();
                    }
                };
            }
        }
    }

    @Override
    public List<Prenda> listar() throws Exception {
        String sql = "SELECT ref,color,marca,talla,valor,tipo FROM prenda";
        List<Prenda> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final String ref = rs.getString("ref");
                final String color = rs.getString("color");
                final String marca = rs.getString("marca");
                final String talla = rs.getString("talla");
                final double valor = rs.getDouble("valor");
                final String tipo = rs.getString("tipo");

                Prenda p = new Prenda(ref, color, marca, talla, valor) {
                    @Override
                    public String descripcion() {
                        return tipo + " " + getTalla();
                    }
                };
                out.add(p);
            }
        }
        return out;
    }

    @Override
    public List<Prenda> listarDisponiblesPorFecha(java.sql.Date fecha) throws Exception {
        // Disponible = ref que no aparece en un servicio con esa fecha
        String sql = """
            SELECT p.ref, p.color, p.marca, p.talla, p.valor, p.tipo
            FROM prenda p
            WHERE p.ref NOT IN (
              SELECT sp.ref
              FROM servicio_alquiler sa
              JOIN servicio_prenda sp ON sp.numero = sa.numero
              WHERE sa.fechaAlqui = ?
            )
        """;
        List<Prenda> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final String ref = rs.getString("ref");
                    final String color = rs.getString("color");
                    final String marca = rs.getString("marca");
                    final String talla = rs.getString("talla");
                    final double valor = rs.getDouble("valor");
                    final String tipo = rs.getString("tipo");

                    Prenda p = new Prenda(ref, color, marca, talla, valor) {
                        @Override
                        public String descripcion() {
                            return tipo + " " + getTalla();
                        }
                    };
                    out.add(p);
                }
            }
        }
        return out;
    }
}
