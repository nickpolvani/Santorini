package it.polimi.ingsw.controller.turn.setup;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.AthenaTurn;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.Turn;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodNameAndDescription;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SetupWorkersTurn extends Observable<Options> implements SetupTurn {

    private Player currentPlayer;
    private final GameController gameController;
    private final Player firstPlayer;
    private final Queue<Operation> turnOperations = new LinkedList<>();

    public SetupWorkersTurn(GameController gameController, Player currentPlayer, List<Observer<Options>> observers) {
        this.gameController = gameController;
        this.currentPlayer = currentPlayer;
        this.firstPlayer = currentPlayer;
        this.turnOperations.add(Operation.PLACE_WORKERS);
        this.observers.clear();
        this.observers.addAll(observers);
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
        if (currentPlayer == firstPlayer) {
            boolean thereIsAthena = false;
            for (Player p : gameController.getGameState().getPlayers()) {
                if (p.getGod().getNameAndDescription() == GodNameAndDescription.ATHENA) {
                    thereIsAthena = true;
                    break;
                }
            }
            Turn nextTurn;
            if (thereIsAthena) {
                nextTurn = new AthenaTurn(gameController, currentPlayer, observers);
            } else {
                nextTurn = new BasicTurn(gameController, currentPlayer, observers);
            }
            gameController.setTurn(nextTurn);
        } else {
            turnOperations.add(Operation.PLACE_WORKERS);
            notifyOptions();
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
        } else {
            notifyOptions();
        }
    }

    //TODO implement method witch at the end of setup checks if AthenaTurn is necessary
    private void notifyOptions() {
        List<Tile.IndexTile> freeIndexTiles = new ArrayList<>();
        for (Tile[] t : gameController.getGameState().getIslandBoard().clone()) {
            for (Tile tile : t) {
                if (!tile.isOccupied()) freeIndexTiles.add(tile.getIndex());
            }
        }
        Options options = new TileOptions(currentPlayer, freeIndexTiles,
                gameController.getGameState().getIslandBoard().clone(), getCurrentOperation(),
                "Choose two tiles where you want to place your workers");
        notify(options);
    }
}
