package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta.
 * Your Build: Your Worker may build one additional block (not dome) on top of your first block.
 */
public class Hephaestus extends God {

    private Tile.IndexTile tileForAdditionalBlock;
    private Queue<Operation> remainingOperations;

    /**
     * Default constructor
     */
    protected Hephaestus(GameState gameState, Player player) {
        super(GodDescription.HEPHAESTUS, player, gameState);
        choiceNotAllowedMessage = "You cannot build a dome in additional build operation.\nYou will not loose the game.";
    }

    @Override
    public void build(Tile.IndexTile indexTile) throws DomeAlreadyPresentException {
        super.build(indexTile);
        tileForAdditionalBlock = indexTile;
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    /**
     * @return empty Operations'List both if player confirms to use Hephaestus' power, and he can perform his choice,
     * and if he rejects his chance;
     * Operation.MESSAGE_NO_REPLY if player confirms to use Hephaestus' power but he can't perform his choice.
     */
    @Override
    public Queue<Operation> getRemainingOperations() {
        return remainingOperations;
    }

    @Override
    public void applyChoice(boolean confirm) throws DomeAlreadyPresentException {
        confirmed = confirm;
        remainingOperations = new LinkedList<>();
        if (confirmed) {
            // the player can build the additional block
            if (board.getBuildingLevel(tileForAdditionalBlock) < 3) {
                board.addBlock(tileForAdditionalBlock);

            } else { // the player cannot build the additional block, so he has to be notified
                remainingOperations = new LinkedList<>(Collections.singletonList(Operation.MESSAGE_NO_REPLY));
            }
        }

    }

    @Override
    public void resetGodState() {
        confirmed = false;
        tileForAdditionalBlock = null;
    }
}