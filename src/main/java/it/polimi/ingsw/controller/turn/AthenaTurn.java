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
import it.polimi.ingsw.observer.Observer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaTurn extends BasicTurn {
    private Athena athena;


    public AthenaTurn(GameController gameController, Player currentPlayer, List<Observer<Options>> observerList) {

        this.currentPlayer = currentPlayer;
        this.currentPlayer.getGod().resetGodState();
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
        this.gameController = gameController;
        this.observers.addAll(observerList);

        for (Player p : gameController.getGameState().getPlayers()) {
            if (p.getGod().getNameAndDescription() == GodNameAndDescription.ATHENA) {
                athena = (Athena) p.getGod();
                break;
            }
        }

        if (athena == null) throw new RuntimeException("Athena is not set despite we are in AthenaTurn");

        notify(getOptions());
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
            handleLooserNotification();
        }

    }

    /**
     * @param w: worker selected by the player at the beginning of a turn
     * @return collection of tiles where workers can move according to Athena's Power
     */
    protected Collection<Tile.IndexTile> athenaTileToMove(Worker w) {
        Collection<Tile.IndexTile> godTile = currentPlayer.getGod().tileToMove(w.getIndexTile());

        if (!athena.getCanMoveUp()) {
            final int currentWorkerLevel = gameController.getGameState().getIslandBoard().getTile(w.getIndexTile()).getBuildingLevel();

            return godTile.stream()
                    .filter(t -> (gameController.getGameState().getIslandBoard().getTile(t).getBuildingLevel() - currentWorkerLevel) == 0)
                    .collect(Collectors.toList());
        } else {
            return godTile;
        }

    }


    /**
     * Since Athena could limit Artemis'power, we decide to let AthenaTurn handle
     * this situation. So we decide override endCurrentOperation to handle ChooseOperation.
     */
    @Override
    public void endCurrentOperation() {

        if (getCurrentPlayer().isWinner()) {
            handleWinnerNotification();
        }

        if (getCurrentOperation().equals(Operation.CHOOSE)) {

            if (currentPlayer.getGod().getNameAndDescription() == GodNameAndDescription.ARTEMIS
                    && currentPlayer.getGod().isConfirmed()
                    && athenaTileToMove(currentPlayer.getGod().getWorker()).size() == 0) {
                turnOperations = new LinkedList<>(Arrays.asList(Operation.SEND_MESSAGE, Operation.BUILD));
            } else {
                this.turnOperations = getCurrentPlayer().getGod().getRemainingOperations();
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
    public Options getOptions() {
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
                return new ConfirmOptions(currentPlayer, currentGod.getNameAndDescription().getDescriptionOfPower() +
                        "\nDo you want to use your god's power? (Yes/No)", boardClone);
            case SELECT_WORKER:
                Collection<Tile.IndexTile> indexTiles = new ArrayList<>();
                Worker[] workers = currentPlayer.getWorker();
                for (Worker w : workers) {
                    //with this check game does not pass as option a worker who can't move
                    if (athenaTileToMove(w).size() > 0) indexTiles.add(w.getIndexTile());
                }
                if (indexTiles.size() == 0) {
                    return null;
                } else {
                    return new TileOptions(currentPlayer, indexTiles, boardClone, currentOperation, "Choose one of your workers");
                }
            case SEND_MESSAGE:
                return new MessageOption(currentPlayer, currentGod.getChoiceNotAllowedMessage());
            default:
                throw new IllegalStateException("Invalid current operation in Turn of " + currentPlayer.getNickname());
        }
    }
}
