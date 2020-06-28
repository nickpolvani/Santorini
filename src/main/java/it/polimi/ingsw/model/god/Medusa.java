package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * If possible, your Workers build in lower neighboring spaces that are
 * "occupied by opponent Workers, removing the opponent Workers from the game.
 */
public class Medusa extends God {

    // Necessary for the method removeWorker()
    private final GameState gameState;


    protected Medusa(GameState gameState, Player player) {
        super(GodDescription.MEDUSA, player, gameState);
        this.gameState = gameState;
    }

    @Override
    public Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile indexTile) {
        int currentLevel = board.getBuildingLevel(indexTile);
        return board.indexOfNeighbouringTiles(indexTile).stream()
                .filter(x -> !board.getDome(x)
                        && (board.getTile(x).getCurrentWorker() == null ||
                        (currentLevel > board.getTile(x).getBuildingLevel() &&
                                board.getCurrentWorker(x).getColor() != currentWorker.getColor())))
                .collect(Collectors.toList());
    }

    @Override
    public void build(Tile.IndexTile indexTile) throws IllegalArgumentException, DomeAlreadyPresentException {
        if (!tileToBuild(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }
        if (board.getTile(indexTile).getCurrentWorker() != null) {
            removeWorker(indexTile);
        }
        board.addBlock(indexTile);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    /**
     * If the player decides to use the Medusa power, the opponent's worker standing on the tile where the
     * players built has to be removed
     */
    private void removeWorker(Tile.IndexTile index) {
        Worker toRemove = board.getCurrentWorker(index);
        Player player = gameState.getPlayers().stream().filter(p -> {
            for (Worker w : p.getWorkers()) {
                if (w.equals(toRemove)) return true;
            }
            return false;
        }).findAny().orElse(null);
        if (player == null) throw new IllegalArgumentException();
        player.getWorkers().remove(toRemove);
        try {
            board.getTile(index).setCurrentWorker(null);
        } catch (AlreadyOccupiedException e) {
            Logger.getLogger("Server").fatal(e.getMessage(), e);
        }
    }
}
