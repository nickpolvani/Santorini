package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_ENTER;

public class ChooseNicknamePanel extends ActivePanel {

    private final GUI gui;
    private JTextField textField = new JTextField();
    private JButton submitButton = new JButton("SUBMIT");
    public ChooseNicknamePanel(GUI gui) {
        super();

        this.setLayout(new GridBagLayout());
        this.gui = gui;
        textField.setSize(100, 20);
        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == VK_ENTER) {
                    System.out.println(textField.getText());
                    gui.notify(textField.getText());
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //AUTO IMPLEMENTED
            }


            @Override
            public void keyReleased(KeyEvent e) {
                //AUTO IMPLEMENTED
            }
        });

        submitButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        submitButton.addActionListener(e -> {
            System.out.println(textField.getText());
            gui.notify(textField.getText());

        });


        GridBagConstraints c = new GridBagConstraints();
        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/WelcomeToSantorini.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon image = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(image);

        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        this.add(imageLabel, c);

        c.insets = new Insets(0, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 1;
        c.ipadx = 40;
        c.ipady = 20;
        this.add(textLabel, c);

        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 300;
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        this.add(textField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 40;
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
