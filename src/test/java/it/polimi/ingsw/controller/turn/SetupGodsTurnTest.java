package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.action.SelectGodAction;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.god.GodDescription;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SetupGodsTurnTest {

    SetupGodsTurn turn;
    GameController controller;
    GameState model;

    @Before
    public void setUp() {
        Set<String> players = new LinkedHashSet<>(Arrays.asList("juri", "fra", "nick"));
        model = new GameState(players);
        controller = new GameController(model);
        turn = (SetupGodsTurn) controller.getTurn();
    }

    @Test
    public void endCurrentOperation() {
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        assertEquals(turn.getCurrentOperation(), Operation.CHOOSE_GOD);
        turn.endCurrentOperation();
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        assertEquals(turn.getCurrentOperation(), Operation.CHOOSE_GOD);
        turn.endCurrentOperation();
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        assertEquals(turn.getCurrentOperation(), Operation.CHOOSE_GOD);
        turn.endCurrentOperation();
        assertNotEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
    }

    @Test(expected = IllegalStateException.class)
    public void switchTurn() throws IllegalStateException {
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        turn.switchTurn();
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(1));
        turn.switchTurn();
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(2));
        turn.switchTurn();
        assertEquals(controller.getTurn().getCurrentPlayer(), model.getPlayers().get(0));
    }

    @Test
    public void turnFlow() {
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
        controller.update(new SelectGodAction(turn.getCurrentPlayer(), GodDescription.APOLLO));

        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
        controller.update(new SelectGodAction(turn.getCurrentPlayer(), GodDescription.PAN));

        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
        controller.update(new SelectGodAction(turn.getCurrentPlayer(), GodDescription.ARTEMIS));

        assertEquals(model.getPlayers().get(1), turn.getCurrentPlayer());
        controller.update(new SelectGodAction(turn.getCurrentPlayer(), GodDescription.APOLLO));
        assertEquals(GodDescription.APOLLO, model.getPlayers().get(1).getGod().getGodDescription());

        assertEquals(model.getPlayers().get(2), turn.getCurrentPlayer());
        controller.update(new SelectGodAction(turn.getCurrentPlayer(), GodDescription.ARTEMIS));
        assertEquals(GodDescription.ARTEMIS, model.getPlayers().get(2).getGod().getGodDescription());

        assertEquals(GodDescription.PAN, model.getPlayers().get(0).getGod().getGodDescription());
        assertTrue(controller.getTurn() instanceof SetupWorkersTurn);
    }
}