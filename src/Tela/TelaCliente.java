    package tela;

    import dao.ClienteDAO;
    import modelo.Cliente;

    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.sql.SQLException;
    import java.util.List;

    public class TelaCliente extends JFrame {

        private JTextField txtNome, txtCpf, txtEmail;
        private JTable tabela;
        private DefaultTableModel modelo;

        public TelaCliente() {
            setTitle("Cadastro de Clientes");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Painel de entrada de dados
            JPanel painelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
            painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            painelCampos.add(new JLabel("Nome:"));
            txtNome = new JTextField();
            painelCampos.add(txtNome);

            painelCampos.add(new JLabel("CPF:"));
            txtCpf = new JTextField();
            painelCampos.add(txtCpf);

            painelCampos.add(new JLabel("Email:"));
            txtEmail = new JTextField();
            painelCampos.add(txtEmail);

            // Botão de cadastrar
            JButton btnCadastrar = new JButton("Cadastrar");
            btnCadastrar.addActionListener(this::cadastrarCliente);
            painelCampos.add(btnCadastrar);

            // Botão de excluir
            JButton btnExcluir = new JButton("Excluir");
            btnExcluir.addActionListener(e -> excluirCliente());
            painelCampos.add(btnExcluir);

            add(painelCampos, BorderLayout.NORTH);

            // Tabela de exibição
            modelo = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Email"}, 0);
            tabela = new JTable(modelo);
            JScrollPane scroll = new JScrollPane(tabela);
            add(scroll, BorderLayout.CENTER);

            carregarTabela();

            setVisible(true);
        }

        // Método para cadastrar um novo cliente
        private void cadastrarCliente(ActionEvent e) {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String email = txtEmail.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            Cliente c = new Cliente(0, nome, cpf, email);

            try {
                ClienteDAO dao = new ClienteDAO();
                dao.inserir(c);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                txtNome.setText("");
                txtCpf.setText("");
                txtEmail.setText("");
                carregarTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage());
            }
        }

        // Método para carregar todos os clientes na tabela
        private void carregarTabela() {
            modelo.setRowCount(0);

            try {
                ClienteDAO dao = new ClienteDAO();
                List<Cliente> lista = dao.listarTodos();
                for (Cliente c : lista) {
                    modelo.addRow(new Object[]{
                            c.getId(),
                            c.getNome(),
                            c.getCpf(),
                            c.getEmail()
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
            }
        }

        // Método para excluir cliente selecionado
        private void excluirCliente() {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                int id = (int) tabela.getValueAt(linha, 0);

                try {
                    ClienteDAO dao = new ClienteDAO();
                    dao.excluir(id);
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
                    carregarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + ex.getMessage());
                }
            }
        }
    }
