package br.com.agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Compromisso {
    private int id;
    private String titulo;
    private LocalDateTime dataHora;
    private String descricao;
    private String local;

    public Compromisso() {
    }

   
    public Compromisso(String titulo, LocalDateTime dataHora, String descricao, String local) {
        this.id = 0; // O ID será gerado automaticamente pelo banco de dados
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
    }

    public Compromisso(int id, String titulo, LocalDateTime dataHora, String descricao, String local) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
    }

    //Getters e Setters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }


    //Data formatada para exibição
    public String getDataHoraFormatada() {
        if (this.dataHora != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return this.dataHora.format(formatter);
        }
        return "Data não definida";
    }

    @Override
    public String toString() {
        return "Compromisso [" +
                "ID: " + id +
                " | Título: '" + titulo + '\'' +
                " | Data/Hora: " + getDataHoraFormatada() +
                " | Descrição: '" + descricao + '\'' +
                " | Local: '" + local + '\'' +
                ']';
    }
}
