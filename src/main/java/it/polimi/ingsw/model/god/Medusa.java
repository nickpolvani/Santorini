package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

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

    //TODO inserito per il metodo removeWorker
    private final GameState gameState;

    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Medusa(GameState gameState, Player player) {
        super(GodDescription.MEDUSA, player, gameState);
        this.gameState = gameState;
    }

    @Override
    public Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile indexTile) {
        int currentLevel = board.getBuildingLevel(worker.getIndexTile());
        return board.indexOfNeighbouringTiles(indexTile).stream()
                .filter(x -> !board.getDome(x) && !(board.getTile(x).getCurrentWorker() != null && board.getTile(x).getBuildingLevel() >= currentLevel))
                .collect(Collectors.toList());
    }

    @Override
    public void build(Tile.IndexTile indexTile) throws IllegalArgumentException, DomeAlreadyPresentException {
        if (!tileToBuild(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }
        if (board.getTile(indexTile).getCurrentWorker() != null) {
            board.getTile(indexTile).getCurrentWorker().setIndexTile(null);
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
     *
     */
    private void removeWorker(Tile.IndexTile index) {
        Player player = gameState.getPlayers().stream().filter(p -> {
            for (Worker w : p.getWorkers()) {
                if (w.equals(board.getCurrentWorker(index))) return true;
            }
            return false;
        }).findAny().orElse(null);
        if (player == null) throw new IllegalArgumentException();
        player.getWorkers().remove(board.getCurrentWorker(index));
        try {
            board.getTile(index).setCurrentWorker(null);
        } catch (AlreadyOccupiedException e) {
            e.printStackTrace();
        }
    }
}
