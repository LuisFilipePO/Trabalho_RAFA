
package br.com.agenda;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private List<Compromisso> compromissos;

    public Agenda() {
        this.compromissos = new ArrayList<>();
    }

    // Adicionar compromisso
    public void adicionarCompromisso(Compromisso compromisso) {

        if (compromisso == null) {
            throw new IllegalArgumentException(
                    "O compromisso não pode ser nulo."
            );
        }

        this.compromissos.add(compromisso);
    }

    // Remover compromisso
    public boolean removerCompromisso(Compromisso compromisso) {

        if (compromisso == null) {
            return false;
        }

        return this.compromissos.remove(compromisso);
    }

    // Atualizar compromisso
    public boolean atualizarCompromisso(
            Compromisso compromissoAntigo,
            Compromisso compromissoNovo) {

        if (compromissoAntigo == null || compromissoNovo == null) {
            throw new IllegalArgumentException(
                    "Os compromissos não podem ser nulos."
            );
        }

        int index = this.compromissos.indexOf(compromissoAntigo);

        if (index != -1) {
            this.compromissos.set(index, compromissoNovo);
            return true;
        }

        return false;
    }

    // Listar compromissos
    public void listarCompromissos() {

        if (this.compromissos.isEmpty()) {
            System.out.println("A agenda está vazia.");
            return;
        }

        for (Compromisso compromisso : this.compromissos) {
            System.out.println(compromisso);
        }
    }

    // Retornar uma cópia da lista
    public List<Compromisso> getCompromissos() {
        return new ArrayList<>(compromissos);
    }
}