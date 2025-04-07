package tela;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Locadora de Filmes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton btnFilmes = new JButton("Cadastro de Filmes");
        JButton btnClientes = new JButton("Cadastro de Clientes");
        JButton btnEmprestimos = new JButton("Empréstimos");
        JButton btnConsulta = new JButton("Consulta/Listagem");

        btnFilmes.addActionListener(e -> new TelaFilme());
        btnClientes.addActionListener(e -> new TelaCliente());
        btnEmprestimos.addActionListener(e -> new TelaEmprestimo());
        btnConsulta.addActionListener(e -> new TelaConsulta());

        painel.add(btnFilmes);
        painel.add(btnClientes);
        painel.add(btnEmprestimos);
        painel.add(btnConsulta);

        add(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal());
    }
}
