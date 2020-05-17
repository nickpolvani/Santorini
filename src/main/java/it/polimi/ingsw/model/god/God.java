package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;


public abstract class God {

    private final GodDescription godDescription;

    protected final IslandBoard board;

    protected final Player player;

    protected Worker worker; // worker selected for the current turn

    protected boolean confirmed;

    protected String choiceNotAllowedMessage;


    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected God(GodDescription godDescription, Player player, GameState gameState) {
        this.godDescription = godDescription;
        this.board = gameState.getIslandBoard();
        this.player = player;
        this.confirmed = false;
    }


    public GodDescription getGodDescription() {
        return godDescription;
    }


    /**
     * @param workerPosition selects worker for the current player's turn
     */
    public final void selectWorker(Tile.IndexTile workerPosition) {
        this.worker = board.getCurrentWorker(workerPosition);
        if (worker == null || !Arrays.asList(player.getWorkers()).contains(worker)) {
            throw new IllegalArgumentException();
        }
    }

    public final void selectWorker(Worker worker) {
        if (worker == null || !Arrays.asList(player.getWorkers()).contains(worker)) {
            throw new IllegalArgumentException();
        }
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    /**
     * @param indexTile is the current position for the worker
     * @return default collection of tiles where the worker can go
     */
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();

        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!(board.getTile(otherTile).isOccupied()) &&
                    board.getBuildingLevel(otherTile) - positionTile.getBuildingLevel() < 2) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }

    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning
     *                  of the turn.
     * @throws IllegalArgumentException if tile chosen is not in tileToMove List
     * @throws AlreadyOccupiedException if the tile chosen has a worker yet
     * @throws IllegalStateException    Thrown if current operation in turn is not MOVE
     */
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        board.changePosition(worker, indexTile);

        handleWinningCondition();
    }

    protected void handleWinningCondition() {
        if (board.getBuildingLevel(worker.getIndexTile()) == 3) {
            this.player.setWinner(true);
        }
    }

    /**
     * @param indexTile index of tile where the worker stands
     * @return default collection of tiles where the worker can build
     * @see Tile
     */
    public Collection<IndexTile> tileToBuild(IndexTile indexTile) {
        Collection<IndexTile> tileToBuild = new ArrayList<>();
        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!board.isOccupied(otherTile)) {
                tileToBuild.add(otherTile);
            }
        }
        return tileToBuild;
    }

    /**
     * @param indexTile is the tile chosen by the player to let his worker build
     * @throws IllegalArgumentException
     * @throws DomeAlreadyPresentException
     * @throws IllegalStateException       thrown if current operation in turn is not BUILD
     */
    public void build(IndexTile indexTile) throws IllegalArgumentException, DomeAlreadyPresentException {

        if (!tileToBuild(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }

        board.getTile(indexTile).getBuilding().addBlock();
    }

    /**
     * it checks if both workers of the current player cannot move, so the player is declared looser
     *
     * @return
     */
    public boolean cannotMove() {
        Worker[] workers = player.getWorkers();
        return tileToMove(workers[0].getIndexTile()).size() == 0 &&
                tileToMove(workers[1].getIndexTile()).size() == 0;
    }

    public boolean cannotBuild() throws NullPointerException {
        if (worker == null) throw new NullPointerException("Worker is not set yet");
        return tileToBuild(worker.getIndexTile()).size() == 0;
    }

    public String getChoiceNotAllowedMessage() {
        return choiceNotAllowedMessage;
    }

    @Override
    public String toString() {
        return "Your god is " + this.godDescription.getName() + ". His power is: " + godDescription.getDescriptionOfPower();
    }

    /**
     * @return operations that the player can make during his turn, due to his God power
     */
    public abstract Queue<Operation> getTurnOperations();

    public Queue<Operation> getRemainingOperations() {
        return null;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Every time a player starts his turn, his god is reset to his initial state
     */
    public void resetGodState() {
        confirmed = false;
    }

    /**
     * Used when the user has to make a choice that changes his turn logic.
     * not all classes need to implement this method if their power does not involve
     * any player's choice     *
     *
     * @param confirm: the choice of the player
     */
    public void applyChoice(boolean confirm) throws DomeAlreadyPresentException {
        this.confirmed = confirm;
    }

}