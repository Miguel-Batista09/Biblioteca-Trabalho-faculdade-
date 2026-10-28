package br.edu.biblioteca.dao;

import br.edu.biblioteca.modelo.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDao {
    private Connection connection;

    public LivroDao() {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adiciona(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, categoria, ano_publicacao, descricao) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getDescricao());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Livro> getLista() {
        String sql = "SELECT * FROM livros";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Livro> livros = new ArrayList<>();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getLong("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setCategoria(rs.getString("categoria"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setDescricao(rs.getString("descricao"));
                livros.add(livro);
            }
            return livros;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Livro buscaPorId(Long id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getLong("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setCategoria(rs.getString("categoria"));
                    livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                    livro.setDescricao(rs.getString("descricao"));
                    return livro;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void altera(Livro livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, categoria = ?, ano_publicacao = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getDescricao());
            stmt.setLong(6, livro.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Long id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}