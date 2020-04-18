package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta.
 * Your Build: Your Worker may build one additional block (not dome) on top of your first block.
 */
public class Hephaestus extends God {

    private Tile.IndexTile tileForAdditionalBlock;

    /**
     * Default constructor
     */
    protected Hephaestus(GameState gameState, Player player) {
        super(GodNameAndDescription.HEPHAESTUS, player, gameState);
        choiceNotAllowedMessage = "You cannot build a dome in additional build operation.\nYou will not loose the game.";
    }

    @Override
    public Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile tile) {
        return super.tileToBuild(tile);
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
     * Operation.SEND_MESSAGE if player confirms to use Hephaestus' power but he can't perform his choice.
     */
    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            if (gameState.getIslandBoard().getTile(tileForAdditionalBlock).getBuildingLevel() < 3) {
                return new LinkedList<>();
            } else {
                return new LinkedList<>(Collections.singletonList(Operation.SEND_MESSAGE));
            }
        } else {
            return new LinkedList<>();
        }

    }

    @Override
    public void applyChoice(boolean confirm) throws RuntimeException, DomeAlreadyPresentException {
        confirmed = confirm;
        if (confirm && gameState.getIslandBoard().getTile(tileForAdditionalBlock).getBuildingLevel() < 3) {
            gameState.getIslandBoard().getTile(tileForAdditionalBlock).getBuilding().addBlock();
        }
    }

    @Override
    public void resetGodState() {
        confirmed = false;
        tileForAdditionalBlock = null;
    }
}