package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOverPanel extends ActivePanel {

    public GameOverPanel(String nickname, GUI gui) {

        String imagePath = null;
        ImageIcon image = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/";
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (nickname.equals(gui.getNickname())) {
            imagePath += "win.jpg";
            image = new ImageIcon(imagePath);
            image = new ImageIcon(image.getImage().getScaledInstance(626, 417, Image.SCALE_SMOOTH));
        } else {
            imagePath += "loose.gif";
            image = new ImageIcon(imagePath);
            image = new ImageIcon(image.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        }

        JLabel label = new JLabel(image);
        JTextArea textArea = new JTextArea("Close the window and restart the application to begin a new game.\n" +
                "Moreover you will be disconnected in 30seconds");
        textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        textArea.setEditable(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(textArea, c);
        c.gridy = 1;
        this.add(label, c);
    }

}
