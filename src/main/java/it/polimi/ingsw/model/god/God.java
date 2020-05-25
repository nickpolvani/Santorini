package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;


public abstract class God {

    private final GodDescription godDescription;

    protected final IslandBoard board;

    protected final Player player;

    protected Worker currentWorker; // worker selected for the current turn

    protected boolean confirmed;

    /**
     * Default constructor, can be called only by GodsFactory
     *
     * @param godDescription The godDescription that corresponding
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
        this.currentWorker = board.getCurrentWorker(workerPosition);
        if (currentWorker == null || !player.getWorkers().contains(currentWorker)) {
            throw new IllegalArgumentException();
        }
    }

    public final void selectWorker(Worker worker) {
        if (worker == null || !player.getWorkers().contains(worker)) {
            throw new IllegalArgumentException();
        }
        this.currentWorker = worker;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
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
    public void move(IndexTile indexTile) throws AlreadyOccupiedException {

        if (!tileToMove(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        board.changePosition(currentWorker, indexTile);

        handleWinningCondition();
    }

    protected void handleWinningCondition() {
        if (board.getBuildingLevel(currentWorker.getIndexTile()) == 3) {
            this.player.setWinner(true);
        }
    }

    /**
     * @param indexTile indexTile: index of the tile where the worker stands
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
     * @throws DomeAlreadyPresentException
     */
    public void build(IndexTile indexTile) throws DomeAlreadyPresentException {

        if (!tileToBuild(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }

        board.getTile(indexTile).getBuilding().addBlock();
    }

    /**
     * it checks if both the workers of the current player cannot move, so the player is declared looser
     */
    public boolean cannotMove() {
        List<Worker> workers = player.getWorkers();
        return workers.stream().allMatch(w -> tileToMove(w.getIndexTile()).isEmpty());
    }

    public boolean cannotBuild() {
        //TODO il lancio di questa eccezione non serve sarebbe comunque lanciata dalla riga sottostante!!
        if (currentWorker == null) throw new NullPointerException("Worker is not set yet");
        return tileToBuild(currentWorker.getIndexTile()).isEmpty();
    }

    @Override
    public String toString() {
        return this.godDescription.getName() + ". His power is: " + godDescription.getDescriptionOfPower();
    }

    /**
     * @return operations that the player can make during his turn, due to his God power
     */
    public abstract Queue<Operation> getTurnOperations();

    //TODO serve questo metodo??
    public Queue<Operation> getRemainingOperations() {
        return null;
    }


    /**
     * Every time a player starts his turn, his god is reset to his initial state
     */
    //TODO ma se lo fa questo perché è presente anche nelle sottoclassi
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
    public void applyChoice(boolean confirm) {
        this.confirmed = confirm;
    }

    public boolean isChooseAvailable() {
        return true;
    }

    protected Worker findNotCurrentWorker() {
        //we have to do this check because there is Medusa, thus the otherWorker may have been deleted from the game
        for (Worker w : player.getWorkers()) {
            if (!w.equals(currentWorker)) {
                return w;
            }
        }
        return null;
    }

}