package br.com.agenda;

import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            DatabaseConnection.inicializarBanco();
            System.out.println("Banco SQLite inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Não foi possível inicializar o banco: " + e.getMessage());
            System.exit(1);
        }
    }
}
