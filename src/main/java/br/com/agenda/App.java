
package br.com.agenda;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       AGENDA INTELIGENTE");
        System.out.println("================================");

        inicializarBanco();

        inicializarAssistente();
    }

    // Inicializar banco de dados
    private static void inicializarBanco() {

        try {

            DatabaseConnection.inicializarBanco();

            System.out.println(
                    "\nBanco SQLite inicializado com sucesso."
            );

        } catch (SQLException e) {

            System.err.println(
                    "Erro ao iniciar banco SQLite: " + e.getMessage()
            );

            throw new IllegalStateException(
                    "Não foi possível iniciar a aplicação.", e
            );
        }
    }

    // Inicializar assistente IA
    private static void inicializarAssistente() {

        System.out.println("\nConectando com o Gemini...");

        try {

            AssistenteIA assistente = new AssistenteIA();

            String resposta = assistente.conversar(
                    "Você é Hermes, meu assistente pessoal de agenda. "
                    + "Apresente-se brevemente em português."
            );

            System.out.println("\nResposta da IA:");
            System.out.println(resposta);

        } catch (RuntimeException e) {

            System.err.println(
                    "\nNão foi possível inicializar o assistente IA."
            );

            System.err.println(e.getMessage());
        }
    }
}