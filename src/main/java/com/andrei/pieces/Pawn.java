package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Images;
import com.andrei.game.Tile;

public class Pawn extends Piece{
    public Pawn(Colour colour) {
        super(colour, colour == Colour.WHITE ? Images.WHITE_PAWN : Images.BLACK_PAWN);
    }

    @Override
    public int checkMove(ChessBoard board , Tile start, Tile end) {
        if(end.getPiece()!= null && end.getPiece().getColour() == this.getColour()) return -1;
        if(this.getColour()== Colour.WHITE && end.getX()-start.getX() == 2  && (end.getY() == start.getY()) && !hasMoved())
        {
            if(end.getPiece() == null ) return 0; //valid move
            else return -1;
        }
        else if(this.getColour()== Colour.BLACK && end.getX()-start.getX() == -2  && (end.getY() == start.getY()) && !hasMoved())
        {
            if(end.getPiece() == null ) return 0; //valid move
            else return -1;
        }
        else if(this.getColour()== Colour.WHITE && end.getX()-start.getX() == 1  && (end.getY() == start.getY()))
        {
            if(end.getPiece() == null ) return 0; //valid move
            else return -1;
        }
        else if(this.getColour()== Colour.BLACK && end.getX()-start.getX() == -1  && (end.getY() == start.getY()))
        {
            if(end.getPiece() == null ) return 0; //valid move
            else return -1;
        }
        else if(this.getColour()== Colour.WHITE && end.getX() - start.getX() == 1 && (Math.abs(end.getY() - start.getY()) == 1))
        {
            if(end.getPiece() == null) return 1; //cannot attack empty square
            else return 0; //valid move
        }
        else if(this.getColour()== Colour.BLACK && end.getX() - start.getX() == -1 && (Math.abs(end.getY() - start.getY()) == 1))
        {
            if(end.getPiece() == null) return 1; //cannot attack empty square
            else return 0; //valid move
        }
        else return -2; //no such move for pawn

    }

    @Override
    public String toString() {
        return "PAWN_"+getColour();
    }
}
