package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel currently showing in Frame
 */
public abstract class ActivePanel extends JPanel {

    /**
     * label used to display text messages to the user
     */
    protected JLabel textLabel = new JLabel("", SwingConstants.CENTER);

    public ActivePanel() {
        textLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
    }

    protected void showMessage(String message) {
        this.textLabel.setText(message);
    }

    /**
     * method used by panels during game setup, when the user cannot perform any action
     * it is empty and not abstract because many panels do not need to override this
     */
    public void noReply() {
    }
}
