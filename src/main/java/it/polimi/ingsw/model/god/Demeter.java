package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Demeter extends God {

    private Tile.IndexTile tileAlreadyBuild;

    /**
     * Default constructor
     */
    protected Demeter(GameState gameState, Player player) {
        super(GodNameAndDescription.DEMETER, player, gameState);
    }

    @Override
    protected Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile indexTile) {

        Collection<Tile.IndexTile> tileToBuild = new ArrayList<>();
        for (Tile.IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!gameState.getIslandBoard().getTile(otherTile).isOccupied()) {
                if (tileToBuild != null) {
                    if (!otherTile.equals(tileAlreadyBuild)) {
                        tileToBuild.add(otherTile);
                    }
                } else {
                    tileToBuild.add(otherTile);
                }
            }
        }
        return tileToBuild;

    }

    @Override
    public void build(Tile.IndexTile indexTile) throws DomeAlreadyPresentException {
        super.build(indexTile);
        if (tileAlreadyBuild == null) {
            tileAlreadyBuild = indexTile;
        } else {
            tileAlreadyBuild = indexTile;
        }

    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public void applyChoice(boolean confirm) throws RuntimeException {
        if (!confirm) {
            tileAlreadyBuild = null;
            //TODO gestire come cambiare il turno in caso di rifiuto
        }
    }
}