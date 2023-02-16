package com.andrei.game;

import com.andrei.pieces.*;

import java.util.ArrayList;

/**
 * Models the chess board: an 8x8 matrix of tiles.
 * Each tile has a piece on it, or is empty. The constructor fills the board with the
 * pieces in their starting positions.
 * <p>
 * Has an optional parameter for testing purposes. In test mode, the board is filled
 * with 2 kings and a white queen allowing for testing of the game logic (check, checkmate, stalemate).
 */
public class ChessBoard {
    public Tile[][] boardMatrix = new Tile[8][8];
    public ChessBoard(boolean test) {
        //ADD TILES: 8x8 matrix of tiles. Colour alternates between black and white.
        for(int i=0; i<8; i++)
        {
            for(int j=0; j < 8; j++)
            {
                if(i%2 == 0)
                {
                    if(j%2 == 0) boardMatrix[i][j] = new Tile(i,j, Colour.BLACK);
                    else boardMatrix[i][j] = new Tile(i,j,Colour.WHITE);
                }
                else
                {
                    if(j%2 == 0) boardMatrix[i][j] = new Tile(i,j,Colour.WHITE);
                    else boardMatrix[i][j] = new Tile(i,j,Colour.BLACK);
                }
            }
        }
        // testing mode
        if(test)
        {
            for(int i=0; i<8; i++)
            {
                for(int j=0; j < 8; j++)
                {
                    boardMatrix[i][j].setPiece(null);
                }
            }
            boardMatrix[7][7].setPiece(new King(Colour.BLACK));
            boardMatrix[6][5].setPiece(new King(Colour.WHITE));
            boardMatrix[0][6].setPiece(new Queen(Colour.WHITE));
            return;
        }

        //ADD PIECES: 8 pawns, 2 rooks, 2 knights, 2 bishops, 1 queen, 1 king for each colour.
        addPieces(boardMatrix);
    }

    /**
     * Populates the board with pieces in their starting positions.
     * Left hand corner is (0,0), right hand corner is (7,7).
     * @param boardMatrix the board matrix to add pieces to
     */
    void addPieces(Tile[][] boardMatrix){
        //ADD PAWNS
        for(int j=0; j < 8; j++)
        {
            boardMatrix[1][j].setPiece(new Pawn(Colour.WHITE));

            boardMatrix[6][j].setPiece(new Pawn(Colour.BLACK));
        }
        //ADD KNIGHTS
        boardMatrix[0][1].setPiece(new Knight(Colour.WHITE));
        boardMatrix[0][6].setPiece(new Knight(Colour.WHITE));


        boardMatrix[7][1].setPiece(new Knight(Colour.BLACK));

        boardMatrix[7][6].setPiece(new Knight(Colour.BLACK));

        //ADD ROOKS
        boardMatrix[0][0].setPiece(new Rook(Colour.WHITE));
        boardMatrix[0][7].setPiece(new Rook(Colour.WHITE));

        boardMatrix[7][0].setPiece(new Rook(Colour.BLACK));
        boardMatrix[7][7].setPiece(new Rook(Colour.BLACK));
        //ADD BISHOPS
        boardMatrix[0][2].setPiece(new Bishop(Colour.WHITE));
        boardMatrix[0][5].setPiece(new Bishop(Colour.WHITE));

        boardMatrix[7][2].setPiece(new Bishop(Colour.BLACK));
        boardMatrix[7][5].setPiece(new Bishop(Colour.BLACK));

        //ADD QUEENS
        boardMatrix[0][3].setPiece(new Queen(Colour.WHITE));

        boardMatrix[7][3].setPiece(new Queen(Colour.BLACK));
        //ADD KINGS
        boardMatrix[0][4].setPiece(new King(Colour.WHITE));

        boardMatrix[7][4].setPiece(new King(Colour.BLACK));
    }


    /**
     * Checks if the king of the given colour is in check.
     * The method will automatically find the king of the given colour.
     * It will then check if any of the other pieces can move to the king's position.
     * If any of the other pieces can move to the king's position, the king is in check.
     * The method checkMove() return 0 if the move is valid.
     * @param colour the colour of the king to find
     * @return boolean value indicating whether the king of the given colour is in check
     */
    public boolean isInCheck(Colour colour)
    {
        Tile king;
        if(colour == Colour.WHITE)
        {
            king = findKing(Colour.WHITE);
        }
        else //Colour.BLACK
        {
            king = findKing(Colour.BLACK);
        }

        for(int i=0; i<8; i++)
        {
            for(int j=0; j < 8; j++)
            {
                Tile sourceTile = boardMatrix[i][j];
                if(sourceTile.getPiece()!= null && sourceTile != king)
                {
                    if(sourceTile.getPiece().checkMove(this,sourceTile,king)==0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds the king of the given colour.
     * Iterates through the board matrix and returns the tile containing the king of the given colour.
     * @param colour the colour of the king to find
     * @return the tile containing the king of the given colour
     */
    public Tile findKing(Colour colour)
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j < 8; j++)
            {
                if(boardMatrix[i][j].getPiece() instanceof King && boardMatrix[i][j].getPiece().getColour() == colour)
                {
                    return  boardMatrix[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Checks if we have check mate.
     * @param colour the colour of the turn
     * @return boolean value indicating whether we have a check mate
     */
    public boolean isCheckMate(Colour colour)
    {
        if(!isInCheck(colour)) return false;
        return noMoreLegalMoves(colour);
    }

    /**
     * Checks if we have stalemate.
     * @param colour the colour of the turn
     * @return boolean value indicating whether we have a stalemate
     */
    public boolean isStalemate(Colour colour)
    {
        if(isInCheck(colour)) return false;
        return noMoreLegalMoves(colour);
    }


    /**
     * Checks if the player of the given colour has any legal moves.
     * Iterates through the board matrix and checks if any of the pieces of the given colour has any legal moves.
     * Of those legal moves, it checks if any of them do NOT cause check for our player. If at least
     * one move that does not cause check is found, the method returns false, i.e. the player has at least one legal move.
     * If no legal moves are found that do not cause check, the method returns true, i.e. the player has no more legal
     * moves to play.
     * @param colour the colour for the player
     * @return boolean value indicating whether the king of the given colour has any legal moves
     */
    private boolean noMoreLegalMoves(Colour colour) {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j < 8; j++)
            {
                if(boardMatrix[i][j].getPiece() != null && boardMatrix[i][j].getPiece().getColour() == colour)
                {
                    Piece piece = boardMatrix[i][j].getPiece();
                    ArrayList<Tile> arr = piece.getLegalMoves(this,boardMatrix[i][j]);
                    for(Tile candidateTile : arr)
                    {
                        if(!causesCheck(boardMatrix[i][j],candidateTile,colour))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Checks if the move from start to end causes check for the player of the given colour.
     * The piece is temporarily moved to the end tile and then the method checks if the king of the given colour is in check.
     * Then the piece is moved back to the start tile and the method returns the boolean value indicating whether the king
     * of the given colour is in check.
     * @param start the tile where the piece is located
     * @param end the tile where the piece is going to move
     * @param colour the colour of the player
     * @return boolean value indicating whether the move causes check for the player of the given colour
     */
    public boolean causesCheck(Tile start, Tile end, Colour colour)
    {
        Piece aux = Game.movePiece(start,end);
        boolean ok = this.isInCheck(colour);
        start.setPiece(aux);
        Game.swapPieces(start,end);
        return ok;
    }

    /**
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     * @return the tile at the given coordinates or null if the coordinates are out of bounds
     */
    public Tile getTile(int x, int y)
    {
        if(x < 0 || x > 7 || y < 0 || y > 7) return null;
        return boardMatrix[x][y];
    }

    /**
     * Utility method for printing the board to system out.
     * @param printPieces boolean value indicating whether to print the pieces or just the colours
     * @deprecated no longer using system out as a GUI has been implemented
     */
    @Deprecated
    public void printChessBoard(boolean printPieces)
    {
        if(printPieces)
        {
            for(int i=7; i>=0; i--)
            {
                System.out.print(i + ": ");
                for(int j=0; j<8; j++)
                {
                    System.out.print(j +" "+ boardMatrix[i][j].getPiece() + " ");
                }
                System.out.println();
            }
        }
        else
        {
            for(int i=7; i>=0; i--)
            {
                System.out.print(i + ": ");
                for(int j=0; j<8; j++)
                {
                    System.out.print(j + " " + boardMatrix[i][j].getColour() +" ");
                }
                System.out.println();
            }
        }

    }


}
