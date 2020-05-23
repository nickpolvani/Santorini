package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class ActivePanel extends JPanel {

    protected JLabel textLabel = new JLabel("", SwingConstants.CENTER);


    protected void showMessage(String message) {
        this.textLabel.setText(message);
    }

    public ActivePanel() {
        textLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
    }

    public void noReply() {
    }

    ;
}
