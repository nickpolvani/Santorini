package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.*;
import java.util.stream.Collectors;

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

        super(GodDescription.DEMETER, player, gameState);
        choiceNotAllowedMessage = "We checked your choice but you cannot build. Don't worry: it's an additional operation, so you won't loose!";
    }

    @Override
    public Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile indexTile) {

        Collection<Tile.IndexTile> tileToBuild = board.indexOfNeighbouringTiles(indexTile);
        if (tileAlreadyBuild != null) {
            return tileToBuild.stream().filter(t -> !(t.equals(tileAlreadyBuild)) && !board.isOccupied(t)).collect(Collectors.toList());
        }
        return tileToBuild.stream().filter(t -> !board.isOccupied(t)).collect(Collectors.toList());
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
                return new LinkedList<>(Collections.singletonList(Operation.MESSAGE_NO_REPLY));
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