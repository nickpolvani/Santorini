package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.God;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Francesco Puoti
 */
public class Player {

    public final Color color;
    /**
     * Name Inserted by the player during the game's initialization
     */
    private final String nickname;
    /**
     * The GameState's instance of the current lobby
     */
    private final GameState gameState;
    /**
     * The reference to the player's workers
     */
    private List<Worker> workers;
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
     *
     * @param color     color of player's workers
     * @param gameState game in which the player participates
     * @param nickname  player's nickname
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
    public List<Worker> getWorkers() {
        return this.workers;
    }

    /**
     * @param indexes : is the TileIndexes' array selected by the player during the setup of the game
     * @throws AlreadySetException if workers are already instantiated for this player
     */
    public void setWorkers(Tile.IndexTile[] indexes) throws AlreadySetException {
        if (this.workers != null) throw new AlreadySetException("Team already set");
        this.workers = new ArrayList<>(Arrays.asList(
                new Worker(indexes[0], this.color),
                new Worker(indexes[1], this.color)
        ));
        for (int i = 0; i < workers.size(); i++) {
            try {
                gameState.getIslandBoard().getTile(indexes[i]).setCurrentWorker(workers.get(i));
            } catch (AlreadyOccupiedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return this.god
     */
    public God getGod() {
        return this.god;
    }

    /**
     * @param selectedGod : this parameter comes form the controller, that claims the player's choice of the god
     */
    public void setGod(God selectedGod) {
        this.god = selectedGod;
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
                workers.equals(player.workers) &&
                Objects.equals(god.getGodDescription(), player.god.getGodDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, gameState, color, workers, god, winner, looser);
    }
}