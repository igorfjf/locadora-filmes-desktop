package tela;

import conexao.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaFilme extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtCategoria;
    private JTextField txtAno;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaFilme() {
        setTitle("Cadastro de Filmes");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com campos
        JPanel painelCadastro = new JPanel();
        painelCadastro.setLayout(new FlowLayout());

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField(12);

        JLabel lblCategoria = new JLabel("Categoria:");
        txtCategoria = new JTextField(10);

        JLabel lblAno = new JLabel("Ano:");
        txtAno = new JTextField(5);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar Tabela");
        JButton btnExcluir = new JButton("Excluir Selecionado");

        painelCadastro.add(lblTitulo);
        painelCadastro.add(txtTitulo);
        painelCadastro.add(lblCategoria);
        painelCadastro.add(txtCategoria);
        painelCadastro.add(lblAno);
        painelCadastro.add(txtAno);
        painelCadastro.add(btnCadastrar);
        painelCadastro.add(btnAtualizar);
        painelCadastro.add(btnExcluir);

        // Tabela
        modelo = new DefaultTableModel(new String[]{"ID", "Título", "Categoria", "Ano"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelCadastro, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrarFilme());
        btnAtualizar.addActionListener(e -> carregarTabela());
        btnExcluir.addActionListener(e -> excluirFilme());

        carregarTabela();
        setVisible(true);
    }

    private void cadastrarFilme() {
        String titulo = txtTitulo.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String anoStr = txtAno.getText().trim();

        if (titulo.isEmpty() || categoria.isEmpty() || anoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        int ano;
        try {
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ano deve ser um número válido.");
            return;
        }

        try (Connection con = Conexao.getConexao()) {
            String sql = "INSERT INTO filmes (titulo, categoria, ano) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, titulo);
            stmt.setString(2, categoria);
            stmt.setInt(3, ano);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Filme cadastrado com sucesso!");
            txtTitulo.setText("");
            txtCategoria.setText("");
            txtAno.setText("");
            carregarTabela();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar filme: " + e.getMessage());
        }
    }

    private void carregarTabela() {
        modelo.setRowCount(0); // limpa tabela

        try (Connection con = Conexao.getConexao()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM filmes");

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String categoria = rs.getString("categoria");
                int ano = rs.getInt("ano");
                modelo.addRow(new Object[]{id, titulo, categoria, ano});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados.");
        }
    }

    private void excluirFilme() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um filme para excluir.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = (int) tabela.getValueAt(linhaSelecionada, 0);

            try (Connection con = Conexao.getConexao()) {
                String sql = "DELETE FROM filmes WHERE id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Filme excluído.");
                carregarTabela();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir filme.");
            }
        }
    }
}
