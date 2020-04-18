package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 * Your Build: Your Worker may build one additional time, but not on the same space.
 */
public class Demeter extends God {

    private Tile.IndexTile tileAlreadyBuild;


    /**
     * Default constructor
     */
    protected Demeter(GameState gameState, Player player) {

        super(GodNameAndDescription.DEMETER, player, gameState);
        choiceMessage = "Your worker may build one additional time, but not on the same space. Choose where to build. Mind that if you have no choice, you won't loose";
        choiceNotAllowedMessage = "We checked your choice but you cannot build. Don't worry: it's an additional operation, so you won't loose!";
    }

    @Override
    public Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile indexTile) {

        Collection<Tile.IndexTile> tileToBuild = new ArrayList<>();
        for (Tile.IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!gameState.getIslandBoard().getTile(otherTile).isOccupied()) {
                if (tileAlreadyBuild != null) {
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
        if (tileAlreadyBuild == null) tileAlreadyBuild = indexTile;

    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }


    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            if (tileToBuild(worker.getIndexTile()).size() == 0) {
                return new LinkedList<>(Collections.singletonList(Operation.SEND_MESSAGE));
            } else {
                return new LinkedList<>(Collections.singletonList(Operation.BUILD));
            }
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public void resetGodState() {
        this.confirmed = false;
        tileAlreadyBuild = null;
    }


}