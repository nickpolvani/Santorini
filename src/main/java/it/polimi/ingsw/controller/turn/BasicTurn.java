package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.*;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.Ares;
import it.polimi.ingsw.model.god.Charon;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.Poseidon;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.MessageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;


/**
 * Type of Turn to be used after setup is finished
 */

public class BasicTurn extends Observable<Options> implements Turn {
    /**
     *
     */
    protected GameController gameController;
    /**
     *
     */
    protected Player currentPlayer;
    /**
     *
     */
    protected Queue<Operation> turnOperations;

    private boolean started;

    /**
     * Default constructor that set only which game the turn belongs to.
     */
    public BasicTurn(GameController gameController, Player firstPlayer, List<Observer<Options>> observerList) {
        this.currentPlayer = firstPlayer;
        this.currentPlayer.getGod().resetGodState();
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
        this.gameController = gameController;
        addObserverList(observerList);
    }

    /**
     * Initializer of a new turn, personalized for the current player's god
     */
    public void switchTurn() {
        currentPlayer = gameController.getNextPlayer(currentPlayer);
        currentPlayer.getGod().resetGodState();

        if (currentPlayer.getGod().cannotMove()) {
            gameController.hasLost(currentPlayer);
        } else {
            this.turnOperations = currentPlayer.getGod().getTurnOperations();
            notify(getOptions()); //this notify is used to notify the new current player about his first move
        }
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Operation getCurrentOperation() {
        return turnOperations.peek();
    }

    /**
     * First: we check if the operation just done is a ChooseOperation: in this way we modify the remaining TurnOperations
     * according to the player's choice;
     * Then handleRemainingOperation is called.
     */
    public void endCurrentOperation() {

        if (getCurrentPlayer().isWinner()) {
            gameController.hasWon(currentPlayer);
        }
        if (getCurrentOperation().equals(Operation.CHOOSE)) {
            this.turnOperations = getCurrentPlayer().getGod().getRemainingOperations();
        } else {
            turnOperations.poll();
        }

        handleRemainingOperations();
    }

    /**
     * If player has done all his turnOperations, we switch turn;
     * If the player has to build but he cannot perform this operation, he looses and he receives
     * the notification from controller.
     * Otherwise we notify the operation the player has to do.
     */
    protected void handleRemainingOperations() {

        if (turnOperations.isEmpty()) {
            switchTurn();
        } else if (getCurrentOperation() == Operation.BUILD && currentPlayer.getGod().cannotBuild()) {
            gameController.hasLost(currentPlayer);
            switchTurn(); //this because after removing the looser whe have to notify the next player to play
        } else {
            if (getCurrentOperation() == Operation.CHOOSE && !currentPlayer.getGod().isChooseAvailable()) {
                notify(new MessageOption(currentPlayer.getNickname(), MessageType.GODS_POWER_NOT_AVAILABLE));
                endCurrentOperation();
            } else {
                notify(getOptions());
            }
        }
    }

    /**
     * @return the options the current player has, according to his god's power
     * @throws IllegalStateException We put this method in turn and not in gods because we have to handle also athenaTurn. In fact AthenaTurn will override
     *                               this method checking Athena's Power
     */
    public Options getOptions() {
        Operation currentOperation = getCurrentOperation();
        God currentGod = currentPlayer.getGod();
        IslandBoard boardClone = gameController.getGameState().getIslandBoard().clone();
        switch (currentOperation) {
            case MOVE:
                return new TileOptions(currentPlayer.getNickname(), currentGod.tileToMove(currentGod.getCurrentWorker().getIndexTile()),
                        boardClone, currentOperation, MessageType.MOVE);
            case BUILD:
                return new TileOptions(currentPlayer.getNickname(), currentGod.tileToBuild(currentGod.getCurrentWorker().getIndexTile()),
                        boardClone, currentOperation, MessageType.BUILD);
            case CHOOSE:
                return new ChooseOptions(currentPlayer.getNickname(), currentGod.getGodDescription().getDescriptionOfPower() +
                        "\nDo you want to use your god's power? (Yes/No)", boardClone);
            case SELECT_WORKER:
                Collection<Tile.IndexTile> indexTiles = new ArrayList<>();
                for (Worker w : currentPlayer.getWorkers()) {
                    //with this check game does not pass as option a worker who can't move
                    if (!currentGod.tileToMove(w.getIndexTile()).isEmpty()) indexTiles.add(w.getIndexTile());
                }
                return new TileOptions(currentPlayer.getNickname(), indexTiles, boardClone, currentOperation, MessageType.SELECT_WORKER);
            case SELECT_OPPONENTS_WORKER:
                if (!(currentGod instanceof Charon)) throw new IllegalArgumentException();
                return new TileOptions(currentPlayer.getNickname(), ((Charon) currentGod).opponentsWorkerTile(), boardClone,
                        currentOperation, MessageType.SELECT_OPPONENT_WORKER);
            case POSEIDON_BUILD:
                if (!(currentGod instanceof Poseidon)) throw new IllegalArgumentException();
                return new PoseidonTileOptions(currentPlayer.getNickname(), ((Poseidon) currentGod).unmovedWorkerTileToBuild(), boardClone,
                        currentOperation, MessageType.POSEIDON_BUILD);
            case REMOVE_BLOCK:
                if (!(currentGod instanceof Ares)) throw new IllegalArgumentException();
                return new TileOptions(currentPlayer.getNickname(), ((Ares) currentGod).tileToRemoveBlock(), boardClone,
                        currentOperation, MessageType.REMOVE_BLOCK);
            default:
                throw new IllegalStateException("Invalid current operation in Turn of " + currentPlayer.getNickname());
        }
    }


    public void start() {
        if (isStarted()) throw new IllegalStateException();
        started = true;
        notify(getOptions());
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}

