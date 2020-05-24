package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WinnerPanel extends ActivePanel {

    public WinnerPanel(String nickname, GUI gui) {

        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/SantoriniBoard.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon image;
        if (nickname.equals(gui.getNickname())) {
            imagePath += "win.jpg";
            image = new ImageIcon(imagePath);
            image = new ImageIcon(image.getImage().getScaledInstance(626, 417, Image.SCALE_SMOOTH));
        } else {
            imagePath += "loose.jpg";
            image = new ImageIcon(imagePath);
            image = new ImageIcon(image.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH));
        }

        JLabel label = new JLabel(image);
        this.add(label);
    }
}
