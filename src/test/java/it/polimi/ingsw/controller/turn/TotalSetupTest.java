package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;

public class TotalSetupTest {
    GameState model;
    GameController controller;
    SetupTurn turn;

   /* @Before
    public void setUp() {
        Set<String> players = new LinkedHashSet<>(Arrays.asList("juri", "fra", "nick"));
        model = new GameState(players);
        controller = new GameController(model, null);
        List<Observer<Options>> observers = new ArrayList<>();
        turn = (SetupTurn) controller.getTurn();
        turn.start();
    }

    @Test
    public void flowSetupWithoutAthena() {

        GodDescription godChosen = GodDescription.APOLLO;
        for (int i = 0; i < model.getPlayers().size(); i++) {
            assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
            assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
            controller.update(new SelectGodTurnAction(godChosen, turn.getCurrentPlayer().getNickname()));
            if (i == 0) {
                godChosen = GodDescription.ARTEMIS;
            } else if (i == 1) {
                godChosen = GodDescription.MINOTAUR;
            }
        }

        godChosen = GodDescription.APOLLO;

        for (int i = 1; i < model.getPlayers().size(); i++) {
            assertEquals(model.getPlayers().get(i), turn.getCurrentPlayer());
            controller.update(new SelectGodTurnAction(godChosen, turn.getCurrentPlayer().getNickname()));
            assertEquals(godChosen, model.getPlayers().get(i).getGod().getGodDescription());
            if (i == 1) {
                godChosen = GodDescription.ARTEMIS;
            }
        }
        if (model.getPlayers().size() == 3) {
            assertEquals(GodDescription.MINOTAUR, model.getPlayers().get(0).getGod().getGodDescription());
        } else {
            assertEquals(GodDescription.ARTEMIS, model.getPlayers().get(0).getGod().getGodDescription());
        }

        assertTrue(controller.getTurn() instanceof SetupWorkersTurn);
        turn = (SetupTurn) controller.getTurn();
        setWorker();
        assertTrue(controller.getTurn() instanceof BasicTurn);
    }

    @Test
    public void flowSetupWithAthena() {

        GodDescription godChosen = GodDescription.ATHENA;
        for (int i = 0; i < model.getPlayers().size(); i++) {
            assertEquals(Operation.CHOOSE_GOD, turn.getCurrentOperation());
            assertEquals(model.getPlayers().get(0), turn.getCurrentPlayer());
            controller.update(new SelectGodTurnAction(godChosen, turn.getCurrentPlayer().getNickname()));
            if (i == 0) {
                godChosen = GodDescription.ARTEMIS;
            } else if (i == 1) {
                godChosen = GodDescription.MINOTAUR;
            }
        }

        godChosen = GodDescription.ATHENA;

        for (int i = 1; i < model.getPlayers().size(); i++) {
            assertEquals(model.getPlayers().get(i), turn.getCurrentPlayer());
            controller.update(new SelectGodTurnAction(godChosen, turn.getCurrentPlayer().getNickname()));
            assertEquals(godChosen, model.getPlayers().get(i).getGod().getGodDescription());
            if (i == 1) {
                godChosen = GodDescription.ARTEMIS;
            }
        }
        if (model.getPlayers().size() == 3) {
            assertEquals(GodDescription.MINOTAUR, model.getPlayers().get(0).getGod().getGodDescription());
        } else {
            assertEquals(GodDescription.ARTEMIS, model.getPlayers().get(0).getGod().getGodDescription());
        }
        assertTrue(controller.getTurn() instanceof SetupWorkersTurn);
        turn = (SetupTurn) controller.getTurn();

        setWorker();
        assertTrue(controller.getTurn() instanceof AthenaTurn);
    }

    private void setWorker() {
        Tile.IndexTile position1 = new Tile.IndexTile(0, 0), position2 = new Tile.IndexTile(1, 1);
        for (int i = 1; i <= model.getPlayers().size(); i++) {
            if (i == 3) i = 0;
            assertEquals(Operation.PLACE_WORKERS, turn.getCurrentOperation());
            assertEquals(model.getPlayers().get(i), turn.getCurrentPlayer());
            controller.update(new PlaceWorkerActions(new Tile.IndexTile[]{position1, position2},
                    turn.getCurrentPlayer().getNickname()));
            assertEquals(model.getPlayers().get(i).getWorkers()[0],
                    model.getIslandBoard().getTile(position1).getCurrentWorker());
            assertEquals(model.getPlayers().get(i).getWorkers()[1],
                    model.getIslandBoard().getTile(position2).getCurrentWorker());
            if (i == 1) {
                position1 = new Tile.IndexTile(1, 2);
                position2 = new Tile.IndexTile(2, 1);
            } else if (i == 2) {
                position1 = new Tile.IndexTile(4, 4);
                position2 = new Tile.IndexTile(3, 3);
            }
            if (i == 0) break;
        }
    }*/

}
