package com.example.chess;

import javafx.geometry.Pos;

import java.util.ArrayList;

public interface Movable {
    boolean isMoveLegal(Position position);
    ArrayList<Position> getLegalMoves();
    void updateLegalMoves(GameBoard gameBoard);
    void move(Position position);
    //Position moveH(int x);
    //Position moveV(int y);
    Position moveD(int x, int y);
}
