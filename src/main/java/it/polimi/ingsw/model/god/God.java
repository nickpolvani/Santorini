package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 * TODO Dobbiamo pensare ad un modo per controllare la logica del turno a seconda del God
 */


public abstract class God {

    //TODO bisogna aggiungere ad ogni God le remoteView come observer, quando vengono instanziati.

    private final GodNameAndDescription nameAndDescription;

    protected final GameState gameState;

    protected final Player player;

    protected String confirmMessage;

    protected Worker worker; // worker selected for the current BasicTurn

    /**
     * Default constructor, can be called only by GodsFactory
     */

    protected God(GodNameAndDescription nameAndDescription, Player player, GameState gameState) {
        this.nameAndDescription = nameAndDescription;
        this.gameState = gameState;
        this.player = player;
    }


    public GodNameAndDescription getNameAndDescription() {
        return nameAndDescription;
    }


    public GameState getGameState() {
        return gameState;
    }

    /**
     * Notifies the View of the Options available for current operation in the turn
     */

    public final Options getOptions(Operation currentOperation) throws IllegalStateException {
        switch (currentOperation) {

            case MOVE:
                return createTileOptions(tileToMove(worker.getIndexTile()), "These are the Tiles where you can move");
            case BUILD:
                return createTileOptions(tileToBuild(worker.getIndexTile()), "These are the Tiles where you can build");
            case CHOOSE:
                return createConfirmOptions();
            case SELECT_WORKER:
                Collection<IndexTile> indexTiles = new ArrayList<>();
                Worker[] workers = player.getWorker();
                for (Worker w : workers) {
                    //with this check game does not pass as option a worker who can't move
                    if (tileToMove(w.getIndexTile()).size() > 0) indexTiles.add(w.getIndexTile());
                }
                return createTileOptions(indexTiles, "Choose one of your workers");
            default:
                throw new IllegalStateException("Invalid current operation in Turn of " + player.getNickname());
        }
    }

    /**
     * @param worker selects worker for the current player's turn
     */
    public final void selectWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * @param indexTile is the current position for the worker
     * @return default collection of tiles where the worker can go
     */
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = gameState.getIslandBoard().getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();

        for (IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!(gameState.getIslandBoard().getTile(otherTile).isOccupied()) &&
                    gameState.getIslandBoard().getTile(otherTile).getBuildingLevel() - positionTile.getBuildingLevel() < 2) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }

    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning
     *                  of the turn.
     * @throws IllegalArgumentException
     * @throws AlreadyOccupiedException
     * @throws IllegalStateException    thrown if current operation in turn is not MOVE
     */
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        gameState.getIslandBoard().changePosition(worker, indexTile);

        if (gameState.getIslandBoard().getTile(worker.getIndexTile()).getBuildingLevel() == 3) {
            this.player.setWinner(true);
        }
    }

    /**
     * @param indexTile index of tile where the worker stands
     * @return default collection of tiles where the worker can build
     */
    protected Collection<IndexTile> tileToBuild(IndexTile indexTile) {
        Collection<IndexTile> tileToBuild = new ArrayList<>();
        for (IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!gameState.getIslandBoard().getTile(otherTile).isOccupied()) {
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

        gameState.getIslandBoard().getTile(indexTile).getBuilding().addBlock();
    }

    /**
     * it checks if both workers of the current player cannot move, so the player is declared looser
     *
     * @return
     */
    public boolean cannotMove() {
        Worker[] workers = player.getWorker();
        return tileToMove(workers[0].getIndexTile()).size() == 0 &&
                tileToMove(workers[1].getIndexTile()).size() == 0;
    }

    public boolean cannotBuild() throws NullPointerException {
        if (worker == null) throw new NullPointerException("Worker is not set yet");
        return tileToBuild(worker.getIndexTile()).size() == 0;
    }

    /**
     * Used when the user has to make a choice that changes his turn logic.
     * not all classes need to implement this method if their power does not involve
     * any player's choice
     *
     * @param confirm
     */
    public void applyChoice(boolean confirm) throws RuntimeException {
        throw new RuntimeException("cannot call applyChoice method of abstract class God!!!");
    }

    /**
     * @param tilesToChoose
     * @param message
     * @return
     */
    protected final TileOptions createTileOptions(Collection<IndexTile> tilesToChoose, String message) {
        return new TileOptions(player, tilesToChoose, gameState.getIslandBoard().clone(), message);
    }

    /**
     * @return
     */
    public final ConfirmOptions createConfirmOptions() {
        return new ConfirmOptions(player, this.confirmMessage, gameState.getIslandBoard().clone());
    }

    @Override
    public String toString() {
        return "Your god is " + this.nameAndDescription.getName() + "his power is:\n" + nameAndDescription.getDescriptionOfPower();
    }

    /**
     * @return operations that the player can make during his turn, due to his God power
     */
    public abstract Queue<Operation> getTurnOperations();

}