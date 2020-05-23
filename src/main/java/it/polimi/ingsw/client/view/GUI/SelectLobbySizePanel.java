package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLobbySizePanel extends ActivePanel {

    private final JButton b1;
    private final JButton b2;

    public SelectLobbySizePanel(GUI gui) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        b1 = new JButton("2 players");
        b2 = new JButton("3 players");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.notify("2");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.notify("3");
            }
        });

        c.insets = new Insets(20, 20, 20, 20);
        c.ipadx = 40;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;

        this.add(textLabel, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        this.add(b1, c);

        c.gridx = 2;
        c.gridy = 1;
        this.add(b2, c);
    }

    @Override
    public void setCurrentOptions(Options currentOptions) {
        this.currentOptions = currentOptions;
        if (this.currentOptions.getCurrentOperation() == Operation.MESSAGE_NO_REPLY) {
            this.b1.setVisible(false);
            this.b2.setVisible(false);
            this.remove(this.b1);
            this.remove(this.b2);
        }
    }


}
