
package br.com.agenda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

public class AssistenteIATest {

    @Test
    @EnabledIfSystemProperty(
            named = "executarGemini",
            matches = "true"
    )
    @EnabledIfEnvironmentVariable(
            named = "GOOGLE_API_KEY",
            matches = ".+"
    )
    public void deveConectarEReceberRespostaDoGemini() {

        AssistenteIA assistente = new AssistenteIA();

        String resposta = assistente.conversar(
                "Responda apenas: Integração funcionando!"
        );

        assertNotNull(resposta);
        assertFalse(resposta.trim().isEmpty());

        assertFalse(
                resposta.startsWith("Limite de utilização"),
                "A API atingiu o limite de utilização."
        );

        assertFalse(
                resposta.startsWith("O serviço do Gemini"),
                "O serviço do Gemini está indisponível."
        );

        assertFalse(
                resposta.startsWith("Não foi possível conectar"),
                "Não foi possível conectar ao Gemini."
        );
    }
}