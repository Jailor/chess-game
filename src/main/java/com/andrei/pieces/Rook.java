package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Images;
import com.andrei.game.Tile;

public class Rook extends Piece{
    public Rook(Colour colour) {
        super(colour, colour == Colour.WHITE ? Images.WHITE_ROOK : Images.BLACK_ROOK);
    }

    /**
     * Checks if the move is valid for a rook.
     * Move must be on the same row or column, and there must be no pieces in the way.
     * @return 0 if valid move, -1 if bad colour, -2 if invalid move, -3 if piece in the way
     */
    @Override
    public int checkMove(ChessBoard board, Tile start, Tile end) {
        if(end.getPiece()!= null && end.getPiece().getColour() == this.getColour()) return -1; //bad colour
        if(end.getX() == start.getX() || start.getY()==end.getY())
        {
            if(hasPathRook(board,start,end))
            {
                return 0;
            }
            else return -3; //piece in the way
        }
        else return -2;

    }

    /**
     * Must check if there are any pieces in the way of the rook: line and column.
     * @return whether there is a path for a rook to move from start to end.
     */
    public static boolean hasPathRook(ChessBoard board, Tile start, Tile end)
    {
        if(start.getY() == end.getY())
        {
            if(end.getX() > start.getX())
            {
                for(int i = start.getX() + 1; i < end.getX(); i++)
                {
                   if(board.getTile(i, end.getY()).getPiece() != null) return false;
                }
            }
            else //end.getX < start.getX
            {
                for(int i = end.getX() + 1; i< start.getX(); i++)
                {
                    if(board.getTile(i, end.getY()).getPiece() != null) return false;
                }
            }
            return true;
        }
        if(start.getX() == end.getX())
        {
            if(end.getY() > start.getY())
            {
                for(int i = start.getY() + 1; i < end.getY(); i++)
                {
                    if(board.getTile(end.getX(), i).getPiece() != null) return false;
                }
            }
            else //end.getY < start.getY
            {
                for(int i = end.getY() + 1; i< start.getY(); i++)
                {
                    if(board.getTile(end.getX(), i).getPiece() != null) return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ROOK_" + getColour();
    }
}
