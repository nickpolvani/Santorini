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

/**
 * graphical-user interface, this class controls all the panels shown to the user, and notifies
 * client-side controller when the user performs some operation
 */
public class GUI extends View {

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Controller controller;
    protected JFrame frame = new JFrame("Santorini");
    private ActivePanel activePanel;
    private Options currentOptions;
    private boolean gameStarted;
    private GodDescription playerGod;


    public GUI(Controller controller) {
        this.board = null;
        this.controller = controller;
    }

    /**
     * if players have completed the game setup, the GamePanel is updated when a new Option is available
     *
     * @param currentOption current options the player has
     */
    @Override
    public void setCurrentOption(Options currentOption) {
        this.currentOptions = currentOption;
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
        ActivePanel activePanel = new WelcomePanel(this);
        this.activePanel = activePanel;
        frame.setContentPane(activePanel);
        frame.setMinimumSize(new Dimension(850, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void close() {
        setActivePanel(new ConnectionClosedPanel());
        frame.validate();
    }

    private void setActivePanel(ActivePanel activePanel) {
        this.activePanel = activePanel;
        frame.setContentPane(activePanel);
    }

    /**
     * notifies user if he has lost or won the game
     *
     * @param nickname is the nickname of the winner
     */
    public void notifyWinner(String nickname) {
        setActivePanel(new GameOverPanel(nickname, this));
        frame.validate();
    }

    /**
     * shows buttons to let the first user entering the lobby
     * decide with how many opponents he wants to play
     */
    public void chooseLobbySize() {
        if (activePanel instanceof WelcomePanel) {
            ((WelcomePanel) activePanel).changePanel();
        }
    }

    /**
     * this method is called only when there are three players and one of them looses while the other two continue
     * playing. The user that lost the game remains connected in spectator mode
     *
     * @param nickname nickname of player that lost
     */
    public void notifyLooser(String nickname) {
        if (nickname.equals(this.getNickname())) {
            new WinLooseJFrame("Sorry, you lost the game.<br>You can keep watching the game.<br>Otherwise close the application!");
        } else {
            new WinLooseJFrame(nickname + " has lost!!<br>You can still continue playing a one-on-one game<br>Good luck!!");
        }
    }

    /**
     * used during game setup when the user has to wait
     */
    public void noReply() {
        activePanel.noReply();
    }

    public void setup(Operation currentOperation) {
        if (currentOperation == Operation.SELECT_LOBBY_SIZE) {
            setActivePanel(new SelectLobbySizePanel(this));
        }
    }

    /**
     * Method called after setup phase
     * the tiles available to the user are highlighted and a message is displayed
     *
     * @param currentOperation current operation the user has to do
     * @param tilesToChoose    tiles available to perform the current operation
     */
    public void showAvailableTiles(Operation currentOperation, Collection<Tile.IndexTile> tilesToChoose) {
        if (!gameStarted) throw new IllegalStateException();
        GamePanel gamePanel = (GamePanel) activePanel;
        showMessage("The tiles in which you can " + currentOperation + " are highlighted");
        gamePanel.highlight(tilesToChoose);
        gamePanel.getBoardPanel().setDoubleSelection(false);
    }

    /**
     * method called to let the player decide where he wants to place his workers in the board
     *
     * @param tilesToChoose tiles available for the user to place his workers
     * @param color         the player's color
     */
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

    /**
     * lets the player perform a build operation with Poseidon
     *
     * @param tilesToChoose tiles available to perform a build operation with Poseidon
     */
    public void poseidonBuild(Collection<Tile.IndexTile> tilesToChoose) {
        ((GamePanel) activePanel).getBoardPanel().enablePoseidonBuild(tilesToChoose);
    }

    /**
     * shows list of gods to the user
     *
     * @param godsToChoose gods available to the user
     */
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
