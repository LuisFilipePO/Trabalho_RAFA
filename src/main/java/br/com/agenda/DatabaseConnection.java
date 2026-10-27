package br.com.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection conectar() {
        Connection conn = null;
        try {
            // Define o nome e o local do arquivo do banco
            String url = "jdbc:sqlite:agenda.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Conexão ao SQLite estabelecida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return conn;
    }
}
