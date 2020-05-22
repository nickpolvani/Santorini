package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.bean.action.PlaceWorkerActions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.observer.Observer;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SetupWorkersTurnTest {

    GameState model;
    GameController controller;
    SetupWorkersTurn turn;

    @Before
    public void setUp() {
        Set<String> players = new LinkedHashSet<>(Arrays.asList("juri", "fra", "nick"));
        model = new GameState(players);
        controller = new GameController(model, null);
        controller.setActionHandler(new ActionHandler());
        List<Observer<Options>> observers = new ArrayList<>();
        turn = new SetupWorkersTurn(controller, model.getPlayers().get(0), observers);
        controller.setTurn(turn);
    }

    @Test(expected = NullPointerException.class)
    public void flowTurn() {
        Tile.IndexTile position1 = new Tile.IndexTile(0, 0), position2 = new Tile.IndexTile(1, 1);

        for (int i = 0; i < model.getPlayers().size(); i++) {
            assertEquals(Operation.PLACE_WORKERS, turn.getCurrentOperation());
            assertEquals(model.getPlayers().get(i), turn.getCurrentPlayer());
            controller.update(new PlaceWorkerActions(new Tile.IndexTile[]{position1, position2},
                    turn.getCurrentPlayer().getNickname()));
            assertEquals(model.getPlayers().get(i).getWorkers().get(0),
                    model.getIslandBoard().getTile(position1).getCurrentWorker());
            assertEquals(model.getPlayers().get(i).getWorkers().get(1),
                    model.getIslandBoard().getTile(position2).getCurrentWorker());
            if (i == 0) {
                position1 = new Tile.IndexTile(1, 2);
                position2 = new Tile.IndexTile(2, 1);
            } else if (i == 1) {
                position1 = new Tile.IndexTile(4, 4);
                position2 = new Tile.IndexTile(3, 3);
            }
        }
    }
}