package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.utilities.MessageType;

import javax.swing.*;
import java.awt.*;


public class GUI extends View {

    JFrame frame = new JFrame("Santorini");
    private IslandBoard board;
    private ActivePanel gamePanel;
    private SetupPanel setupPanel;  // used to choose nickname
    private SelectLobbySizePanel selectLobbySizePanel;
    private ActivePanel chooseGodPanel;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ActivePanel activePanel;

    public GUI() {
        this.board = null;
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    @Override
    public void showMessage(String message) {
        if (message.equals(MessageType.CHOOSE_LOBBY_SIZE)) {

        }
        activePanel.showMessage(message);
    }

    @Override
    public void updateBoard(IslandBoard board) {

    }

    @Override
    public void readInput() {

    }


    @Override
    public void start() {
        setupPanel = new SetupPanel(this);
        activePanel = setupPanel;

        frame.setSize(screenSize);
        frame.setResizable(false);
        frame.add(activePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void close() {

    }

    private void setActivePanel(ActivePanel activePanel) {
        frame.remove(activePanel);
        this.activePanel = activePanel;
        frame.add(activePanel);
    }

}
