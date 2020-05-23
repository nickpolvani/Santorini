package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class WinnerPanel extends ActivePanel {

    public WinnerPanel(String nickname, GUI gui) {
        String text;
        if (nickname.equals(gui.getNickname())) {
            text = nickname + " has won.";
        } else {
            text = "YOU WON!!!";
        }
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 26));
        this.add(label);
    }
}
