package tela;

import conexao.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaConsulta extends JFrame {
    private final JTextField campoFiltro = new JTextField();
    private final JTable tabela = new JTable();
    private final DefaultTableModel modelo = new DefaultTableModel();
    private final JButton btnBuscar = new JButton("Buscar");

    public TelaConsulta() {
        setTitle("Consulta de Filmes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        configurarEventos();

        setVisible(true);
    }

    /**
     * Inicializa os elementos visuais da tela.
     */
    private void inicializarComponentes() {
        // Painel de busca
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Buscar por título"));
        painelTopo.setPreferredSize(new Dimension(600, 60));

        painelTopo.add(campoFiltro, BorderLayout.CENTER);
        painelTopo.add(btnBuscar, BorderLayout.EAST);

        // Configura a tabela
        modelo.setColumnIdentifiers(new String[]{"ID", "Título", "Categoria", "Ano"});
        tabela.setModel(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Configura os eventos da interface.
     */
    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscar());
    }

    /**
     * Executa a busca no banco de dados conforme o filtro inserido.
     */
    private void buscar() {
        String filtro = campoFiltro.getText().trim();
        modelo.setRowCount(0); // limpa resultados anteriores

        if (filtro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um título para pesquisar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM filmes WHERE titulo LIKE ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");
            ResultSet rs = stmt.executeQuery();

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("categoria"),
                        rs.getInt("ano")
                });
            }

            if (!encontrou) {
                JOptionPane.showMessageDialog(this, "Nenhum filme encontrado com esse título.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar filmes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
