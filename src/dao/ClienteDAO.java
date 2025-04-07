package dao;

import conexao.Conexao;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações com a tabela clientes.
 */
public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO() {
        try {
            this.conexao = Conexao.getConexao();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    /**
     * Insere um novo cliente no banco.
     */
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, cpf, email) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getCpf());
        stmt.setString(3, cliente.getEmail());
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Exclui um cliente pelo ID.
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Lista todos os clientes do banco.
     */
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Cliente c = new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("email")
            );
            lista.add(c);
        }

        rs.close();
        stmt.close();

        return lista;
    }
}
