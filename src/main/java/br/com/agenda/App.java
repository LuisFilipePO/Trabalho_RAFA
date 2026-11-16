
package br.com.agenda;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) {

        // Inicialização do banco
        try {
            DatabaseConnection.inicializarBanco();

            System.out.println("Banco SQLite inicializado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao iniciar banco: " + e.getMessage());
            return;
        }

        // Conexão com o Gemini
        try {
            AssistenteIA assistente = new AssistenteIA();

            System.out.println("\nConectando com o Gemini...");

            String resposta = assistente.conversar(
                "Você é Hermes, meu assistente pessoal de agenda. " +
                "Apresente-se brevemente em português."
            );

            System.out.println("\nResposta da IA:");
            System.out.println(resposta);

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o Gemini:");
            System.err.println(e.getMessage());

            e.printStackTrace();
        }
    }
}