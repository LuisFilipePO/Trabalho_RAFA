
package br.com.agenda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    private Agenda agenda;
    private Compromisso compromisso;

    @BeforeEach
    public void configurar() {
        agenda = new Agenda();

        compromisso = new Compromisso(
            "Reunião de projeto",
            LocalDateTime.of(2026, 11, 20, 14, 30),
            "Reunião para discutir o projeto",
            "Faculdade"
        );
    }

    @Test
    public void deveAdicionarCompromisso() {
        agenda.adicionarCompromisso(compromisso);

        assertEquals(1, agenda.getCompromissos().size());
        assertTrue(agenda.getCompromissos().contains(compromisso));
    }

    @Test
    public void deveRemoverCompromisso() {
        agenda.adicionarCompromisso(compromisso);

        agenda.removerCompromisso(compromisso);

        assertTrue(agenda.getCompromissos().isEmpty());
        assertFalse(agenda.getCompromissos().contains(compromisso));
    }

    @Test
    public void deveAtualizarCompromisso() {
        agenda.adicionarCompromisso(compromisso);

        Compromisso atualizado = new Compromisso(
            "Reunião atualizada",
            LocalDateTime.of(2026, 11, 21, 15, 30),
            "Nova descrição",
            "Sala 2"
        );

        agenda.atualizarCompromisso(compromisso, atualizado);

        assertEquals(1, agenda.getCompromissos().size());
        assertFalse(agenda.getCompromissos().contains(compromisso));
        assertTrue(agenda.getCompromissos().contains(atualizado));
    }

    @Test
    public void deveIniciarComAgendaVazia() {
        assertEquals(0, agenda.getCompromissos().size());
        assertTrue(agenda.getCompromissos().isEmpty());
    }
}