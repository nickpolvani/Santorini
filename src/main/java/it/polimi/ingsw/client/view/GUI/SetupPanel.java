package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupPanel extends ActivePanel {

    private final GUI gui;
    private JTextField textField = new JTextField();
    private JButton submitButton = new JButton("submit");

    public SetupPanel(GUI gui) {
        super();

        this.setLayout(new GridBagLayout());
        this.gui = gui;
        textField.setSize(100, 20);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(textField.getText());
                gui.notify(textField.getText());

            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 40;
        c.ipady = 20;
        this.add(textLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 300;
        this.add(textField, c);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 40;
        this.add(submitButton, c);
    }


}
