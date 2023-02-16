package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Images;
import com.andrei.game.Tile;

public class Queen extends Piece{
    public Queen(Colour colour) {
        super(colour, colour == Colour.WHITE ? Images.WHITE_QUEEN : Images.BLACK_QUEEN);
    }

    /**
     * Checks if the Queen has a valid move. We check if the move is valid for a Rook or a Bishop.
     * @return whether the Queen has a valid move
     */
    @Override
    public int checkMove(ChessBoard board, Tile start, Tile end) {
        if(end.getPiece()!= null && end.getPiece().getColour() == this.getColour()) return -1; //bad colour
        if(end.getX() == start.getX() || start.getY()==end.getY())
        {
            if(Rook.hasPathRook(board,start,end))
            {
                return 0;
            }
            else return -3; //piece in the way
        }
        int dx = Math.abs(end.getX()-start.getX());
        int dy = Math.abs(end.getY()-start.getY());
        if(dx==dy)
        {
            if(Bishop.hasPathBishop(board,start,end)) return 0;
            else return -3; //piece in the way
        }
        return -2; //not a valid move for Queen
    }

    @Override
    public String toString() {
        return "QUEEN_" + getColour();
    }
}
