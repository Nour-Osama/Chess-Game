package com.example.chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(String RookNum, boolean white, Position position){
        super("Rook " + RookNum,white,position);
        this.xyMoves = new int[][]{ {1,0}, {-1,0}, {0,1}, {0,-1}, {0,0}, {0,0}, {0,0}, {0,0} };
    }
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "r";
    }
    /*
    @Override
    protected void updateAvailablePositions(){
        /*

        int max_pos = 7;
        this.availablePositions = new ArrayList<Position>();
        for(int i =0; i< max_pos; i ++){
            this.availablePositions.add(this.moveH(1));
            this.availablePositions.add(this.moveH(-1));
            this.availablePositions.add(this.moveV(1));
            this.availablePositions.add(this.moveV(-1));
        }

    }*/
}
