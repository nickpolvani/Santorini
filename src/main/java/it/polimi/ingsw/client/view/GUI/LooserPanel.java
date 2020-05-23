package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class LooserPanel extends ActivePanel {

    LooserPanel() {
        JLabel label = new JLabel("You Lost");
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 26));
        this.add(label);
    }

}
