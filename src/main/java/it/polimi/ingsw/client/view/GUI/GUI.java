package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;


public class GUI extends View {

    JFrame frame = new JFrame("Santorini");
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ActivePanel activePanel;
    private final Controller controller;
    private Options currentOptions;
    private boolean gameStarted;
    private GodDescription playerGod;


    public GUI(Controller controller) {
        this.board = null;
        this.controller = controller;
    }

    @Override
    public void setCurrentOption(Options currentOption) {
        this.currentOptions = currentOption;
        boolean isMyTurn = controller.isMyTurn();
        if (gameStarted && activePanel instanceof GamePanel) {
            ((GamePanel) activePanel).getBoardPanel().removeHighlight();
            ((GamePanel) activePanel).getBoardPanel().setDoubleSelection(false);
            ((GamePanel) activePanel).showChoiceButtons(false);
        }
    }


    @Override
    public void showMessage(String message) {
        activePanel.showMessage(message);
    }

    @Override
    public void updateBoard(IslandBoard board) {
        this.board = board;
        if (!gameStarted) {
            gameStarted = true;
            setActivePanel(new GamePanel(this));
        }
        ((GamePanel) activePanel).getBoardPanel().updateBoard(board);
    }


    @Override
    public void start() {
        ActivePanel activePanel = new ChooseNicknamePanel(this);
        this.activePanel = activePanel;
        frame.setSize(screenSize);
        frame.setResizable(false);
        frame.add(activePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void close() {
        //
    }

    private void setActivePanel(ActivePanel activePanel) {
        frame.remove(this.activePanel);
        this.activePanel = activePanel;
        frame.add(activePanel);
    }

    public void notifyWinner(String nickname) {
        setActivePanel(new GameOverPanel(nickname, this));
        frame.validate();
    }

    public void notifyLooser(String nickname) {
        if (nickname.equals(this.getNickname())) {
            TextArea textArea = new TextArea(3, 45);
            textArea.setText("Sorry, you lost the game.\nYou can keep watching the game." +
                    "\nOtherwise close the application!");
            textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
            textArea.setEditable(false);
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 1;
            c.gridx = 1;
            activePanel.add(textArea, c);
            frame.validate();
        } else {
            showMessage(nickname + " has lost");
        }
    }

    public void noReply() {
        activePanel.noReply();
    }

    public void setup(Operation currentOperation) {
        if (currentOperation == Operation.SELECT_LOBBY_SIZE) {
            setActivePanel(new SelectLobbySizePanel(this));
        }
    }

    public void showAvailableTiles(Operation currentOperation, Collection<Tile.IndexTile> tilesToChoose) {
        if (!gameStarted) throw new IllegalStateException();
        GamePanel gamePanel = (GamePanel) activePanel;
        showMessage("The tiles in which you can " + currentOperation + " are highlighted");
        gamePanel.highlight(tilesToChoose);
        gamePanel.getBoardPanel().setDoubleSelection(false);
    }

    public void placeWorkers(Collection<Tile.IndexTile> tilesToChoose, Color color) {
        if (!gameStarted) throw new IllegalStateException();
        GamePanel gamePanel = (GamePanel) activePanel;
        switch (color) {
            case RED:
                gamePanel.setPlayerColor(java.awt.Color.RED);
                break;
            case BLUE:
                gamePanel.setPlayerColor(java.awt.Color.BLUE);
                break;
            case YELLOW:
                gamePanel.setPlayerColor(java.awt.Color.YELLOW);
                break;
        }
        gamePanel.getBoardPanel().setDoubleSelection(true);
        gamePanel.showMessage("Choose two tiles to place your workers");
        gamePanel.highlight(tilesToChoose);
    }

    public void poseidonBuild(Collection<Tile.IndexTile> tilesToChoose) {
        ((GamePanel) activePanel).getBoardPanel().enablePoseidonBuild(tilesToChoose);
    }

    public void chooseGod(List<GodDescription> godsToChoose) {
        setActivePanel(new ChooseGodPanel(this, godsToChoose));
    }

    public void choose() {
        GamePanel gamePanel = (GamePanel) activePanel;
        gamePanel.showChoiceButtons(true);
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    GodDescription getPlayerGod() {
        return this.playerGod;
    }

    public void setPlayerGod(GodDescription playerGod) {
        this.playerGod = playerGod;
    }
}
