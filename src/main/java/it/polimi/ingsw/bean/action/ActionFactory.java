package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.List;

/**
 * Class used to create the correct action in response to a given operation
 *
 * @see Action
 * @see Operation
 */
public class ActionFactory {

    /**
     * Create an instance of the correct type of action based on the parameters passed.
     * The created action also contains the correct arguments.
     * @param operation The current operation that came with the previous options.
     * @param object Contains the arguments to put into the new action. It can be of various types.
     * @param nickname The player's nickname who generated the action.
     * @return The correct action that will be send to the server.
     * @see Operation
     */
    public static Action createAction(Operation operation, Object object, String nickname) {
        Action action;
        switch (operation) {
            case MOVE:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new MoveGameAction((Tile.IndexTile) object, nickname);
                break;
            case BUILD:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new BuildGameAction((Tile.IndexTile) object, nickname);
                break;
            case CHOOSE:
                if (!(object instanceof Boolean)) throw new IllegalArgumentException();
                action = new ConfirmGameAction((Boolean) object, nickname);
                break;
            case CHOOSE_GOD:
                if (!(object instanceof List)) throw new IllegalArgumentException();
                action = new SelectGodTurnAction((List<GodDescription>) object, nickname);
                break;
            case PLACE_WORKERS:
                if (!(object instanceof Tile.IndexTile[])) throw new IllegalArgumentException();
                action = new PlaceWorkerActions((Tile.IndexTile[]) object, nickname);
                break;
            case POSEIDON_BUILD:
                if (!(object instanceof List)) throw new IllegalArgumentException();
                if (!(((List) object).get(0) instanceof Tile.IndexTile && ((List) object).get(1) instanceof Integer))
                    throw new IllegalArgumentException();
                action = new PoseidonBuildGameAction((Tile.IndexTile) ((List) object).get(0), (int) ((List) object).get(1), nickname);
                break;
            case REMOVE_BLOCK:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new RemoveBlock((Tile.IndexTile) object, nickname);
                break;
            case SELECT_WORKER:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new SelectWorkerGameAction((Tile.IndexTile) object, nickname);
                break;
            case SELECT_OPPONENTS_WORKER:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new SelectOpponentWorkerGameAction((Tile.IndexTile) object, nickname);
                break;
            case SELECT_NICKNAME:
                if (object != null) throw new IllegalArgumentException();
                action = new SelectNicknameAction(nickname);
                break;
            case SELECT_LOBBY_SIZE:
                action = new LobbySizeAction((int) object, nickname);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return action;
    }
}
