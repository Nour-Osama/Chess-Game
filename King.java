package com.example.chess;

import java.util.ArrayList;

public class King extends Piece{
    public King(boolean white, Position position){
        super("King",white,position);
        this.xyMoves = new int[][]{ {1,1}, {1,-1}, {-1,1}, {-1,-1}, {1,0}, {-1,0}, {0,1}, {0,-1} };
        this.maxMove = 1;
    }
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "ki";
    }
    /*
    @Override
    protected void updateAvailablePositions(){
        this.availablePositions = new ArrayList<Position>();
        this.availablePositions.add(moveH(1));
        this.availablePositions.add(moveH(-1));
        this.availablePositions.add(moveV(1));
        this.availablePositions.add(moveV(-1));
        this.availablePositions.add(moveD(1,1));
        this.availablePositions.add(moveD(1,-1));
        this.availablePositions.add(moveD(-1,1));
        this.availablePositions.add(moveD(-1,-1));
    }
    */

}
