package br.edu.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        // Ajuste URL, usuário e senha conforme seu banco
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/biblioteca_db?useTimezone=true&serverTimezone=UTC",
                "root",
                "Andrade.Miguel2B"
        );
    }
}