package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Artemis extends God {

    private IndexTile tileFrom;


    /**
     * Default constructor
     */
    public Artemis(GameState gameState, Player player) {
        super(GodNameAndDescription.ARTEMIS, player, gameState);
    }


    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = gameState.getIslandBoard().getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        for (IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!(gameState.getIslandBoard().getTile(otherTile).isOccupied()) &&
                    gameState.getIslandBoard().getTile(otherTile).getBuildingLevel() - positionTile.getBuildingLevel() < 2) {
                if (tileToMove != null) {
                    if (!otherTile.equals(tileFrom)) {
                        tileToMove.add(otherTile);
                    }
                } else {
                    tileToMove.add(otherTile);
                }
            }
        }
        return tileToMove;
    }

    @Override
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {
        super.move(indexTile);
        if (tileFrom == null) {
            tileFrom = indexTile;
        } else {
            tileFrom = null;
        }
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public void applyChoice(boolean confirm) throws RuntimeException {
        if (!confirm) {
            tileFrom = null;
            //TODO gestire come sistemare l'evoluzione del turno nel caso in cui l'utente non confermi la scelta
        }

    }
}