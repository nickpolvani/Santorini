package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Your Turn: If your Worker does not move up, it may build both before and after moving.
 */
public class Prometheus extends God {

    protected Prometheus(GameState gameState, Player player) {

        super(GodDescription.PROMETHEUS, player, gameState);
    }

    /**
     * @param indexTile is the current position for the worker
     * @return the tile where the worker can move, according to the choice of the player
     * about using Prometheus' power
     */
    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        int maxLevelDifference = 2;
        if (confirmed) maxLevelDifference = 1;

        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!(board.isOccupied(otherTile)) &&
                    board.getBuildingLevel(otherTile) - positionTile.getBuildingLevel() < maxLevelDifference) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }


    /**
     * This method is used to check if the player can properly make his choice.
     * If making choice would let the player loose the game, we avoid that situation notifying
     * the player of the impossibility of making the optional build.
     *
     * @return true if the player can perform the optional build without loosing
     */
    private boolean checkValidOptionalBuild() {
        int currentLevel = board.getBuildingLevel(this.currentWorker.getIndexTile());
        // tiles on the same or lower level
        List<IndexTile> validTileToMove = tileToMove(this.currentWorker.getIndexTile()).stream()
                .filter(t -> board.getBuildingLevel(t) - currentLevel <= 0).collect(Collectors.toList());

        long numberValidTilesToBuild = tileToBuild(this.currentWorker.getIndexTile()).size();
        // player can only build in the unique tile where he would be allowed to move
        // with this check we avoid the player defeat.
        // We know that tileToBuild includes tileToMove, so if both have size = 1, they contain the same element.
        if (validTileToMove.size() == 1 && numberValidTilesToBuild == 1 &&
                board.getBuildingLevel(validTileToMove.get(0)) - currentLevel == 0) {
            return false;
        }
        // the worker is surrounded by tiles that are at least one level higher,
        // performing the optional build would result in the player defeat
        else return validTileToMove.size() != 0;
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        Operation[] operationsArray;
        if (confirmed) {
            operationsArray = new Operation[]{Operation.BUILD, Operation.MOVE, Operation.BUILD};
        } else {
            operationsArray = new Operation[]{Operation.MOVE, Operation.BUILD};
        }

        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public boolean isChooseAvailable() {
        return checkValidOptionalBuild();
    }
}