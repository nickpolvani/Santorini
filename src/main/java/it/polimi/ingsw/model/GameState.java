package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.GodsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Puoti
 */
public class GameState {
    /**
     * The board where the game is about to be played
     */
    private final IslandBoard board;
    /**
     *
     */
    private final List<Player> players;
    private final GodsFactory godsFactory;
    /**
     *
     */
    private Player challenger;
    /**
     * TODO qua ricordiamoci di settare in maniera dinamica il turn
     */
    private Turn turn;

    /**
     * Default constructor: the most important thing is that island board can be instanced one time per game. Therefore,
     * there's no setter for islandBoard.
     */
    public GameState() {
        this.board = new IslandBoard();
        this.players = new ArrayList<>();
        this.godsFactory = new GodsFactory();
    }

    /**
     * @return islandBoard: the real instance, not a clone of it.
     */
    public IslandBoard getIslandBoard() {
        return board;
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
     * getter of ArrayList players is useless beacuase we have methods like  nextPlayer and getCurrrentPlayer in Turn objcet
     * but we use it for testing.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * if any player has chosen Athena, there's already an instance of AthenaTurn built.
     * so we can't create an instance of "normal" turn, otherwise we loose useful boolean for Athena.
     */
    public void setTurn(Turn turn) throws AlreadySetException {
        if (this.turn != null) throw new AlreadySetException();
        this.turn = turn;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public GodsFactory getGodsFactory() {
        return godsFactory;
    }

    /**
     * @return the player who has to play his turn
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
        //TODO questo controllo va fatto ma da qualche parte dobbiamo settare quanti player ci sono nella lobby. Poi si fa il controllo con quello.
        if (this.players.size() == 3) throw new IndexOutOfBoundsException("Maximum number of players already reached");
        this.players.add(player);
    }
}