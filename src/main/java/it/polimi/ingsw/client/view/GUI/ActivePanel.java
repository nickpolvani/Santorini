package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;

public class ActivePanel extends JPanel {

    protected JLabel textLabel = new JLabel("", SwingConstants.CENTER);

    protected void showMessage(String message) {
        this.textLabel.setText(message);
    }

}
