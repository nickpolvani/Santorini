package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ActionFactoryTest {

    @Test
    public void createAction() {
        String s = "juri";
        Tile.IndexTile index = new Tile.IndexTile(0, 0);

        Action action = new MoveGameAction(index, s);
        assertEquals(action, ActionFactory.createAction(Operation.MOVE, index, s));

        action = new BuildGameAction(new Tile.IndexTile(0, 0), s);
        assertEquals(action, ActionFactory.createAction(Operation.BUILD, index, s));

        action = new ConfirmGameAction(true, s);
        assertEquals(action, ActionFactory.createAction(Operation.CHOOSE, true, s));

        action = new SelectGodTurnAction(Collections.singletonList(GodDescription.APOLLO), s);
        assertEquals(action, ActionFactory.createAction(Operation.CHOOSE_GOD, Collections.singletonList(GodDescription.APOLLO), s));

        Tile.IndexTile[] position = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        action = new PlaceWorkerActions(position, s);
        assertEquals(action, ActionFactory.createAction(Operation.PLACE_WORKERS, position, s));

        action = new SelectWorkerGameAction(index, s);
        assertEquals(action, ActionFactory.createAction(Operation.SELECT_WORKER, index, s));

        action = new SelectOpponentWorkerGameAction(index, s);
        assertEquals(action, ActionFactory.createAction(Operation.SELECT_OPPONENTS_WORKER, index, s));

        action = new SelectNicknameAction(s);
        assertEquals(action, ActionFactory.createAction(Operation.SELECT_NICKNAME, null, s));

        action = new LobbySizeAction(2, s);
        assertEquals(action, ActionFactory.createAction(Operation.SELECT_LOBBY_SIZE, 2, s));

    }
}