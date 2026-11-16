package br.com.agenda;
public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.id = 0; // O ID será gerado automaticamente pelo banco de dados
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
