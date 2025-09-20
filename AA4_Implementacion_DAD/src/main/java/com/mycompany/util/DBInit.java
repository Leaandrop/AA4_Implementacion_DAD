/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

/**
 *
 * @author alejo
 */

import java.sql.Connection;
import java.sql.Statement;

/**
 * Creador de tablas "si no existen".
 * Lo llamo una vez al arrancar para no depender de crearlas a mano.
 */
public class DBInit {

    public static void init() {
        try {
            Connection c = ConexionBD.getInstancia().getConnection();
            try (Statement st = c.createStatement()) {

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS persona (
                      id        VARCHAR(20)  PRIMARY KEY,
                      nombre    VARCHAR(100) NOT NULL,
                      direccion VARCHAR(100),
                      telefono  VARCHAR(20)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS cliente (
                      id   VARCHAR(20) PRIMARY KEY,
                      mail VARCHAR(100),
                      CONSTRAINT fk_cliente_persona
                        FOREIGN KEY (id) REFERENCES persona(id)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS empleado (
                      id    VARCHAR(20) PRIMARY KEY,
                      cargo VARCHAR(50),
                      CONSTRAINT fk_empleado_persona
                        FOREIGN KEY (id) REFERENCES persona(id)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS prenda (
                      ref   VARCHAR(20) PRIMARY KEY,
                      color VARCHAR(50),
                      marca VARCHAR(50),
                      talla VARCHAR(10),
                      valor DOUBLE,
                      tipo  VARCHAR(20)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS servicio_alquiler (
                      numero      INT PRIMARY KEY,
                      fechaSolic  DATE,
                      fechaAlqui  DATE,
                      cliente_id  VARCHAR(20),
                      empleado_id VARCHAR(20),
                      CONSTRAINT fk_sa_cliente  FOREIGN KEY (cliente_id)  REFERENCES cliente(id),
                      CONSTRAINT fk_sa_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS servicio_prenda (
                      numero INT,
                      ref    VARCHAR(20),
                      INDEX idx_sp_numero (numero),
                      INDEX idx_sp_ref (ref)
                    )
                """);
            }
        } catch (Exception e) {
            // Si algo falla lo mostramos, pero no detenemos toda la app de consola.
            System.err.println("[DBInit] Error creando tablas: " + e.getMessage());
        }
    }
}