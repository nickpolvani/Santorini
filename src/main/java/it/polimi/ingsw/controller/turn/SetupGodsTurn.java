package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.observer.Observable;
import org.apache.log4j.Logger;

import java.util.*;

public class SetupGodsTurn extends Observable<Options> implements SetupTurn {
    private final Player challenger;
    private final Queue<Operation> turnOperations;
    private final GameController controller;
    private final List<GodDescription> selectedGods = new ArrayList<>();
    private Player currentPlayer;
    private Boolean challengerGodsChosen = false;
    private final Logger logger = Logger.getLogger("Server");
    private boolean started = false;


    public SetupGodsTurn(Player challenger, GameController controller) {
        this.challenger = challenger;
        this.currentPlayer = challenger;
        this.controller = controller;
        turnOperations = new LinkedList<>();
        for (int i = 0; i < controller.getGameState().getPlayers().size(); i++) {
            turnOperations.add(Operation.CHOOSE_GOD);
        }
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

    @Override
    public void switchTurn() {
        currentPlayer = controller.getNextPlayer(currentPlayer);
        if (currentPlayer == challenger) {
            //When the current player becomes the challenger again, only one god must remain in the selectedGods list.
            if (selectedGods.size() != 1) throw new IllegalStateException();
            challenger.setGod(controller.getGameState().getGodsFactory().getGod(selectedGods.get(0), challenger));
            SetupWorkersTurn setupWorkersTurn = new SetupWorkersTurn(controller, controller.getNextPlayer(currentPlayer), observers);
            clearObserver();
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

    public void handleGodChoice(GodDescription god) {
        if (!isChallengerGodsChosen()) {
            if (selectedGods.contains(god)) {
                throw new IllegalArgumentException();
            }
            selectedGods.add(god);
        } else {
            currentPlayer.setGod(controller.getGameState().getGodsFactory().getGod(god, currentPlayer));
            selectedGods.remove(god);
        }
    }

    private void notifyOptions() {
        Options options;
        if (!challengerGodsChosen) {
            List<GodDescription> gods = new ArrayList<>(Arrays.asList(GodDescription.values()));
            for (GodDescription god : selectedGods) {
                gods.remove(god);
            }
            options = new GodOptions(currentPlayer.getNickname(), gods, Options.MessageType.CHOOSE_GOD);
        } else {
            options = new GodOptions(currentPlayer.getNickname(), selectedGods, Options.MessageType.CHOOSE_GOD);
        }
        notify(options);
    }

    @Override
    public void start() {
        if (isStarted()) throw new RuntimeException();
        started = true;
        notifyOptions();
        logger.debug("First options has been notified");
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
