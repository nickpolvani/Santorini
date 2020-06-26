package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends ActivePanel {

    private final GUI gui;
    private ActivePanel choosePanel;

    public WelcomePanel(GUI gui) {
        super();
        this.gui = gui;
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        this.add(new WelcomeImg(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        choosePanel = new ChooseNicknamePanel(gui);
        this.add(choosePanel, c);
    }

    public void changePanel() {
        this.remove(choosePanel);
        choosePanel = new SelectLobbySizePanel(gui);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.PAGE_START;
        choosePanel = new SelectLobbySizePanel(gui);
        this.add(choosePanel, c);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    protected void showMessage(String message) {
        choosePanel.showMessage(message);
    }

    @Override
    public void noReply() {
        choosePanel.noReply();
    }
}
