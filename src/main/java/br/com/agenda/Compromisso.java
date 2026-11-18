
package br.com.agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Compromisso {

    private int id;
    private String titulo;
    private LocalDateTime dataHora;
    private String descricao;
    private String local;

    private static final DateTimeFormatter FORMATADOR =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Construtor vazio
    public Compromisso() {
    }

    // Construtor para novos compromissos
    public Compromisso(String titulo, LocalDateTime dataHora,
                       String descricao, String local) {

        setTitulo(titulo);
        setDataHora(dataHora);
        setDescricao(descricao);
        setLocal(local);
    }

    // Construtor para compromissos existentes no banco
    public Compromisso(int id, String titulo, LocalDateTime dataHora,
                       String descricao, String local) {

        this(titulo, dataHora, descricao, local);
        this.id = id;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O título do compromisso é obrigatório."
            );
        }

        this.titulo = titulo.trim();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            throw new IllegalArgumentException(
                    "A data e hora do compromisso são obrigatórias."
            );
        }

        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O local do compromisso é obrigatório."
            );
        }

        this.local = local.trim();
    }

    // Formatação da data e hora

    public String getDataHoraFormatada() {
        if (dataHora == null) {
            return "Data não definida";
        }

        return dataHora.format(FORMATADOR);
    }

    // Representação textual

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