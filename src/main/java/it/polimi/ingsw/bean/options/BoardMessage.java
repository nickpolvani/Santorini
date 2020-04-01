package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Tile;

/*Questa classe è il bean che avevo pensato prima di sentire l'idea di nick... La comunicazione Model-> View va ancora definita correttamente*/
public class BoardMessage {

    private final Tile[][] board;

    public BoardMessage(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return board;
    }

    /* Questo è il messaggio se non vogliamo passare tutta la board alla view ma vogliamo dirgli cosa è cambiato
    private Boolean built;
    private Boolean moved;
    private Worker worker;
    private Tile tile;
    private Boolean dome;
     */
}
