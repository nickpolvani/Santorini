package it.polimi.ingsw.controller;

import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.action.SetupActionHandler;
import it.polimi.ingsw.bean.options.MessageOption;
import it.polimi.ingsw.bean.options.Options;
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
import it.polimi.ingsw.utilities.MessageType;
import org.apache.log4j.Logger;

public class GameController extends Observable<Options> implements Observer<GameAction> {

    private Turn turn;

    private final GameState gameState;

    private ActionHandler actionHandler;

    private final Lobby lobby;

    private final Logger logger = Logger.getLogger("Server");

    /**
     * Default constructor
     */
    public GameController(GameState gameState, Lobby lobby) {
        logger.debug("Start to initialize the controller");
        this.gameState = gameState;
        this.lobby = lobby;
        //TODO implement random choice of challenger
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

    public void hasWon(Player winner) {
        logger.debug("The player " + winner + "has won!");
        String notifyMessage = (MessageType.WIN + winner.getNickname());
        notify(new MessageOption(winner.getNickname(), notifyMessage, Operation.MESSAGE_NO_REPLY));
        lobby.close();
    }

    public void hasLost(Player looser) {
        logger.debug("The player " + looser + "has lost!");
        String notifyMessage = (MessageType.LOST + looser.getNickname());
        notify(new MessageOption(looser.getNickname(), notifyMessage, Operation.MESSAGE_NO_REPLY));
        gameState.getPlayers().remove(looser);
        if (lobby.size == 2) {
            Player winner = null;
            for (Player p : getGameState().getPlayers()) {
                if (!p.equals(looser)) winner = p;
            }
            assert winner != null;
            hasWon(winner);
        } else {
            for (Worker w : looser.getWorkers()) {
                try {
                    gameState.getIslandBoard().getTile(w.getIndexTile()).setCurrentWorker(null);
                } catch (AlreadyOccupiedException e) {
                    e.printStackTrace();
                }
            }
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