package br.com.agenda;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Compromisso> compromissos;

    public Agenda() {
        this.compromissos = new ArrayList<>();
    }

    public void adicionarCompromisso(Compromisso compromisso) {
        this.compromissos.add(compromisso);
    }

    public void removerCompromisso(Compromisso compromisso) {
        this.compromissos.remove(compromisso);
    }

    public List<Compromisso> getCompromissos() {
        if (this.compromissos.isEmpty()) {
            System.out.println("A agenda está vazia.");
        }
        return this.compromissos;
    }
}