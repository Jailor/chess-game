# Chess Game Project ♕

## About :thought_balloon:
For my OOP project, I decided to create a chess game because it involves logic, math, and graphical user interface elements. Chess is an easy game to understand but hard to master, making it a great project to work on.

## Features :white_check_mark:
The game works just like a regular game of chess, with opponents taking turns to move a single piece. If a move is illegal for any reason, it is not counted, and the player must choose again. Some of the features implemented in the game include:
- Out of bounds checking.
- Checking if some pieces are in the way of an otherwise legal move (Rook, Queen, Bishop).
- Error codes for illegal moves displayed on screen (Example: “Move will cause check!” or “"Out of bounds!").
- The cursor changes to the selected piece when making a move.
- The game automatically does score keeping and displays it.
- The game automatically resets when an end condition is reached.
- Checkmate detector: The game knows when a player no longer has any legal moves while in check, triggering a checkmate, updating the score, and resetting the game.
- Stalemate detector: The game knows when a player no longer has any legal moves while NOT in check, triggering a stalemate (a draw), leaving scores unchanged, and resetting the game.
- Special rule: Pawn can move 2 tiles if it hasn’t moved yet.
- Special rule: Promotion/Underpromotion: A pawn that reaches the end of the board can be promoted. A dialogue appears, allowing the user to select what piece they want to promote the pawn to.
- Special rule: Castling/Rocadă: If the king and the rook have never moved, it’s possible to “castle,” making the rook jump over the king, and the king moving closer to the rook’s initial position in one move. This move is used to protect the king. Restrictions apply: cannot castle when in check, cannot castle into check, cannot castle if the pieces have moved, and cannot castle if there are pieces between the rook and king. The game checks for all these restrictions before completing the castling move. Otherwise, an error is shown.

## GUI and Implementation :computer:


## Further Implementation :chart_with_upwards_trend:
There are some niche chess features that can be implemented, such as en passant, the 50 moves rule, and threefold repetition, which require a more complex game-keeping logic like a full history of all the moves that have happened in the game.
