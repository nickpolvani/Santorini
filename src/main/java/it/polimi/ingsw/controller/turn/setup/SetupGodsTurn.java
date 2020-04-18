package it.polimi.ingsw.controller.turn.setup;

import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodNameAndDescription;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class SetupGodsTurn extends Observable<Options> implements SetupTurn {
    private final Player challenger;
    private final Queue<Operation> turnOperations;
    private final GameController gameController;
    private final List<GodNameAndDescription> selectedGods = new ArrayList<>();
    private Player currentPlayer;
    private Boolean challengerGodChosen = false;


    public SetupGodsTurn(Player challenger, GameController gameController) {
        this.challenger = challenger;
        this.currentPlayer = challenger;
        this.gameController = gameController;
        turnOperations = new LinkedList<>();
        for (int i = 0; i < gameController.getGameState().getPlayers().size(); i++) {
            turnOperations.add(Operation.CHOOSE_GOD);
        }
        notifyOptions();
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
            switchTurn();
        } else {
            notifyOptions();
        }
    }

    @Override
    public void switchTurn() {
        currentPlayer = gameController.getNextPlayer(currentPlayer);
        if (currentPlayer == challenger) {
            if (selectedGods.size() != 1) throw new IllegalStateException();
            challenger.setGod(gameController.getGameState().getGodsFactory().getGod(selectedGods.get(0), challenger));
            gameController.setTurn(new SetupWorkersTurn(gameController, gameController.getNextPlayer(currentPlayer), observers));
            observers.clear();
        } else {
            turnOperations.add(Operation.CHOOSE_GOD);
            notifyOptions();
        }
    }

    public Boolean isChallengerGodChosen() {
        return challengerGodChosen;
    }

    public void handleGodChoice(GodNameAndDescription god) throws AlreadySetException {
        if (!isChallengerGodChosen()) {
            if (selectedGods.contains(god)) {
                throw new AlreadySetException();
            }
            selectedGods.add(god);
        } else {
            currentPlayer.setGod(gameController.getGameState().getGodsFactory().getGod(god, currentPlayer));
            selectedGods.remove(god);

        }
        endCurrentOperation();
    }

    private void notifyOptions() {
        Options options;
        if (!challengerGodChosen) {
            List<GodNameAndDescription> gods = new ArrayList<>(Arrays.asList(GodNameAndDescription.values()));
            for (GodNameAndDescription god : selectedGods) {
                gods.remove(god);
            }
            options = new GodOptions(currentPlayer, gods, "Choose a God");
        } else {
            options = new GodOptions(currentPlayer, selectedGods, "Choose a God");
        }
        notify(options);
    }
}
