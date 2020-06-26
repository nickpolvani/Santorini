package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.PlaceWorkersOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.MessageType;

import java.util.*;

public class SetupWorkersTurn extends Observable<Options> implements SetupTurn {

    private final GameController controller;
    private final Player firstPlayer;
    private final Queue<Operation> turnOperations = new LinkedList<>();
    private Player currentPlayer;
    private boolean started = false;

    public SetupWorkersTurn(GameController controller, Player firstPlayer, List<Observer<Options>> observers) {
        this.controller = controller;
        this.currentPlayer = firstPlayer;
        this.firstPlayer = firstPlayer;
        this.turnOperations.add(Operation.PLACE_WORKERS);
        this.observers.clear();
        addObserverList(observers);
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void switchTurn() {
        currentPlayer = controller.getNextPlayer(currentPlayer);
        if (currentPlayer == firstPlayer) {
            boolean thereIsAthena = controller.getGameState().getPlayers().stream()
                    .anyMatch(player -> player.getGod().getGodDescription().equals(GodDescription.ATHENA));
            Turn nextTurn;
            if (thereIsAthena) {
                nextTurn = new AthenaTurn(controller, currentPlayer, observers);
            } else {
                nextTurn = new BasicTurn(controller, currentPlayer, observers);
            }
            clearObserver();
            controller.setTurn(nextTurn);
            nextTurn.start();
        } else {
            notifyOptions();
        }
    }

    @Override
    public Operation getCurrentOperation() {
        return turnOperations.peek();
    }

    @Override
    public void endCurrentOperation() {
        switchTurn();
    }

    private void notifyOptions() {
        List<Tile.IndexTile> freeIndexTiles = new ArrayList<>();
        Arrays.stream(controller.getGameState().getIslandBoard().clone().getBoard())
                .forEach(colTile -> Arrays.stream(colTile)
                        .filter(tile -> !tile.isOccupied()).forEach(t -> freeIndexTiles.add(t.getIndex())));

        Options playerOptions = new PlaceWorkersOptions(currentPlayer.getNickname(), freeIndexTiles,
                controller.getGameState().getIslandBoard().clone(), getCurrentOperation(),
                MessageType.PLACE_WORKERS, currentPlayer.color);
        notify(playerOptions);
    }

    public void start() {
        if (isStarted()) throw new IllegalStateException();
        started = true;
        notifyOptions();
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
