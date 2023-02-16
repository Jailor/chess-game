package com.andrei.game;

import com.andrei.pieces.Piece;

/**
 * Class that represents a tile on the chess board.
 * Has a colour, a piece and coordinates. If no piece is on the tile, the piece field is null.
 * Coordinates are 0-7 and assumed to be valid.
 */
public class Tile {
    private int x;
    private int y;
    private Colour colour;
    private Piece piece;

    public Tile(int x, int y, Colour colour, Piece piece) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.piece = piece;
    }
    public Tile(int x, int y,Colour colour) {
        this(x,y,colour,null);
    }
    public Tile(int x, int y) {
        this(x,y,Colour.WHITE,null);
    }

    @Override
    public String toString() {
        return "TILE "+x +" "+y;
    }

    //GETTERS AND SETTERS
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
