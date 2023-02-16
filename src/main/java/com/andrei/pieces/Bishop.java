package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Images;
import com.andrei.game.Tile;

public class Bishop extends Piece{
    public Bishop(Colour colour) {
        super(colour, colour == Colour.WHITE ? Images.WHITE_BISHOP : Images.BLACK_BISHOP);
    }

    @Override
    public int checkMove(ChessBoard board, Tile start, Tile end) {
        if(end.getPiece()!= null && end.getPiece().getColour() == this.getColour()) return -1; //bad colour
        int dx = Math.abs(end.getX()-start.getX());
        int dy = Math.abs(end.getY()-start.getY());
        if(dx==dy)
        {
            if(hasPathBishop(board,start,end)) return 0;
            else return -3; //piece in the way
        }
        else return -2; //not a valid move for bishop
    }

    /**
     * Checks if there is a piece in the way diagonally, the 4 diagonal cases of
     * values are given by dx and dy.
     * @param board the board
     * @param start the start tile
     * @param end the end tile
     * @return true if there is no piece in the way diagonally, false otherwise
     */
    public static boolean hasPathBishop(ChessBoard board, Tile start, Tile end)
    {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if(dx > 0 && dy >0)
        {
            for (int i = start.getX()+1, j= start.getY()+1; i < end.getX(); i++,j++) {
                if(board.getTile(i, j).getPiece() != null) return false;
            }
            return true;
        }

        if(dx > 0 && dy < 0)
        {
            for (int i = start.getX()+1, j= start.getY() - 1; i < end.getX(); i++,j--) {
                if(board.getTile(i, j).getPiece() != null) return false;
            }
            return true;
        }

        if(dx < 0 && dy > 0)
        {
            for (int i = start.getX()-1, j= start.getY() + 1; j < end.getY(); i--,j++) {
                if(board.getTile(i, j).getPiece() != null) return false;
            }
            return true;
        }

        if(dx < 0 && dy < 0)
        {
            for (int i = start.getX()-1, j= start.getY() - 1; j > end.getY(); i--,j--) {
                if(board.getTile(i, j).getPiece() != null) return false;
            }
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "BISHOP_" + getColour();
    }

}
