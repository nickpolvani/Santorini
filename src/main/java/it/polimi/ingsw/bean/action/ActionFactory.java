package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

public class ActionFactory {

    public static Action createAction(Options options, Object object, String nickname) {
        Action action;
        switch (options.getCurrentOperation()) {
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
                if (!(object instanceof GodDescription)) throw new IllegalArgumentException();
                action = new SelectGodTurnAction((GodDescription) object, nickname);
                break;
            case SEND_MESSAGE:
                if (object != null) throw new IllegalArgumentException();
                action = new NodGameAction(nickname);
                break;
            case PLACE_WORKERS:
                if (!(object instanceof Tile.IndexTile[])) throw new IllegalArgumentException();
                action = new PlaceWorkerActions((Tile.IndexTile[]) object, nickname);
                break;
            case SELECT_WORKER:
                if (!(object instanceof Tile.IndexTile)) throw new IllegalArgumentException();
                action = new SelectWorkerGameAction((Tile.IndexTile) object, nickname);
                break;
            case SELECT_NICKNAME:
                action = new ChooseNicknameAction(nickname);
                break;
            case SELECT_LOBBY_SIZE:
                action = new LobbySizeAction(nickname, (int) object);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return action;
    }
}