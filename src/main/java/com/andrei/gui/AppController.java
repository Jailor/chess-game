package com.andrei.gui;

import com.andrei.except.IllegalMoveException;
import com.andrei.game.Colour;
import com.andrei.game.Game;
import com.andrei.game.Tile;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import static com.andrei.game.Images.EMPTY_TILE;

/**
 * The AppController class is the controller for the app_view.fxml file.
 * All the logic related for the GUI elements is implemented here.
 */
public class AppController {
    private final String[] OPTIONS = {"Queen", "Knight", "Rook", "Bishop"};
    private boolean isSelecting = true;
    private int prevX = 0;
    private int prevY = 0;
    Game game = new Game();

    @FXML private AnchorPane appBoard;

    @FXML private Label errorLabel;

    @FXML private Label whiteScore;

    @FXML private Label blackScore;

    private final ImageView [][] imagesBoard = new ImageView[8][8];

    /**
     * Handles the click of the mouse. It calculates the coordinates of the tiles,
     * and the moves the piece from the previous coordinates to the new ones if we are selecting,
     * otherwise it sets the cursor to the new coordinates. Updates the error label if the move is illegal.
     * @param e the mouse event
     */
    @FXML
    public void handleClick(MouseEvent e) {
        int x = 8 - (int)e.getY()/50; //calculate x
        int y = (int)e.getX()/50 - 1 ; //calculate y
        if(isSelecting){
            errorLabel.setText("");
            prevX = x;
            prevY = y;
            isSelecting = false;
            setCursorImage(x,y);
        }
        else{
            try {
                game.move(prevX, prevY, x, y);
            } catch (IllegalMoveException ex) {
                errorLabel.setText(ex.getMessage());
            }
            checkPromotion();
            updateBoard();
            gameOverRoutine();
            isSelecting = true;
            appBoard.setCursor(Cursor.DEFAULT);
        }
    };

    /**
     * Initialize method automatically called by the FXMLLoader.
     */
    public void initialize() {
        updateScore(); //initial set score
        initBoardGUI();
        updateBoard();
    }
    public void updateScore()
    {
        whiteScore.setText("White wins: " + game.getWhiteScore());
        blackScore.setText("Black wins: " + game.getBlackScore());
    }

    /**
     * Initializes the 8x8 matrix of image views for the board.
     * Each image view sits atop a black/white tile.
     */
    public void initBoardGUI()
    {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                ImageView img = new ImageView();
                img.setFitHeight(50);
                img.setFitWidth(50);
                img.setPreserveRatio(false);
                img.setLayoutY(450 - i*50 - 50);
                img.setLayoutX(j*50 + 50);

                imagesBoard[i][j] = img;
                appBoard.getChildren().add(img);
            }
        }
    }

    /**
     * Updates the board with the pieces from the game.
     * null for empty tiles means that we use the image for an empty tile,
     * otherwise we use the image for the piece.
     */
    public void updateBoard(){
        Tile[][] pieces = game.getBoard().boardMatrix;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if(pieces[i][j].getPiece() != null){
                    imagesBoard[i][j].setImage(new Image(pieces[i][j].getPiece().getImagePath()));
                }
                else
                {
                     imagesBoard[i][j].setImage(new Image(EMPTY_TILE));
                }
            }
        }
    }


    /**
     * Checks if the game needs promotion, and if it does, it shows a dialog box
     * with the options for the promotion. It then calls the promotePawns method
     * with the options from the dialog box.
     */
    public void checkPromotion()
    {
        if(game.needsPromote()) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(OPTIONS[0], OPTIONS);
            dialog.setHeaderText("Choose a promotion: ");
            dialog.showAndWait();
            game.promotePawns(dialog.getSelectedItem());
        }
    }


    /**
     * Sets the cursor to the image of the piece on the tile if the tile is not empty.
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     */
    public void setCursorImage(int x, int y){
        if( Game.isInRange(x,y) && game.getBoard().boardMatrix[x][y].getPiece() != null)
        {
            appBoard.setCursor(new ImageCursor(new Image(
                    game.getBoard().boardMatrix[x][y].getPiece().getImagePath())));
        }
        else appBoard.setCursor(Cursor.DEFAULT);
    }

    /**
     * Checks if the game is over, and if it is, it shows an alert with the winner.
     * It also resets the game and updates the score.
     */
    public void gameOverRoutine()
    {
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over window");
        alert.setContentText("The game will reset automatically.");
        if(game.getBoard().isCheckMate(game.getTurn()))
        {
            alert.setHeaderText(((game.getTurn() == Colour.WHITE)?Colour.BLACK:Colour.WHITE) + " has won by check mate!");
            alert.show();
            if(game.getTurn() == Colour.WHITE) game.setBlackScore(game.getBlackScore()+1);
            else game.setWhiteScore(game.getWhiteScore()+1);
            updateScore();
            game.reset();
            updateBoard();
        }
        else if(game.getBoard().isStalemate(game.getTurn()))
        {
            alert.setHeaderText("STALEMATE: the game is drawn. " + game.getTurn() +" has no more legal moves!");
            alert.show();
            game.reset();
            updateBoard();
        }
    }
}