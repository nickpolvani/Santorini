package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends ActivePanel {

    private final GUI gui;
    private ActivePanel activePanel;

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
        activePanel = new ChooseNicknamePanel(gui);
        this.add(activePanel, c);
    }

    public void changePanel() {
        this.remove(activePanel);
        activePanel = new SelectLobbySizePanel(gui);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.PAGE_START;
        activePanel = new SelectLobbySizePanel(gui);
        this.add(activePanel, c);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    protected void showMessage(String message) {
        activePanel.showMessage(message);
    }

    @Override
    public void noReply() {
        activePanel.noReply();
    }
}
