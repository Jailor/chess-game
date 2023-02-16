package com.andrei.except;

/**
 * An exception class that is thrown when a move,
 * for whatever reason, is illegal.
 */
public class IllegalMoveException extends Exception{
    public IllegalMoveException(String message) {
        super(message);
    }
}
