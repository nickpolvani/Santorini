package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class SelectLobbySizePanel extends ActivePanel {

    private final JButton b1;
    private final JButton b2;

    public SelectLobbySizePanel(GUI gui) {
        super();
        this.setLayout(new GridBagLayout());

        b1 = new JButton("2 Players");
        b2 = new JButton("3 Players");

        b1.addActionListener(e -> gui.notify("2"));
        b2.addActionListener(e -> gui.notify("3"));

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(20, 20, 20, 20);
        this.add(textLabel, c);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 20, 10);
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        b1.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        buttonPanel.add(b1, c);

        c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 20, 0);
        c.gridx = 2;
        c.anchor = GridBagConstraints.CENTER;
        b2.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        buttonPanel.add(b2, c);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridy = 1;
        this.add(buttonPanel, c);
    }

    @Override
    public void noReply() {
        this.b1.setVisible(false);
        this.b2.setVisible(false);
        this.remove(this.b1);
        this.remove(this.b2);
    }
}
