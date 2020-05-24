package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LooserPanel extends ActivePanel {

    LooserPanel() {
        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/SantoriniBoard.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePath += "loose.jpg";
        ImageIcon image = new ImageIcon(imagePath);
        image = new ImageIcon(image.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH));
        this.add(new JLabel(image));
    }

}
