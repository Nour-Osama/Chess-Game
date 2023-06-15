package com.example.chess;

import java.util.ArrayList;

public class Bishop extends Piece{
    private final int MAX_POS = 8;
    public Bishop(String BishopNum, boolean white, Position position){
        super("Bishop " + BishopNum,white,position);
        this.xyMoves = new int[][]{ {1,1}, {1,-1}, {-1,1}, {-1,-1}, {0,0}, {0,0}, {0,0}, {0,0} };
    }
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "b";
    }
    /*
    private void updateEmptyLanes(int i) {
        for (int j = 0; j < xyMoves.length; j++){
            int x = xyMoves[j][0] * i;
            int y = xyMoves[j][1] * i;
            Position position = Position.getBoardPosition(this.moveD(x, y));
            this.blockedLanes[j] = (position.isEmpty()) ? this.blockedLanes[j] : false;
        }
    }
    /*
    private void updateEmptyLanes(int i){

        this.emptyLanes[0] = ((Position.getBoardPosition(this.moveD(i,i)).isEmpty())) ? this.emptyLanes[0] : false ;
        this.emptyLanes[1] = ((Position.getBoardPosition(this.moveD(i,-1 * i)).isEmpty())) ? this.emptyLanes[1] : false ;
        this.emptyLanes[2] = ((Position.getBoardPosition(this.moveD(-1 * i,i)).isEmpty())) ? this.emptyLanes[2] : false ;
        this.emptyLanes[3] = ((Position.getBoardPosition(this.moveD(-1 * i,-1 * i)).isEmpty())) ? this.emptyLanes[3] : false ;
    }*/
    /*
    @Override
    protected void updateAvailablePositions(){
        blockedLanes = new boolean[] {true,true,true,true,true,true,true,true};
        this.availablePositions = new ArrayList<Position>();
        for(int i =1; i< MAX_POS; i ++) {
            for (int j = 0; j < xyMoves.length; j++) {
                int x = xyMoves[j][0] * i;
                int y = xyMoves[j][1] * i;
                if (this.blockedLanes[j]) this.availablePositions.add(this.moveD(x, y));
            }
            this.updateEmptyLanes(i);
        }
    }
    /*
    @Override
    protected void updateAvailablePositions(){
        for (boolean emptyLane : this.emptyLanes)
        {emptyLane = true;}
        int max_pos = 8 ;
        this.availablePositions = new ArrayList<Position>();
        for(int i =1; i< max_pos; i ++){
            this.updateEmptyLanes(i);
            if(this.emptyLanes[0]) this.availablePositions.add(this.moveD(i,i));
            if(this.emptyLanes[1]) this.availablePositions.add(this.moveD(i,-1 * i));
            if(this.emptyLanes[2]) this.availablePositions.add(this.moveD(-1 * i,i));
            if(this.emptyLanes[3]) this.availablePositions.add(this.moveD(-1 * i, -1 * i));
        }
    }*/
}
