package tela;

import conexao.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaEmprestimo extends JFrame {
    private JComboBox<String> comboCliente, comboFilme;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnEmprestar, btnExcluir;

    public TelaEmprestimo() {
        setTitle("Empréstimo de Filmes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        configurarEventos();
        carregarComboBoxes();
        carregarTabela();

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        comboCliente = new JComboBox<>();
        comboFilme = new JComboBox<>();
        btnEmprestar = new JButton("Emprestar");
        btnExcluir = new JButton("Excluir");

        painelTopo.add(new JLabel("Cliente:"));
        painelTopo.add(comboCliente);
        painelTopo.add(new JLabel("Filme:"));
        painelTopo.add(comboFilme);
        painelTopo.add(btnEmprestar);
        painelTopo.add(btnExcluir);

        modelo = new DefaultTableModel(new String[]{"ID", "Cliente", "Filme", "Data"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Emprestar filme
        btnEmprestar.addActionListener(e -> {
            String cliente = (String) comboCliente.getSelectedItem();
            String filme = (String) comboFilme.getSelectedItem();

            if (cliente == null || filme == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente e um filme.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = Conexao.getConexao()) {
                String sql = "INSERT INTO emprestimos (id_cliente, id_filme, data_emprestimo) " +
                             "VALUES ((SELECT id FROM clientes WHERE nome = ?), " +
                                     "(SELECT id FROM filmes WHERE titulo = ?), CURDATE())";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, cliente);
                stmt.setString(2, filme);
                stmt.executeUpdate();

                carregarTabela();
                JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao emprestar filme: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Excluir empréstimo
        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) tabela.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este empréstimo?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = Conexao.getConexao()) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM emprestimos WHERE id = ?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();

                    carregarTabela();
                    JOptionPane.showMessageDialog(this, "Empréstimo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir empréstimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void carregarComboBoxes() {
        comboCliente.removeAllItems();
        comboFilme.removeAllItems();

        try (Connection conn = Conexao.getConexao()) {
            // Carregar clientes
            PreparedStatement stmtClientes = conn.prepareStatement("SELECT nome FROM clientes ORDER BY nome");
            ResultSet rsClientes = stmtClientes.executeQuery();
            while (rsClientes.next()) {
                comboCliente.addItem(rsClientes.getString("nome"));
            }

            // Carregar filmes
            PreparedStatement stmtFilmes = conn.prepareStatement("SELECT titulo FROM filmes ORDER BY titulo");
            ResultSet rsFilmes = stmtFilmes.executeQuery();
            while (rsFilmes.next()) {
                comboFilme.addItem(rsFilmes.getString("titulo"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar listas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        modelo.setRowCount(0);

        String sql = "SELECT e.id, c.nome AS cliente, f.titulo AS filme, e.data_emprestimo " +
                     "FROM emprestimos e " +
                     "JOIN clientes c ON e.id_cliente = c.id " +
                     "JOIN filmes f ON e.id_filme = f.id";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("cliente"),
                        rs.getString("filme"),
                        rs.getDate("data_emprestimo")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar empréstimos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
