package it.polimi.ingsw.Model;

import java.util.ArrayList;


/**
 * @author Francesco Puoti
 */
public class GameState {
    /**
     *
     */
    private Player challenger;
    /**
     * The board where the game is about to be played
     */
    private final IslandBoard board;
    /**
     *
     */
    private ArrayList<Player> players;
    /**
     *
     */
    private Turn turn;

    /**
     * Default constructor: the most important thing is that island board can be instanced one time per game. Therefore,
     * there's no setter for islandBoard.
     */
    public GameState() {
        this.board = new IslandBoard();
        this.players = new ArrayList<>();
    }

    /**
     * @return islandBoard: the real instance, not a clone of it.
     */
    public IslandBoard getBoard() {
        return this.board;
    }

    /**
     * @return challenger of the match
     */
    public Player getChallenger() {
        return this.challenger;
    }

    /**
     * @param player chosen randomly for being the game's challenger
     */
    public void setChallenger(Player player) {
        this.challenger = player;
    }

    /**
     * @return the player who has to play his turn
     * <p>
     * indexOf return '-1' in case of parameter does not belong to players. Therefore, at the first initialization of Turn
     * currentPlayer would be null so indexOF returns '-1', so the first player would be players.get(0)
     */
    public Player getNextPlayer(Player currentPlayer) {
        int numOfCurrentPlayer = this.players.indexOf(currentPlayer);

        try {
            return this.players.get(numOfCurrentPlayer + 1);
        } catch (IndexOutOfBoundsException e) {
            return this.players.get(0);
        }
    }

    /**
     * @param player : During the initialization of the game, you can add new player to the match.
     * @throws IndexOutOfBoundsException : more then three palyers are not accepted
     */
    public void addPlayer(Player player) throws IndexOutOfBoundsException {
        if (this.players.size() == 3) throw new IndexOutOfBoundsException("Maximum number of players already reached");
        this.players.add(player);
    }

    /**
     * getter of ArrayList players is useless beacuase we have methods like  nextPlayer and getCurrrentPlayer in Turn objcet
     * but we use it for testing.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setTurn() {
        this.turn = new Turn(this);
    }

    public Turn getTurn() {
        return this.turn;
    }
}