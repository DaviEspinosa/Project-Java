package View;

import java.awt.Color;

import javax.swing.JPanel;

public class JanelaProdutos extends JPanel {
    private Color cinza = new Color(128, 128, 128);

    public JanelaProdutos() {
        this.setBounds(100, 120, 1200, 900);
        this.setBackground(cinza);
    }
}
