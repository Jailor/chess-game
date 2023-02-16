package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Images;
import com.andrei.game.Tile;

public class Knight extends Piece{
    public Knight(Colour colour) {
        super(colour,colour == Colour.WHITE ? Images.WHITE_KNIGHT : Images.BLACK_KNIGHT);
    }

    /**
     *
     * @return 0 if valid move, -1 if bad colour, -2 if invalid move
     */
    @Override
    public int checkMove(ChessBoard board, Tile start, Tile end) {
        if(end.getPiece()!= null && end.getPiece().getColour() == this.getColour()) return -1; //bad colour
        int dx = Math.abs(end.getX()-start.getX());
        int dy = Math.abs(end.getY()-start.getY());
        if(dx*dy == 2) return 0; //valid move
        return -2; //invalid move
    }

    @Override
    public String toString() {
        return "KNIGHT_" + getColour();
    }
}
