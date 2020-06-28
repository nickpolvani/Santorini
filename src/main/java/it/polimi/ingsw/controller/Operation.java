package it.polimi.ingsw.controller;

/**
 * Operation that a player can perform during his turn
 */
public enum Operation {
    // Setup operations
    CHOOSE_GOD,
    PLACE_WORKERS,
    SELECT_NICKNAME,
    SELECT_LOBBY_SIZE,

    // Turn operations
    BUILD,
    CHOOSE,
    MOVE,
    POSEIDON_BUILD,
    REMOVE_BLOCK,
    SELECT_WORKER,
    SELECT_OPPONENTS_WORKER,

    // Generic operation
    MESSAGE_NO_REPLY
}
