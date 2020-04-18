package it.polimi.ingsw.model.god;

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
 */

public abstract class God {

    private final GodNameAndDescription nameAndDescription;

    protected final GameState gameState;

    protected final Player player;

    protected Worker worker; // worker selected for the current BasicTurn

    protected String choiceMessage;

    protected boolean confirmed;

    protected String choiceNotAllowedMessage;


    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected God(GodNameAndDescription nameAndDescription, Player player, GameState gameState) {
        this.nameAndDescription = nameAndDescription;
        this.gameState = gameState;
        this.player = player;
        this.confirmed = false;
    }


    public GodNameAndDescription getNameAndDescription() {
        return nameAndDescription;
    }


    public GameState getGameState() {
        return gameState;
    }


    /**
     * @param worker selects worker for the current player's turn
     */
    public final void selectWorker(Worker worker) {
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
     * @throws IllegalArgumentException if tile chosen is not in tileToMove List
     * @throws AlreadyOccupiedException if the tile chosen has a worker yet
     * @throws IllegalStateException    Thrown if current operation in turn is not MOVE
     */
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        gameState.getIslandBoard().changePosition(worker, indexTile);

        handleWinningCondition();
    }

    protected void handleWinningCondition() {
        if (gameState.getIslandBoard().getTile(worker.getIndexTile()).getBuildingLevel() == 3) {
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

    public String getChoiceMessage() {
        return choiceMessage;
    }

    public String getChoiceNotAllowedMessage() {
        return choiceNotAllowedMessage;
    }

    @Override
    public String toString() {
        return "Your god is " + this.nameAndDescription.getName() + "his power is:\n" + nameAndDescription.getDescriptionOfPower();
    }

    /**
     * @return operations that the player can make during his turn, due to his God power
     */
    public abstract Queue<Operation> getTurnOperations();

    public Queue<Operation> getRemainingOperations() {
        return null;
    }

    ;

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