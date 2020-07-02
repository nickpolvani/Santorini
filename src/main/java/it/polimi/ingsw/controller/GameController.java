package it.polimi.ingsw.controller;

import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.action.SetupActionHandler;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.WinLooseOption;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupGodsTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.controller.turn.Turn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utilities.MessageType;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class that changes the game state
 */
public class GameController extends Observable<Options> implements Observer<GameAction> {

    private final GameState gameState;
    private final Lobby lobby;
    private final Logger logger = Logger.getLogger("Server");
    private Turn turn;
    private ActionHandler actionHandler;

    /**
     * Default constructor
     *
     * @param gameState instance of the game controlled
     * @param lobby     lobby that hosts players for this game
     */
    public GameController(GameState gameState, Lobby lobby) {
        logger.debug("Start to initialize the controller");
        this.gameState = gameState;
        this.lobby = lobby;
        this.turn = new SetupGodsTurn(gameState.getPlayers().get(0), this);
        actionHandler = new SetupActionHandler((SetupGodsTurn) turn);
    }

    public void setActionHandler(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn nextTurn) {
        if ((this.turn instanceof SetupGodsTurn && !(nextTurn instanceof SetupWorkersTurn)) ||
                (this.turn instanceof SetupWorkersTurn && !(nextTurn instanceof BasicTurn)))
            throw new IllegalStateException();
        this.turn = nextTurn;
    }

    /**
     * @param currentPlayer the player currently playing this turn
     * @return the player who has to play his gameTurn
     */
    public Player getNextPlayer(Player currentPlayer) {
        int numOfCurrentPlayer = gameState.getPlayers().indexOf(currentPlayer);
        try {
            return gameState.getPlayers().get(numOfCurrentPlayer + 1);
        } catch (IndexOutOfBoundsException e) {
            return gameState.getPlayers().get(0);
        }
    }

    public void start() {
        this.turn.start();
        logger.debug("The controller started");
    }

    /**
     * method called when a player wins, it ends the game and closes the lobby.
     *
     * @param winner nickname of the winner
     */
    public void hasWon(Player winner) {
        logger.debug("The player " + winner.getNickname() + " has won!");
        String message = (MessageType.WIN + winner.getNickname());
        notify(new WinLooseOption(winner.getNickname(), message, gameState.getIslandBoard().clone()));
        try {
            Thread.sleep(15000);
            Server.getInstance().closeLobby(lobby);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * method called when a player looses, it checks if the game is over, and in case notifies
     * the players the game is over
     *
     * @param looser nickname of the looser
     */
    public void hasLost(Player looser) {
        logger.debug("The player " + looser.getNickname() + " has lost!");
        String message = (MessageType.LOST + looser.getNickname());
        List<Player> players = gameState.getPlayers();
        if (players.size() == 2) {
            Player winner = players.stream().filter(p -> !p.equals(looser)).collect(Collectors.toList()).get(0);
            hasWon(winner);
        } else if (players.size() == 3) {
            notify(new WinLooseOption(looser.getNickname(), message, gameState.getIslandBoard().clone()));
            for (Worker w : looser.getWorkers()) {
                try {
                    gameState.getIslandBoard().getTile(w.getIndexTile()).setCurrentWorker(null);
                } catch (AlreadyOccupiedException e) {
                    e.printStackTrace();
                }
            }
            if (turn.getCurrentPlayer().equals(looser)) turn.switchTurn();
            gameState.getPlayers().remove(looser);
        } else {
            throw new IllegalStateException();
        }
    }

    private Player findPlayer(String nickname) {
        for (Player p : getGameState().getPlayers()) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }
        return null;
    }

    /**
     * changes the game state according to the logic implemented in a.execute
     *
     * @param a the game action generated by user interaction client-side
     */
    @Override
    public synchronized void update(GameAction a) {
        if (!(a.getNickname().equals(getTurn().getCurrentPlayer().getNickname()) &&
                a.isCompatible(turn.getCurrentOperation()))) throw new IllegalArgumentException();
        try {
            actionHandler.execute(a, findPlayer(a.getNickname()));
            turn.endCurrentOperation();
        } catch (DomeAlreadyPresentException | AlreadyOccupiedException | AlreadySetException e) {
            e.printStackTrace();
        }
    }
}