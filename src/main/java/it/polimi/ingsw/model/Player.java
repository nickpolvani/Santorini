package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.God;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Francesco Puoti
 */
public class Player implements Serializable {

    /**
     * Name Inserted by the player during the game's initialization
     */
    private final String nickname;

    private transient final GameState gameState;

    private final Color color;
    /**
     * The reference to the player's workers
     */
    private Worker[] workers;
    /**
     * it's provides the reference of the god selected by the player, who will use it for all the game
     */
    private God god;

    private boolean winner;

    private boolean looser;

    /**
     * Default constructor
     * When the game is initialized, every SocketClientConnection has to provide his nickname. In this way, when we create the player's instance,
     * we set also his nickname. Therefore, the setter of nickname is unnecessary and useless.
     */
    public Player(String nickname, GameState gameState, Color color) {
        this.nickname = nickname;
        this.gameState = gameState;
        this.winner = false;
        this.looser = false;
        this.color = color;
        Logger.getLogger("Server").debug("Created player " + nickname + " color: " + color);
    }

    /**
     * @return player's nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * @return this.worker
     */
    public Worker[] getWorkers() {
        return this.workers;
    }

    /**
     * @param indexes : is the TileIndexes' array selected by the player during the setup of the game
     */
    public void setWorkers(Tile.IndexTile[] indexes) throws AlreadySetException {
        if (this.workers != null) throw new AlreadySetException("Team already set");
        this.workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker(indexes[i], this.color);
            try {
                gameState.getIslandBoard().getTile(indexes[i]).setCurrentWorker(workers[i]);
            } catch (AlreadyOccupiedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param selectedGod : this parameter comes form the controller, that claims the player's choice of the god
     */
    public void setGod(God selectedGod) {
        this.god = selectedGod;
    }

    /**
     * @return this.god
     */
    public God getGod() {
        return this.god;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isLooser() {
        return looser;
    }

    public void setLooser(boolean looser) {
        this.looser = looser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return winner == player.winner &&
                looser == player.looser &&
                Objects.equals(nickname, player.nickname) &&
                color == player.color &&
                Arrays.equals(workers, player.workers) &&
                Objects.equals(god.getGodDescription(), player.god.getGodDescription());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nickname, gameState, color, god, winner, looser);
        result = 31 * result + Arrays.hashCode(workers);
        return result;
    }
}