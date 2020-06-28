package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your Build: Your Worker may build one additional block (not dome) on top of your first block.
 */
public class Hephaestus extends God {

    private Tile.IndexTile tileForAdditionalBlock;

    protected Hephaestus(GameState gameState, Player player) {
        super(GodDescription.HEPHAESTUS, player, gameState);
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


    @Override
    public Queue<Operation> getRemainingOperations() {
        /*
        empty Operations'List both if player confirms to use Hephaestus' power, and he can perform his choice,
        and if he rejects his chance; Operation.MESSAGE_NO_REPLY if player confirms to use Hephaestus' power
        but he can't perform his choice.
         */
        return new LinkedList<>();
    }

    @Override
    public void applyChoice(boolean confirm) {
        confirmed = confirm;
        if (confirmed) {
            try {
                board.addBlock(tileForAdditionalBlock);
            } catch (DomeAlreadyPresentException e) {
                Logger.getLogger("Server").fatal(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean isChooseAvailable() {
        return (board.getBuildingLevel(tileForAdditionalBlock) < 3);
    }

    @Override
    public void resetGodState() {
        confirmed = false;
        tileForAdditionalBlock = null;
    }
}