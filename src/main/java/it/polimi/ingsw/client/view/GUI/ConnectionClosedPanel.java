package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Panel displayed when the connection with server is closed
 */
public class ConnectionClosedPanel extends ActivePanel {

    public ConnectionClosedPanel() {


        String imagePath = null;
        ImageIcon image;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/thunderbolt.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = new ImageIcon(imagePath);
        image = new ImageIcon(image.getImage().getScaledInstance(220, 276, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(image);
        JTextArea textArea = new JTextArea("Connection closed server-side.\nClose the window and restart the application to begin a new game.");
        textArea.setFont(new Font("Helvetica Neue", Font.BOLD, 35));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.RED);
        textArea.setOpaque(true);
        textArea.setEditable(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(label, c);
        c.insets = new Insets(15, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        this.add(textArea, c);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
    }

}
