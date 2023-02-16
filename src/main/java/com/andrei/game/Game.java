package com.andrei.game;


import com.andrei.Main;
import com.andrei.except.IllegalMoveException;
import com.andrei.pieces.*;

import static com.andrei.Main.TEST_SETUP;
import static com.andrei.game.Images.*;

/**
 * Class that represents the game itself.
 * Has a ChessBoard object and a turn variable, and a score for each player.
 * Optionally has a test setup. The main method is the move method, called from the controller.
 */
public class Game {
    private Colour turn = Colour.WHITE;
    private ChessBoard board;
    private int blackScore = 0;
    private int whiteScore = 0;

    public Game() {
        board = new ChessBoard(TEST_SETUP);
    }

    /**
     * The method check if the x and y coordinates are in the range of the board.
     * If they are, we check if we are trying to move an empty tile. If not,
     * we grab the start and end pieces and check if we are the correct colour for this turn.
     * If the move is valid, we check if it causes check. If it doesn't, we move the piece.
     * @param x1 - start x coordinate
     * @param y1 - start y coordinate
     * @param x2 - end x coordinate
     * @param y2 - end y coordinate
     * @throws IllegalMoveException - if the move is not valid. A message is provided.
     */
    public void move(int x1, int y1, int x2, int y2) throws IllegalMoveException
    {
        if(!isInRange(x1,y1,x2,y2))throw new IllegalMoveException("Out of bounds!");
        Tile start = board.boardMatrix[x1][y1];
        Tile end = board.boardMatrix[x2][y2];

        if (start.getPiece() == null) throw new IllegalMoveException("Cannot move an empty tile!");

        if(start == end) throw new IllegalMoveException("Can't move onto the same tile!");

        Piece startPiece = start.getPiece();
        Piece endPiece = end.getPiece();

        if (!colorMatchTurn(startPiece))
            throw new IllegalMoveException("Choose correct colour piece for your turn! Current turn: " + turn);

        if (startPiece.checkMove(board, start, end) != 0)  throw new IllegalMoveException("Not a valid move!");

        if (board.causesCheck(start, end, turn)) throw new IllegalMoveException("Move will cause check!");

        if(isCastlingMove(startPiece, endPiece)) doCastling(start, end);
        else {
            movePiece(start, end);
            end.getPiece().setHasMoved(true);
        }
        changeTurn();
    }

    /**
     * Moves a piece from start to end, and captures the piece on end if there is one.
     * @param start - start tile
     * @param end - end tile
     * @return the piece that was captured, or null if no piece was captured
     */
    public static Piece movePiece(Tile start, Tile end)
    {
        Piece aux = end.getPiece();
        end.setPiece(start.getPiece());
        start.setPiece(null);
        return aux;
    }

    /**
     * Swaps the pieces on the tiles start and end.
     * @param start - start tile
     * @param end - end tile
     */
    public static void swapPieces(Tile start, Tile end)
    {
        //assume that move is valid
        Piece aux = end.getPiece();
        end.setPiece(start.getPiece());
        start.setPiece(aux);
    }

    /**
     * Reset the game, but keep the score.
     */
    public void reset()
    {
        turn = Colour.WHITE;
        board = new ChessBoard(TEST_SETUP);
    }

    /**
     * Does a castling move.
     * @param start - start tile, must be the king
     * @param end - end tile
     */
    public void doCastling(Tile start, Tile end)
    {
        Piece startPiece = start.getPiece();
        if(startPiece.getColour() == Colour.WHITE)
        {
                if(end.getY() == 0)
                {
                    board.boardMatrix[0][2].setPiece(new King(Colour.WHITE));
                    board.boardMatrix[0][2].getPiece().setHasMoved(true);

                    board.boardMatrix[0][3].setPiece(new Rook(Colour.WHITE));
                    board.boardMatrix[0][3].getPiece().setHasMoved(true);
                }
                else
                {
                    board.boardMatrix[0][6].setPiece(new King(Colour.WHITE));
                    board.boardMatrix[0][6].getPiece().setHasMoved(true);

                    board.boardMatrix[0][5].setPiece(new Rook(Colour.WHITE));
                    board.boardMatrix[0][5].getPiece().setHasMoved(true);
                }
        }
        else
        {
                if(end.getY() == 0)
                {
                    board.boardMatrix[7][2].setPiece(new King(Colour.BLACK));
                    board.boardMatrix[7][2].getPiece().setHasMoved(true);
                    board.boardMatrix[7][3].setPiece(new Rook(Colour.BLACK));
                    board.boardMatrix[7][3].getPiece().setHasMoved(true);
                }
                else
                {
                    board.boardMatrix[7][6].setPiece(new King(Colour.BLACK));
                    board.boardMatrix[7][6].getPiece().setHasMoved(true);
                    board.boardMatrix[7][5].setPiece(new Rook(Colour.BLACK));
                    board.boardMatrix[7][5].getPiece().setHasMoved(true);
                }
        }
        start.setPiece(null);
        end.setPiece(null);
    }

    /**
     * Checks if there are any pawns that need to be promoted.
     * The rows 0 and 7 are checked for pawns of the corresponding colour.
     * @return true if a pawn needs to be promoted, false otherwise
     */
    public boolean needsPromote()
    {
        for(int i=0; i<8; i++)
        {
            Piece candidatePiece = board.boardMatrix[0][i].getPiece();
            if(candidatePiece instanceof Pawn)
            {
                return true;
            }
            candidatePiece = board.boardMatrix[7][i].getPiece();
            if(candidatePiece instanceof Pawn)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for the pawns on the rows 0 and 7 and promotes them to the piece specified by the parameter.
     * @param piece - a string value of the piece to promote to
     */
    public void promotePawns(String piece)
    {
        for(int i=0; i<8; i++)
        {
            Piece candidatePiece = board.boardMatrix[0][i].getPiece();
            if(candidatePiece instanceof Pawn)
            {
                if(piece.equalsIgnoreCase("queen"))
                {
                    board.boardMatrix[0][i].setPiece(new Queen(Colour.BLACK));
                }
                else if(piece.equalsIgnoreCase("rook"))
                {
                    board.boardMatrix[0][i].setPiece(new Rook(Colour.BLACK));
                }
                else if(piece.equalsIgnoreCase("bishop"))
                {
                    board.boardMatrix[0][i].setPiece(new Bishop(Colour.BLACK));
                }
                else if(piece.equalsIgnoreCase("knight"))
                {
                    board.boardMatrix[0][i].setPiece(new Knight(Colour.BLACK));
                }
                board.boardMatrix[0][i].getPiece().setHasMoved(true);
                break;
            }

            candidatePiece = board.boardMatrix[7][i].getPiece();

            if(candidatePiece instanceof Pawn)
            {
                if(piece.equalsIgnoreCase("queen"))
                {
                    board.boardMatrix[7][i].setPiece(new Queen(Colour.WHITE));
                }
                else if(piece.equalsIgnoreCase("rook"))
                {
                    board.boardMatrix[7][i].setPiece(new Rook(Colour.WHITE));
                }
                else if(piece.equalsIgnoreCase("bishop"))
                {
                    board.boardMatrix[7][i].setPiece(new Bishop(Colour.WHITE));
                }
                else if(piece.equalsIgnoreCase("knight"))
                {
                    board.boardMatrix[7][i].setPiece(new Knight(Colour.WHITE));
                }
                board.boardMatrix[7][i].getPiece().setHasMoved(true);
                break;
            }
        }
    }

    /**
     * Checks if the move is a castling move.
     * Castling can only be done with a king and a rook of the same colour, and the king
     * must be the first piece.
     * @return true if the move is a castling move, false otherwise
     */
    public boolean isCastlingMove(Piece startPiece, Piece endPiece)
    {
        return startPiece instanceof King && endPiece instanceof Rook && startPiece.getColour() == endPiece.getColour();
    }
    public boolean colorMatchTurn(Piece startPiece){
        return startPiece.getColour() == Colour.WHITE && turn == Colour.WHITE ||
                startPiece.getColour() == Colour.BLACK && turn == Colour.BLACK;
    }
    public void changeTurn()
    {
        if (turn == Colour.WHITE) turn = Colour.BLACK;
        else turn = Colour.WHITE;
    }
    public static boolean isInRange(int x1, int y1, int x2, int y2)
    {
        return isInRange(x1, y1) && isInRange(x2, y2);
    }
    public static boolean isInRange(int x, int y)
    {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    //GETTERS AND SETTERS

    public ChessBoard getBoard() {
        return board;
    }

    public Colour getTurn() {
        return turn;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void setBlackScore(int blackScore) {
        this.blackScore = blackScore;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public void setWhiteScore(int whiteScore) {
        this.whiteScore = whiteScore;
    }
}
