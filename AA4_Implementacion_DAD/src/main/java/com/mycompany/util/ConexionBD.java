/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conn;

    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/losatuendos"
      + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    private ConexionBD() {}

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) instancia = new ConexionBD();
        return instancia;
    }

    /** Devuelve una conexión viva (reconecta si se cerró). */
    public synchronized Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed() || !conn.isValid(2)) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}