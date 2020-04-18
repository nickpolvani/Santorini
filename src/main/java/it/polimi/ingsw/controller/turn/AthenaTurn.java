package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.MessageOption;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.Athena;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.GodNameAndDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaTurn extends BasicTurn {
    private Athena athena;


    public AthenaTurn(GameController gameController, Player currentPlayer) {
        super(currentPlayer, gameController);

        for (Player p : gameController.getGameState().getPlayers()) {
            if (p.getGod().getNameAndDescription() == GodNameAndDescription.ATHENA) {
                athena = (Athena) p.getGod();
            }
        }

        if (athena == null) throw new RuntimeException("Athena is not set despite we are in AthenaTurn");
    }


    @Override
    public void switchTurn() {
        currentPlayer = gameController.getNextPlayer(currentPlayer);
        currentPlayer.getGod().resetGodState();
        this.turnOperations = currentPlayer.getGod().getTurnOperations();

        Options currentOption = getOptions();
        if (currentOption != null) {
            notify(currentOption);
        } else {
            gameController.hasLost(currentPlayer);
        }

    }

    /**
     * @param w: worker selected by the player at the beginning of a turn
     * @return collection of tiles where workers can move according to Athena's Power
     */
    private Collection<Tile.IndexTile> athenaTileToMove(Worker w) {
        Collection<Tile.IndexTile> res = currentPlayer.getGod().tileToMove(w.getIndexTile());
        if (!athena.getCanMoveUp()) {
            int currentWorkerLevel = gameController.getGameState().getIslandBoard().getTile(w.getIndexTile()).getBuildingLevel();
            int otherLevel;
            for (Tile.IndexTile t : res) {
                otherLevel = gameController.getGameState().getIslandBoard().getTile(t).getBuildingLevel();
                if (otherLevel - currentWorkerLevel > 0) {
                    res.remove(t);
                }
            }
        }
        return res;
    }


    /**
     * Since Athena could limit Artemis'power, we decide to let AthenaTurn handle
     * this situation. So we decide override endCurrentOperation to handle ChooseOperation.
     */
    @Override
    public void endCurrentOperation() {

        if (getCurrentOperation().equals(Operation.CHOOSE)) {
            this.turnOperations = getCurrentPlayer().getGod().getRemainingOperations();

            if (currentPlayer.getGod().getNameAndDescription() == GodNameAndDescription.ARTEMIS
                    && currentPlayer.getGod().isConfirmed()
                    && athenaTileToMove(currentPlayer.getGod().getWorker()).size() == 0) {
                turnOperations = new LinkedList<>(Arrays.asList(Operation.SEND_MESSAGE, Operation.BUILD));
            }

        } else {
            turnOperations.poll();
        }

        handleRemainingOperations();
    }


    /**
     * @return the options the current player has, according to his god's power and Athena's power
     * @throws IllegalStateException We put this method in turn and not in gods because we have to handle also athenaTurn. In fact AthenaTurn will override
     *                               this method checking Athena's Power
     */
    @Override
    public Options getOptions() throws IllegalStateException {
        Operation currentOperation = getCurrentOperation();
        God currentGod = currentPlayer.getGod();
        Tile[][] boardClone = gameController.getGameState().getIslandBoard().clone();

        switch (currentOperation) {
            case MOVE:
                return new TileOptions(currentPlayer, athenaTileToMove(currentGod.getWorker()),
                        boardClone, currentOperation, "These are the Tiles where you can move");
            case BUILD:
                return new TileOptions(currentPlayer, currentGod.tileToBuild(currentGod.getWorker().getIndexTile()),
                        boardClone, currentOperation, "These are the Tiles where you can build");
            case CHOOSE:
                return new ConfirmOptions(currentPlayer, currentGod.getChoiceMessage(), boardClone);
            case SELECT_WORKER:
                Collection<Tile.IndexTile> indexTiles = new ArrayList<>();
                Worker[] workers = currentPlayer.getWorker();
                for (Worker w : workers) {
                    //with this check game does not pass as option a worker who can't move
                    if (athenaTileToMove(w).size() > 0) indexTiles.add(w.getIndexTile());
                }
                if (indexTiles.size() == 0) return null;

                return new TileOptions(currentPlayer, indexTiles, boardClone, currentOperation, "Choose one of your workers");
            case SEND_MESSAGE:
                return new MessageOption(currentPlayer, currentGod.getChoiceNotAllowedMessage());
            default:
                throw new IllegalStateException("Invalid current operation in Turn of " + currentPlayer.getNickname());
        }
    }
}
