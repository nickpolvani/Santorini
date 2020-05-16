package it.polimi.ingsw.controller.turn;

public class SetupGodsTurnTest {

    /*SetupGodsTurn turn;
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
        assertEquals(turn.getCurrentPlayer(), model.getPlayers().get(0));
        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        turn.endCurrentOperation();
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
        controller.update(new SelectGodTurnAction(GodDescription.APOLLO, turn.getCurrentPlayer().getNickname()));

        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(GodDescription.PAN, turn.getCurrentPlayer().getNickname()));

        assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
        assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(GodDescription.ARTEMIS, turn.getCurrentPlayer().getNickname()));

        assertEquals(model.getPlayers().get(1), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(GodDescription.APOLLO, turn.getCurrentPlayer().getNickname()));
        assertEquals(GodDescription.APOLLO, model.getPlayers().get(1).getGod().getGodDescription());

        assertEquals(model.getPlayers().get(2), turn.getCurrentPlayer());
        controller.update(new SelectGodTurnAction(GodDescription.ARTEMIS, turn.getCurrentPlayer().getNickname()));
        assertEquals(GodDescription.ARTEMIS, model.getPlayers().get(2).getGod().getGodDescription());

        assertEquals(GodDescription.PAN, model.getPlayers().get(0).getGod().getGodDescription());
        assertTrue(controller.getTurn() instanceof SetupWorkersTurn);
    }*/
}