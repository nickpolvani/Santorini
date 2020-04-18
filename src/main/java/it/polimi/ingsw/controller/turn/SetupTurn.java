package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodNameAndDescription;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class SetupTurn extends Observable<Options> implements Turn {

    private Player challenger;
    private Player currentPlayer;

    private Boolean godChosen = false;

    private Queue<Operation> turnOperations;
    private GameController gameController;
    private List<GodNameAndDescription> selectedGods = new ArrayList<>();

    private Boolean challengerGodChosen = false;

    public SetupTurn(Player challenger, GameController gameController) {
        this.challenger = challenger;
        this.currentPlayer = challenger;
        this.turnOperations = new LinkedList<>();
        for (int i = 0; i < gameController.getGameState().getPlayers().size(); i++) {
            turnOperations.add(Operation.CHOOSE_GOD);
        }
        notifyOptions();
    }

    //TODO implement setup
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void switchTurn() {
        currentPlayer = gameController.getNextPlayer(currentPlayer);
        challengerGodChosen = true;
        if (godChosen) {
            turnOperations.add(Operation.PLACE_WORKERS);
        } else {
            turnOperations.add(Operation.CHOOSE_GOD);
        }
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
        }
        notifyOptions();
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
    //TODO implement method witch at the end of setup checks if AthenaTurn is necessary

    public void notifyOptions() {
        Options options;
        if (!godChosen) {
            if (!challengerGodChosen) {
                List<GodNameAndDescription> gods = new ArrayList<GodNameAndDescription>(Arrays.asList(GodNameAndDescription.values()));
                for (GodNameAndDescription god : selectedGods) {
                    gods.remove(god);
                }
                options = new GodOptions(currentPlayer, gods, "Choose a God");
            } else {
                options = new GodOptions(currentPlayer, selectedGods, "Choose a God");
            }
        } else {
            List<Tile.IndexTile> freeIndexTiles = new ArrayList<>();
            for (Tile[] t : gameController.getGameState().getIslandBoard().clone()) {
                for (Tile tile : t) {
                    if (!tile.isOccupied()) freeIndexTiles.add(tile.getIndex());
                }
            }
            options = new TileOptions(currentPlayer, freeIndexTiles, gameController.getGameState().getIslandBoard().clone(),
                    getCurrentOperation(), "Choose two tiles where you want to place your workers");
        }
        notify(options);
    }
}
