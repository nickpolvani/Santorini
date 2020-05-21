package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.Options;

import javax.swing.*;

public class ActivePanel extends JPanel {

    protected JLabel textLabel = new JLabel("", SwingConstants.CENTER);

    protected Options currentOptions;

    public void setCurrentOptions(Options currentOptions) {
        this.currentOptions = currentOptions;
    }

    protected void showMessage(String message) {
        this.textLabel.setText(message);
    }

}
