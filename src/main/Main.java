package main;

import tela.TelaPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Usar o visual do sistema operacional
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println("Não foi possível aplicar o visual do sistema.");
            }

            new TelaPrincipal(); // Abre a tela principal
        });
    }
}
