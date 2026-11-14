package br.com.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Acesso ao banco SQLite da agenda. */
public final class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:agenda.db";

    private DatabaseConnection() { }

    public static Connection conectar() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void inicializarBanco() throws SQLException {
        String compromissos = "CREATE TABLE IF NOT EXISTS compromissos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL CHECK(length(trim(titulo)) > 0),"
                + "data_hora TEXT NOT NULL, descricao TEXT, local TEXT)";
        
        String usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL CHECK(length(trim(nome)) > 0),"
                + "email TEXT NOT NULL COLLATE NOCASE UNIQUE,"
                + "senha_hash TEXT NOT NULL)";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(compromissos);
            stmt.execute(usuarios);
        }
    }

    public static long inserirCompromisso(Compromisso c) throws SQLException {
        String sql = "INSERT INTO compromissos(titulo,data_hora,descricao,local) VALUES(?,?,?,?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDataHora().toString());
            ps.setString(3, c.getDescricao());
            ps.setString(4, c.getLocal());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
            throw new SQLException("SQLite não retornou o ID do compromisso.");
        }
    }

    public static void atualizarCompromisso(Compromisso c) throws SQLException {
        String sql = "UPDATE compromissos SET titulo=?,data_hora=?,descricao=?,local=? WHERE id=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDataHora().toString());
            ps.setString(3, c.getDescricao());
            ps.setString(4, c.getLocal());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public static void removerCompromisso(int id) throws SQLException {
        String sql = "DELETE FROM compromissos WHERE id=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Compromisso> listarCompromissos() throws SQLException {
        List<Compromisso> lista = new ArrayList<>();
        String sql = "SELECT id,titulo,data_hora,descricao,local FROM compromissos ORDER BY data_hora";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Compromisso(rs.getInt("id"), rs.getString("titulo"),
                        LocalDateTime.parse(rs.getString("data_hora")), rs.getString("descricao"), rs.getString("local")));
            }
        }
        return lista;
    }

    public static void inserirUsuario(String nome, String email, String senhaHash) throws SQLException {
        String sql = "INSERT INTO usuarios(nome,email,senha_hash) VALUES(?,?,?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senhaHash);
            ps.executeUpdate();
        }
    }

    public static Usuario buscarUsuarioPorNome(String nome) throws SQLException {
        String sql = "SELECT id,nome,email,senha_hash FROM usuarios WHERE nome=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    return usuario;
                }
            }
        }
        return null; // Usuário não encontrado
    }

    public static Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT id,nome,email,senha_hash FROM usuarios WHERE email=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    return usuario;
                }
            }
        }
        return null; // Usuário não encontrado
    }

    public static void atualizarNomeUsuario(String email, String novoNome) throws SQLException {
        String sql = "UPDATE usuarios SET nome=? WHERE email=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoNome);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    public static void atualizarSenhaUsuario(String email, String novaSenhaHash) throws SQLException {
        String sql = "UPDATE usuarios SET senha_hash=? WHERE email=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novaSenhaHash);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    public static void removerUsuario(String email) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE email=?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }

    public static List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id,nome,email,senha_hash FROM usuarios ORDER BY nome";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha_hash"));
                lista.add(usuario);
            }
        }
        return lista;
    }


}
