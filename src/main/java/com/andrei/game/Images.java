package com.andrei.game;

import java.nio.file.Paths;

/**
 * This class contains the paths to the images used in the game. Cannot be instantiated.
 */
public class Images {
    private Images() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final String WHITE_PAWN = "file:/" + Paths.get("piese","WHITE_PAWN.png").toAbsolutePath();
    public static final String BLACK_PAWN = "file:/" + Paths.get("piese","BLACK_PAWN.png").toAbsolutePath();
    public static final String WHITE_KNIGHT = "file:/" + Paths.get("piese","WHITE_KNIGHT.png").toAbsolutePath();
    public static final String BLACK_KNIGHT = "file:/" + Paths.get("piese","BLACK_KNIGHT.png").toAbsolutePath();
    public static final String WHITE_ROOK = "file:/" + Paths.get("piese","WHITE_ROOK.png").toAbsolutePath();
    public static final String BLACK_ROOK = "file:/" + Paths.get("piese","BLACK_ROOK.png").toAbsolutePath();
    public static final String WHITE_BISHOP = "file:/" + Paths.get("piese","WHITE_BISHOP.png").toAbsolutePath();
    public static final String BLACK_BISHOP = "file:/" + Paths.get("piese","BLACK_BISHOP.png").toAbsolutePath();
    public static final String WHITE_QUEEN = "file:/" + Paths.get("piese","WHITE_QUEEN.png").toAbsolutePath();
    public static final String BLACK_QUEEN = "file:/" + Paths.get("piese","BLACK_QUEEN.png").toAbsolutePath();
    public static final String WHITE_KING = "file:/" + Paths.get("piese","WHITE_KING.png").toAbsolutePath();
    public static final String BLACK_KING = "file:/" + Paths.get("piese","BLACK_KING.png").toAbsolutePath();
    public static final String EMPTY_TILE = "file:/" + Paths.get("piese","rmp.png").toAbsolutePath();
    public static final String ICON = "file:/" + Paths.get("ICON.png").toAbsolutePath();
}
