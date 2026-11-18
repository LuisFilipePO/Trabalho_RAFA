
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

/**
 * Acesso ao banco SQLite da agenda.
 */
public final class DatabaseConnection {

    private static final String URL_PADRAO = "jdbc:sqlite:agenda.db";

    private DatabaseConnection() {
    }

    // Permite utilizar outro banco durante os testes.
    private static String obterUrlBanco() {
        return System.getProperty("agenda.database.url", URL_PADRAO);
    }

    // Conectar ao banco
    public static Connection conectar() throws SQLException {

        Connection conn = DriverManager.getConnection(obterUrlBanco());

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }

        return conn;
    }

    // Inicializar banco e criar tabelas
    public static void inicializarBanco() throws SQLException {

        String compromissos =
                "CREATE TABLE IF NOT EXISTS compromissos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL CHECK(length(trim(titulo)) > 0),"
                + "data_hora TEXT NOT NULL,"
                + "descricao TEXT,"
                + "local TEXT NOT NULL CHECK(length(trim(local)) > 0)"
                + ")";

        String usuarios =
                "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL CHECK(length(trim(nome)) > 0),"
                + "email TEXT NOT NULL COLLATE NOCASE UNIQUE,"
                + "senha_hash TEXT NOT NULL"
                + ")";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(compromissos);
            stmt.execute(usuarios);
        }
    }

    // ==============================
    // OPERAÇÕES COM COMPROMISSOS
    // ==============================

    // Inserir compromisso
    public static long inserirCompromisso(Compromisso c)
            throws SQLException {

        if (c == null) {
            throw new IllegalArgumentException(
                    "O compromisso não pode ser nulo."
            );
        }

        if (c.getDataHora() == null) {
            throw new IllegalArgumentException(
                    "A data e hora do compromisso são obrigatórias."
            );
        }

        String sql = "INSERT INTO compromissos "
                + "(titulo, data_hora, descricao, local) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDataHora().toString());
            ps.setString(3, c.getDescricao());
            ps.setString(4, c.getLocal());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }

            throw new SQLException(
                    "SQLite não retornou o ID do compromisso."
            );
        }
    }

    // Atualizar compromisso
    public static boolean atualizarCompromisso(Compromisso c)
            throws SQLException {

        if (c == null || c.getDataHora() == null) {
            throw new IllegalArgumentException(
                    "Os dados do compromisso são inválidos."
            );
        }

        String sql = "UPDATE compromissos "
                + "SET titulo=?, data_hora=?, descricao=?, local=? "
                + "WHERE id=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDataHora().toString());
            ps.setString(3, c.getDescricao());
            ps.setString(4, c.getLocal());
            ps.setInt(5, c.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // Remover compromisso
    public static boolean removerCompromisso(int id)
            throws SQLException {

        String sql = "DELETE FROM compromissos WHERE id=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    // Listar compromissos
    public static List<Compromisso> listarCompromissos()
            throws SQLException {

        List<Compromisso> lista = new ArrayList<>();

        String sql = "SELECT id, titulo, data_hora, descricao, local "
                + "FROM compromissos ORDER BY data_hora";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                lista.add(new Compromisso(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        LocalDateTime.parse(rs.getString("data_hora")),
                        rs.getString("descricao"),
                        rs.getString("local")
                ));
            }
        }

        return lista;
    }

    // ==============================
    // OPERAÇÕES COM USUÁRIOS
    // ==============================

    // Inserir usuário
    public static void inserirUsuario(
            String nome, String email, String senhaHash)
            throws SQLException {

        if (nome == null || nome.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || senhaHash == null || senhaHash.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Nome, e-mail e hash da senha são obrigatórios."
            );
        }

        String sql = "INSERT INTO usuarios "
                + "(nome, email, senha_hash) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome.trim());
            ps.setString(2, email.trim());
            ps.setString(3, senhaHash);

            ps.executeUpdate();
        }
    }

    // Converter resultado do banco em objeto Usuario
    private static Usuario mapearUsuario(ResultSet rs)
            throws SQLException {

        return new Usuario(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha_hash")
        );
    }

    // Buscar usuário por nome
    public static Usuario buscarUsuarioPorNome(String nome)
            throws SQLException {

        String sql = "SELECT id, nome, email, senha_hash "
                + "FROM usuarios WHERE nome=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }

        return null;
    }

    // Buscar usuário por e-mail
    public static Usuario buscarUsuarioPorEmail(String email)
            throws SQLException {

        String sql = "SELECT id, nome, email, senha_hash "
                + "FROM usuarios WHERE email=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }

        return null;
    }

    // Atualizar nome do usuário
    public static boolean atualizarNomeUsuario(
            String email, String novoNome) throws SQLException {

        if (email == null || email.trim().isEmpty()
                || novoNome == null || novoNome.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "E-mail e novo nome são obrigatórios."
            );
        }

        String sql = "UPDATE usuarios SET nome=? WHERE email=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoNome.trim());
            ps.setString(2, email.trim());

            return ps.executeUpdate() > 0;
        }
    }

    // Atualizar senha do usuário
    public static boolean atualizarSenhaUsuario(
            String email, String novaSenhaHash) throws SQLException {

        if (email == null || email.trim().isEmpty()
                || novaSenhaHash == null
                || novaSenhaHash.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "E-mail e hash da senha são obrigatórios."
            );
        }

        String sql = "UPDATE usuarios SET senha_hash=? WHERE email=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novaSenhaHash);
            ps.setString(2, email.trim());

            return ps.executeUpdate() > 0;
        }
    }

    // Remover usuário
    public static boolean removerUsuario(String email)
            throws SQLException {

        String sql = "DELETE FROM usuarios WHERE email=?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            return ps.executeUpdate() > 0;
        }
    }

    // Listar usuários
    public static List<Usuario> listarUsuarios()
            throws SQLException {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT id, nome, email, senha_hash "
                + "FROM usuarios ORDER BY nome";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }

        return lista;
    }
}