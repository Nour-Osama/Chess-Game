package com.example.chess;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(String KnightNum, boolean white, Position position){
        super("Knight " + KnightNum,white,position);
        this.xyMoves = new int[][]{ {2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,2}, {1,-2}, {-1,2}, {-1,-2} };
        this.maxMove = 1;
    }
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "k";
    }
    /*
    @Override
    protected void updateAvailablePositions(){
        this.availablePositions = new ArrayList<Position>();
        this.availablePositions.add(moveD(2,1));
        this.availablePositions.add(moveD(2,-1));
        this.availablePositions.add(moveD(-2,1));
        this.availablePositions.add(moveD(-2,-1));
        this.availablePositions.add(moveD(1,2));
        this.availablePositions.add(moveD(1,-2));
        this.availablePositions.add(moveD(-1,2));
        this.availablePositions.add(moveD(-1,-2));
    }*/
}
