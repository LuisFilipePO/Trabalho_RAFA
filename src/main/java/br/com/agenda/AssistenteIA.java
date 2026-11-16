
package br.com.agenda;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class AssistenteIA {

    private final Client client;

    public AssistenteIA() {
        // Utiliza a variável GOOGLE_API_KEY
        client = new Client();
    }

    public String conversar(String mensagem) {

        GenerateContentResponse resposta =
            client.models.generateContent(
                "gemini-3.8-flash",
                mensagem,
                null
            );

        return resposta.text();
    }
}