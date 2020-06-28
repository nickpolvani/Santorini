package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.NotifyPlayerGod;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utilities.MessageType;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * turn used when players have to choose their god card
 */
public class SetupGodsTurn extends Observable<Options> implements SetupTurn {
    private final Player challenger;
    private final Queue<Operation> turnOperations;
    private final GameController controller;
    private final List<GodDescription> selectedGods = new ArrayList<>();
    private final Logger logger = Logger.getLogger("Server");
    private Player currentPlayer;
    private boolean challengerGodsChosen = false;
    private boolean started = false;


    public SetupGodsTurn(Player challenger, GameController controller) {
        this.challenger = challenger;
        this.currentPlayer = challenger;
        this.controller = controller;
        turnOperations = new LinkedList<>();
        turnOperations.add(Operation.CHOOSE_GOD);
        logger.debug("SetupGodsTurn initialized!");
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Operation getCurrentOperation() {
        return turnOperations.peek();
    }

    @Override
    public void endCurrentOperation() {
        this.turnOperations.poll();
        if (turnOperations.isEmpty()) {
            if (!challengerGodsChosen) {
                challengerGodsChosen = true;
            }
            switchTurn();
        } else {
            notifyOptions();
        }
    }

    /**
     * changes the current player, when all the players have chosen their god card, the turn's logic passes to
     * SetupWorkersTurn
     */
    @Override
    public void switchTurn() {
        currentPlayer = controller.getNextPlayer(currentPlayer);

        if (currentPlayer == challenger) {
            //When the current player becomes the challenger again, only one god must remain in the selectedGods list.
            if (selectedGods.size() != 1) throw new IllegalStateException();
            challenger.setGod(controller.getGameState().getGodsFactory().getGod(selectedGods.get(0), challenger));
            for (Player p : controller.getGameState().getPlayers()) {
                String message = "Your god for this game is: " + p.getGod().toString();
                notify(new NotifyPlayerGod(p.getNickname(), message, p.getGod().getGodDescription()));
            }
            SetupWorkersTurn setupWorkersTurn = new SetupWorkersTurn(controller, controller.getNextPlayer(currentPlayer), observers);
            clearObserver();
            controller.setActionHandler(new ActionHandler());
            controller.setTurn(setupWorkersTurn);
            setupWorkersTurn.start();
        } else {
            turnOperations.add(Operation.CHOOSE_GOD);
            notifyOptions();
        }
    }

    public Boolean isChallengerGodsChosen() {
        return challengerGodsChosen;
    }

    /**
     * selects gods for the game if the user is the challenger, assigns god to the user otherwise
     *
     * @param inputGods gods chosen by the user, size of inputGods has to be one if the user is not the challenger,
     *                  otherwise it has to be equal to the number of players in this game
     */
    public void handleGodChoice(List<GodDescription> inputGods) {
        if (!isChallengerGodsChosen()) {
            if (selectedGods.containsAll(inputGods)) {
                throw new IllegalArgumentException();
            }
            if (inputGods.size() != controller.getGameState().getPlayers().size()) {
                //Adding this operation provides to the challenger the chance to insert the corrected god list.
                turnOperations.add(Operation.CHOOSE_GOD);

            } else {
                selectedGods.addAll(inputGods);
            }
        } else {
            if (inputGods.size() > 1) throw new IllegalArgumentException();
            currentPlayer.setGod(controller.getGameState().getGodsFactory().getGod(inputGods.get(0), currentPlayer));
            selectedGods.remove(inputGods.get(0));
        }
    }

    private void notifyOptions() {
        Options options;
        if (!challengerGodsChosen) {
            List<GodDescription> gods = new ArrayList<>(Arrays.asList(GodDescription.values()));
            for (GodDescription god : selectedGods) {
                gods.remove(god);
            }
            String message = MessageType.CHOOSE_GOD + ("You're the challenger: choose the Gods for the game.\n" +
                    "Insert the correct number of gods: " + controller.getGameState().getPlayers().size());
            options = new GodOptions(currentPlayer.getNickname(), gods, message);
        } else {
            options = new GodOptions(currentPlayer.getNickname(), selectedGods, MessageType.CHOOSE_GOD);
        }
        notify(options);
    }

    @Override
    public void start() {
        if (isStarted()) throw new IllegalStateException();
        started = true;
        notifyOptions();
        logger.debug("First options has been notified");
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
