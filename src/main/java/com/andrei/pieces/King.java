package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Game;
import com.andrei.game.Tile;

import static com.andrei.game.Images.BLACK_KING;
import static com.andrei.game.Images.WHITE_KING;

public class King extends Piece{

    public King(Colour colour) {
        super(colour, colour == Colour.WHITE ? WHITE_KING : BLACK_KING);
    }


    /**
     * Check if we have a valid move for king. We can do castling if
     * the ned piece is a rook, the pieces have the same color, the pieces have not
     * moved, the king is not in check, no other pieces between the two and
     * we cannot castle into check.
     * Castling can only be done when there's a valid path (hasPathRook) and the king isn't in check
     * @return 0 if valid move, -1 if bad colour, -2 if invalid move
     */
    @Override
    public int checkMove(ChessBoard board, Tile start, Tile end) {
        boolean isEndPieceRook = end.getPiece()!= null && end.getPiece() instanceof Rook;
        boolean isSameColour = end.getPiece().getColour() == this.getColour();
        boolean piecesHaveNotMoved =  !this.hasMoved() && !end.getPiece().hasMoved();
        boolean notInCheck = !board.isInCheck(this.getColour());
        boolean checkPathRook = Rook.hasPathRook(board, end, start);
        if( isEndPieceRook && isSameColour  && piecesHaveNotMoved && notInCheck)
        {
            if(checkPathRook && canDoCastling(board,start,end))
            {
                return  0;
            }
            else return 2; //not a valid Castling
        }
        //Rest of the moves
        if(end.getPiece()!= null && isSameColour) return -1; //bad colour
        int dx = Math.abs(end.getX()-start.getX());
        int dy = Math.abs(end.getY()-start.getY());
        if(dx + dy == 1 || (dx ==1 && dy == 1)) return 0;
        return -2; //not a valid move for king
    }

    /**
     * Checks if we are in check after castling
     * @return true if we can do castling, false otherwise
     */
    public boolean canDoCastling(ChessBoard board,Tile start, Tile end)
    {
        boolean ok = false;
        Piece startPiece = start.getPiece();
        if(startPiece.getColour() == Colour.WHITE)
        {
            board.boardMatrix[0][4].setPiece(null);
            if(end.getY() == 0)
            {
                board.boardMatrix[0][2].setPiece(new King(Colour.WHITE));
                if(!board.isInCheck(Colour.WHITE)) ok=true;
                board.boardMatrix[0][2].setPiece(null);
            }
            else
            {
                board.boardMatrix[0][6].setPiece(new King(Colour.WHITE));
                if(!board.isInCheck(Colour.WHITE)) ok=true;
                board.boardMatrix[0][6].setPiece(null);
            }
            board.boardMatrix[0][4].setPiece(new King(Colour.WHITE));
        }
        else
        {
            board.boardMatrix[7][4].setPiece(null);
            if(end.getY() == 0)
            {
                board.boardMatrix[7][2].setPiece(new King(Colour.BLACK));
                if(!board.isInCheck(Colour.BLACK)) ok=true;
                board.boardMatrix[7][2].setPiece(null);
            }
            else
            {
            board.boardMatrix[7][6].setPiece(new King(Colour.BLACK));
                if(!board.isInCheck(Colour.BLACK)) ok=true;
                board.boardMatrix[7][6].setPiece(null);
            }
            board.boardMatrix[7][4].setPiece(new King(Colour.BLACK));
        }
        return ok;
    }

    @Override
    public String toString() {
        return "KING_" + getColour();
    }

}
