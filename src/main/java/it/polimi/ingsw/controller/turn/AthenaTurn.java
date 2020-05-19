package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.ChooseOptions;
import it.polimi.ingsw.bean.options.MessageOption;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.Athena;
import it.polimi.ingsw.model.god.Charon;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.MessageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaTurn extends BasicTurn {
    private Athena athena;


    public AthenaTurn(GameController gameController, Player currentPlayer, List<Observer<Options>> observerList) {
        super(gameController, currentPlayer, observerList);
        this.currentPlayer.getGod().resetGodState();
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
        for (Player p : gameController.getGameState().getPlayers()) {
            if (p.getGod().getGodDescription() == GodDescription.ATHENA) {
                athena = (Athena) p.getGod();
                break;
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
            final int currentWorkerLevel = gameController.getGameState().getIslandBoard().getBuildingLevel(w.getIndexTile());

            return godTile.stream()
                    .filter(t -> (gameController.getGameState().getIslandBoard().getBuildingLevel(t) - currentWorkerLevel) < 1)
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
            this.turnOperations = getCurrentPlayer().getGod().getRemainingOperations();
        } else {
            turnOperations.poll();
        }
        handleRemainingOperations();
    }

    @Override
    protected void handleRemainingOperations() {
        if (turnOperations.isEmpty()) {
            switchTurn();
        } else if (getCurrentOperation() == Operation.BUILD && currentPlayer.getGod().cannotBuild()) {
            handleLooserNotification();
            switchTurn(); //this because after removing the looser whe have to notify the next player to play
        } else {
            if (getCurrentOperation() == Operation.CHOOSE) {
                if (currentPlayer.getGod().getGodDescription() == GodDescription.ARTEMIS) {
                    if (athenaTileToMove(currentPlayer.getGod().getWorker()).size() == 0) {
                        notify(new MessageOption(currentPlayer.getNickname(), MessageType.GODS_POWER_NOT_AVAILABLE, Operation.MESSAGE_NO_REPLY));
                        endCurrentOperation();
                    } else {
                        notify(getOptions());
                    }
                } else if (!currentPlayer.getGod().isChooseAvailable()) {
                    notify(new MessageOption(currentPlayer.getNickname(), MessageType.GODS_POWER_NOT_AVAILABLE, Operation.MESSAGE_NO_REPLY));
                    endCurrentOperation();
                } else {
                    notify(getOptions());
                }
            } else {
                notify(getOptions());
            }
        }
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
        IslandBoard boardClone = gameController.getGameState().getIslandBoard().clone();

        switch (currentOperation) {
            case MOVE:
                return new TileOptions(currentPlayer.getNickname(), athenaTileToMove(currentGod.getWorker()),
                        boardClone, currentOperation, MessageType.MOVE);
            case BUILD:
                return new TileOptions(currentPlayer.getNickname(), currentGod.tileToBuild(currentGod.getWorker().getIndexTile()),
                        boardClone, currentOperation, MessageType.BUILD);
            case CHOOSE:
                return new ChooseOptions(currentPlayer.getNickname(), currentGod.getGodDescription().getDescriptionOfPower() +
                        "\nDo you want to use your god's power? (Yes/No)");
            case SELECT_WORKER:
                Collection<Tile.IndexTile> indexTiles = new ArrayList<>();
                for (Worker w : currentPlayer.getWorkers()) {
                    //with this check game does not pass as option a worker who can't move
                    if (athenaTileToMove(w).size() > 0) indexTiles.add(w.getIndexTile());
                }
                if (indexTiles.size() == 0) {
                    return null;
                } else {
                    return new TileOptions(currentPlayer.getNickname(), indexTiles, boardClone, currentOperation, MessageType.SELECT_WORKER);
                }
            case SELECT_OPPONENTS_WORKER:
                if (!(currentGod instanceof Charon)) throw new IllegalArgumentException();
                return new TileOptions(currentPlayer.getNickname(), ((Charon) currentGod).opponentsWorkerTile(), boardClone,
                        currentOperation, MessageType.SELECT_OPPONENT_WORKER);
            default:
                throw new IllegalStateException("Invalid current operation in Turn of " + currentPlayer.getNickname());
        }
    }
}
