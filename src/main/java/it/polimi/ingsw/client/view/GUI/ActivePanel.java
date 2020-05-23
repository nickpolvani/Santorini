package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.Options;

import javax.swing.*;
import java.awt.*;

public class ActivePanel extends JPanel {

    protected JLabel textLabel = new JLabel("", SwingConstants.CENTER);

    protected Options currentOptions;

    public void setCurrentOptions(Options currentOptions) {
        this.currentOptions = currentOptions;
    }

    protected void showMessage(String message) {
        this.textLabel.setText(message);
    }

    public ActivePanel() {
        textLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
    }

}
