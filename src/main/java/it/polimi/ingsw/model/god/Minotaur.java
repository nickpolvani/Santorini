package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 * Your Move: Your Worker may  move into an opponent Worker’s space, if their Worker can be
 * forced one space straight backwards to an unoccupied space at any level.
 */
public class Minotaur extends God {

    /**
     * Default constructor
     */
    protected Minotaur(GameState gameState, Player player) {
        super(GodNameAndDescription.MINOTAUR, player, gameState);

    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        //TODO gestisci i controlli sulle tile dove sono presenti i worker degli opponents
        return null;
    }

    @Override
    public void move(IndexTile indexTile) throws AlreadyOccupiedException {

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }

        gameState.getIslandBoard().changePosition(worker, indexTile);
        //TODO gestisci il caso in cui forzi il worker avversario ad andare indietro.

        handleWinningCondition();
    }

}