package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Tile.IndexTile;

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

    protected GameState gameState;

    protected String confirmMessage;

    protected Worker worker; // worker selected for the current GameTurn

    /**
     * Default constructor, can be called only by GodsFactory
     */

    protected God(GodNameAndDescription nameAndDescription, GameState gameState) {
        this.nameAndDescription = nameAndDescription;
        this.gameState = gameState;
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

    public final Options getOptions() throws IllegalStateException {
        switch (gameState.getTurn().getCurrentOperation()) {

            case MOVE:
                return createTileOptions(tileToMove(worker.getIndexTile()), "These are the Tiles where you can move");
            case BUILD:
                return createTileOptions(tileToBuild(worker.getIndexTile()), "These are the Tiles where you can build");
            case CHOOSE:
                return createConfirmOptions();
            case SELECT_WORKER:
                Collection<IndexTile> indexTiles = new ArrayList<>();
                Worker[] workers = gameState.getTurn().getCurrentPlayer().getWorker();
                for (int i = 0; i < workers.length; ++i) {
                    if (tileToMove(workers[i].getIndexTile()).size() > 0) indexTiles.add(workers[i].getIndexTile());
                }
                return createTileOptions(indexTiles, "choose one of your workers");
            default:
                throw new IllegalStateException("Invalid current operation in GameTurn" + gameState.getTurn().getCurrentPlayer());
        }
    }

    /**
     * @param worker selects worker for the turn, ends the operation and notifies
     *               View of the next Options that the client has
     *               IMPORTANT: notifyOptions() has to be AFTER turn.endCurrentOperation()
     *               otherwise you will notify twice the same options as before
     */
    public final void selectWorker(Worker worker) {
        this.worker = worker;
        gameState.getTurn().endCurrentOperation();
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
                    gameState.getIslandBoard().getTile(otherTile).getBuilding().getLevel().getLevelInt() - positionTile.getBuilding().getLevel().getLevelInt() < 2) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }


    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning
     *                  of the turn.
     * @throws IllegalArgumentException
     */
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {
        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        gameState.getIslandBoard().changePosition(worker, indexTile);

        if (gameState.getIslandBoard().getTile(worker.getIndexTile()).getBuildingLevel() == 3) {

            this.gameState.setWinner(this.gameState.getTurn().getCurrentPlayer());
        } else gameState.getTurn().endCurrentOperation();
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
        if (tileToBuild.size() == 0) this.gameState.setLooser(this.gameState.getTurn().getCurrentPlayer());
        return tileToBuild;
    }

    /**
     * @param indexTile is the tile chosen by the player to let his worker build
     * @throws IllegalArgumentException
     */
    public void build(IndexTile indexTile) throws DomeAlreadyPresentException {
        if (!tileToBuild(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }
        gameState.getIslandBoard().getTile(indexTile).getBuilding().addBlock();
        gameState.getTurn().endCurrentOperation();
    }

    /**
     * it checks if both workers of the current player cannot move, so the player is declared looser
     *
     * @return
     */
    public boolean checkHasLost() {
        Worker[] workers = this.gameState.getTurn().getCurrentPlayer().getWorker();
        if (tileToMove(workers[0].getIndexTile()).size() == 0 &&
                tileToMove(workers[1].getIndexTile()).size() == 0) {
            return true;
        }
        return false;
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
        Player player = gameState.getTurn().getCurrentPlayer();
        if (player.getGod() != this) {
            System.err.println("CRITICAL WARNING:  calling method of wrong God, current player is " + player + "God is: " + this.getNameAndDescription().getName());
            throw new RuntimeException();
        }
        Tile[][] boardClone = gameState.getIslandBoard().clone();
        return new TileOptions(player, tilesToChoose, boardClone, message);
    }

    /**
     * @return
     */
    public final ConfirmOptions createConfirmOptions() {
        Player player = gameState.getTurn().getCurrentPlayer();
        if (player.getGod() != this) {
            System.err.println("CRITICAL WARNING:  calling method of wrong God, current player is " + player + "God is: " + this.getNameAndDescription().getName());
            throw new RuntimeException();
        }
        Tile[][] boardClone = gameState.getIslandBoard().clone();
        return new ConfirmOptions(player, this.confirmMessage, boardClone);

    }

    @Override
    public String toString() {
        return "Your god is " + this.nameAndDescription.getName() + "his power is:\n" + nameAndDescription.getDescriptionOfPower();
    }

    protected Worker getWorker() {
        return worker;
    }

    /**
     * @return operations that the player can make during his turn, due to his God power
     */
    public abstract Queue<Operation> getTurnOperations();


}