package com.example.chess;

public class SingletonPositions {
    public static final Position[][] positions = new Position[9][9];
    //private boolean isInstance = false;
    /*
    private SingletonPositions(Position[][] positions){
        this.positions = (this.isInstance) ? this.positions: positions;
        this.isInstance = true;
    }*/
    /*
     public static Position getBoardPosition(Position position){
         return positions[x][y].clone();

     }
    // public static void setPositions(Position[][] positions){new SingletonPositions(positions);}
    public static void setPosition(Position position){
        if (position.isValid() && this.getBoardPosition() != null){
            final Position finPosition = position;
            Position.boardPositions[this.getX()][this.getX()] = position;
        }
        else System.out.println("Failure to set board position:\t" + this );
    }
    /*
    public Position getBoardPosition(){
        int x = this.getX();
        int y = this.getY();
        if (this.isValid() && Position.boardPositions[x][y] != null) return Position.boardPositions[x][y];
        else return new Position(-1,-1);
    }*/
}
