package com.example.chess;

import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(boolean white, Position position){
        super("Queen" ,white,position);
        this.xyMoves = new int[][]{ {1,1}, {1,-1}, {-1,1}, {-1,-1}, {1,0}, {-1,0}, {0,1}, {0,-1} };
    }
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "q";
    }
    /*
    @Override
    protected void updateAvailablePositions(){


        int max_pos = 7 ;
        this.availablePositions = new ArrayList<Position>();
        for(int i =0; i< max_pos; i ++){
            /* BiShop movement
            this.availablePositions.add(this.moveD(i,i));
            this.availablePositions.add(this.moveD(i,-1 * i));
            this.availablePositions.add(this.moveD(-1 * i,i));
            this.availablePositions.add(this.moveD(-1 * i, -1 * i));
            /* Rook movement *
            this.availablePositions.add(this.moveH(1));
            this.availablePositions.add(this.moveH(-1));
            this.availablePositions.add(this.moveV(1));
            this.availablePositions.add(this.moveV(1));
        }
    }*/
}
