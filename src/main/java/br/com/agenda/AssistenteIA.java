
package br.com.agenda;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class AssistenteIA {

    private static final String MODELO = "gemini-3.8-flash";

    private final Client client;

    public AssistenteIA() {

        String apiKey = System.getenv("GOOGLE_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "A variável GOOGLE_API_KEY não está configurada."
            );
        }

        client = new Client();
    }

    public String conversar(String mensagem) {

        if (mensagem == null || mensagem.trim().isEmpty()) {
            return "Digite uma mensagem para conversar com a IA.";
        }

        try {

            GenerateContentResponse resposta =
                    client.models.generateContent(
                            MODELO,
                            mensagem,
                            null
                    );

            String texto = resposta.text();

            if (texto == null || texto.trim().isEmpty()) {
                return "A IA não retornou uma resposta válida.";
            }

            return texto;

        } catch (RuntimeException e) {

            String erro = e.getMessage() == null
                    ? ""
                    : e.getMessage().toLowerCase();

            if (erro.contains("429")
                    || erro.contains("resource_exhausted")
                    || erro.contains("quota")) {

                return "Limite de utilização da API atingido. "
                        + "Tente novamente mais tarde.";
            }

            if (erro.contains("503")
                    || erro.contains("unavailable")) {

                return "O serviço do Gemini está temporariamente "
                        + "indisponível. Tente novamente mais tarde.";
            }

            return "Não foi possível conectar ao Gemini. "
                    + "Verifique sua conexão e configuração da API.";
        }
    }
}