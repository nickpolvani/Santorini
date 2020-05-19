package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.action.SelectGodTurnAction;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.god.GodDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class SetupGodsTurnTest {

    SetupGodsTurn turn;
    GameController controller;
    GameState model;
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        Set<String> players = new LinkedHashSet<>(Arrays.asList("juri", "fra", "nick"));
        model = new GameState(players);
        controller = new GameController(model, null);
        turn = (SetupGodsTurn) controller.getTurn();
        turn.start();
    }

    @Test
    public void endCurrentOperation() {
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        turn.endCurrentOperation();
        assertNotEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
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
        List<GodDescription> listGods = new ArrayList<>();
        listGods.add(GodDescription.APOLLO);
        listGods.add(GodDescription.PAN);
        listGods.add(GodDescription.ARTEMIS);
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        controller.update(new SelectGodTurnAction(listGods, turn.getCurrentPlayer().getNickname()));

        listGods.remove(GodDescription.PAN);
        listGods.remove(GodDescription.ARTEMIS);
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(1), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(listGods, turn.getCurrentPlayer().getNickname()));
        assertEquals(GodDescription.APOLLO, model.getPlayers().get(1).getGod().getGodDescription());


        listGods.remove(GodDescription.APOLLO);
        listGods.add(GodDescription.ARTEMIS);
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(2), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(listGods, turn.getCurrentPlayer().getNickname()));
        assertEquals(GodDescription.ARTEMIS, model.getPlayers().get(2).getGod().getGodDescription());

        listGods.remove(GodDescription.ARTEMIS);
        listGods.add(GodDescription.PAN);
        assertEquals(GodDescription.PAN, model.getPlayers().get(0).getGod().getGodDescription());
        assertTrue(controller.getTurn() instanceof SetupWorkersTurn);
    }
}