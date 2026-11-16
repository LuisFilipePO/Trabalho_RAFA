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

    public void atualizarCompromisso(Compromisso compromissoAntigo, Compromisso compromissoNovo) {
        int index = this.compromissos.indexOf(compromissoAntigo);
        if (index != -1) {
            this.compromissos.set(index, compromissoNovo);
        } else {
            System.out.println("Compromisso não encontrado na agenda.");
        }
    }

    public void listarCompromissos() {
        if (this.compromissos.isEmpty()) {
            System.out.println("A agenda está vazia.");
        } else {
            for (Compromisso compromisso : this.compromissos) {
                System.out.println(compromisso);
            }
        }
    }

    public List<Compromisso> getCompromissos() {
        return compromissos;
    }
}