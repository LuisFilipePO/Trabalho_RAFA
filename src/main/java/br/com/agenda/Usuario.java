
package br.com.agenda;

public class Usuario {

    private long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    // Construtor para novos usuários
    public Usuario(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    // Construtor para usuários existentes no banco
    public Usuario(long id, String nome, String email, String senha) {
        this(nome, email, senha);
        this.id = id;
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O nome do usuário é obrigatório."
            );
        }

        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                    "Informe um e-mail válido."
            );
        }

        this.email = email.trim();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }

        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='[PROTEGIDA]'" +
                '}';
    }
}