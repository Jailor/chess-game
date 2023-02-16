package com.andrei.pieces;

import com.andrei.game.ChessBoard;
import com.andrei.game.Colour;
import com.andrei.game.Tile;

import java.util.ArrayList;

public abstract class Piece {
    private Colour colour;
    private boolean hasMoved = false;
    private String imagePath;

    public Piece(Colour colour) {
        this.colour = colour;
    }
    public Piece(Colour colour, String imagePath) {
        this(colour);
        this.imagePath = imagePath;
    }

    /**
     * This method checks if the move is valid for the piece. Must be implemented by each piece.
     * @return 0 if valid move
     */
    public abstract int checkMove(ChessBoard board, Tile start, Tile end);


    /**
     * Using the checkMove method, this method returns an ArrayList of all the legal moves for the piece.
     * @return an ArrayList of all the legal moves for the piece
     */
    public ArrayList<Tile> getLegalMoves(ChessBoard board, Tile pieceTile)
    {
        Piece piece = pieceTile.getPiece();
        if(piece == null) return null;
        ArrayList<Tile> validMoves = new ArrayList<>();
        for(int i=0; i<8; i++)
        {
            for(int j=0; j < 8; j++)
            {
                if(board.boardMatrix[i][j] != pieceTile )
                {
                    if(piece.checkMove(board,pieceTile,board.boardMatrix[i][j])==0) validMoves.add(board.boardMatrix[i][j]);
                }
            }
        }
        return validMoves;
    }

    //GETTERS AND SETTERS


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
