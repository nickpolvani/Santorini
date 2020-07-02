package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class WinLooseJFrame extends JFrame {
    public WinLooseJFrame(String message) {
        super("WinLoose JFrame");
        JLabel textLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div>");
        textLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 5, 10);
        c.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(textLabel, c);
        JButton button = new JButton("Close");
        button.addActionListener(e -> {
            this.dispose();
        });
        c = new GridBagConstraints();
        c.gridy = 1;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(button, c);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }
}
