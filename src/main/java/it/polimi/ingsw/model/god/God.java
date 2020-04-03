package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 * TODO Dobbiamo pensare ad un modo per controllare la logica del turno a seconda del God
 */


public abstract class God extends Observable<Options> {

    //TODO bisogna aggiungere ad ogni God le remoteView come observer, quando vengono instanziati.

    private final GodNameAndDescription nameAndDescription;
    private GameState gameState;


    protected String confirmMessage;
    private Worker worker; // worker selected for the current Turn

    /**
     * Default constructor, can be called only by GodsFactory
     */

    protected God(GodNameAndDescription nameAndDescription) {
        this.nameAndDescription = nameAndDescription;
    }


    public GodNameAndDescription getNameAndDescription() {
        return nameAndDescription;
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    /**
     * @param indexTile is the current position for the worker
     * @return default collection of tiles where the worker can go
     */

    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = gameState.getIslandBoard().getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        for (IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!(gameState.getIslandBoard().getTile(otherTile).isOccupied()) && gameState.getIslandBoard().getTile(otherTile).getBuilding().getLevel().getLevelInt() - positionTile.getBuilding().getLevel().getLevelInt() < 2) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
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
     * Notifies the View of the Options available for current operation in the turn
     */

    protected final void notifyOptions() {
        switch (gameState.getTurn().getCurrentOperation()) {

            case MOVE:
                notify(createTileOptions(tileToMove(worker.getTile().getIndex()), "These are the Tiles where you can move"));
                break;
            case BUILD:
                notify(createTileOptions(tileToBuild(worker.getTile().getIndex()), "These are the Tiles where you can build"));
                break;
            case CHOOSE:
                notify(createConfirmOptions());
                break;
            case SELECT_WORKER:
                Collection<IndexTile> indexTiles = new ArrayList<>();
                Worker[] workers = gameState.getTurn().getCurrentPlayer().getWorker();
                for (int i = 0; i < workers.length; ++i) {
                    indexTiles.add(workers[i].getTile().getIndex());
                }
                notify(createTileOptions(indexTiles, "choose one of your workers"));
                break;
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
        notifyOptions();
    }

    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning
     *                  of the turn.
     * @throws IllegalArgumentException
     */
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {
        if (!tileToMove(worker.getTile().getIndex()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        gameState.getIslandBoard().changePosition(worker, indexTile);
        gameState.getTurn().endCurrentOperation();
        notifyOptions();
    }

    /**
     * @param indexTile is the tile chosen by the player to let his worker build
     * @throws IllegalArgumentException
     */
    public void build(IndexTile indexTile) {
        if (!tileToBuild(worker.getTile().getIndex()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }
        gameState.getIslandBoard().getTile(indexTile).getBuilding().addBlock();
        gameState.getTurn().endCurrentOperation();
        notifyOptions();
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
        Tile[][] boardClone = gameState.getIslandBoard().clone();
        return new TileOptions(player, tilesToChoose, boardClone, message);
    }

    /**
     * @return
     */
    public final ConfirmOptions createConfirmOptions() {
        Player player = gameState.getTurn().getCurrentPlayer();
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