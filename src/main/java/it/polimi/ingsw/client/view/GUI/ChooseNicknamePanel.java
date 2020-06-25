package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class ChooseNicknamePanel extends ActivePanel {

    private final JTextField textField = new JTextField();
    private final JButton submitButton = new JButton("SUBMIT");

    public ChooseNicknamePanel(GUI gui) {
        super();
        this.setLayout(new GridBagLayout());
        textField.setSize(100, 20);
        submitButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        submitButton.addActionListener(e -> {
            System.out.println(textField.getText());
            gui.notify(textField.getText());
        });

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 20, 0, 20);
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 20;
        c.weighty = 0.4;
        this.add(textLabel, c);

        c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.gridy = 1;
        c.ipadx = 300;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.3;
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        this.add(textField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(0, 130, 20, 130);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 2;
        c.ipadx = 40;
        c.weighty = 0.1;
        this.add(submitButton, c);
    }

    @Override
    public void noReply() {
        textField.setVisible(false);
        submitButton.setVisible(false);
        this.remove(textField);
        this.remove(submitButton);
    }
}
