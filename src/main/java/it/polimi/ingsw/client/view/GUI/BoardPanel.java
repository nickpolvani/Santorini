package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Image img;

    public BoardPanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public BoardPanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    }
}
