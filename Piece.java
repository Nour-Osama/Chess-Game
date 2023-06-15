package com.example.chess;

import java.util.ArrayList;

public class Piece implements Cloneable, Movable, Killable{
    private final static int MAX_MOVE = 7;
    private String name;
    private Position position;
    private boolean alive;
    private boolean white;
    private String color;
    protected ArrayList<Position> legalMoves = new ArrayList<Position>();
    protected ArrayList<Position> pseudoLegalMoves = new ArrayList<Position>();
    protected ArrayList<Position> availablePositions = new ArrayList<Position>() ;
    protected int maxMove;

    public ArrayList<Position> getPseudoLegalMoves() {
        return pseudoLegalMoves;
    }

    protected int[][] xyMoves = { {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0} };
    private boolean[] allowedLanes = {true,true,true,true,true,true,true,true};
    public Piece(String name,boolean white,Position position){
        this.alive = true;
        this.name = name;
        this.white = white;
        this.color = (this.white) ? "White" : "Black";
        position.setPiece(this);
        this.position = position;
        this.maxMove = MAX_MOVE;
    }
    public String getName(){return this.name;}
    public String getColor(){return this.color;}
    public boolean isWhite(){return this.white;}
    public Position getPosition(){return position;}
    public boolean isAlive(){return this.alive;}
    //public void setPosition(Position position){this.position = position;}
    public void setAlive(boolean alive){this.alive = alive;}
    @Override
    public String toString(){
        if (this.position != null)
            return "Name:\t" + this.color + " " + this.name + "\tAlive:\t" + this.alive + "\tPosition:\t(" + this.position.getX() + ", " + this.position.getY() + ")\n" + this.legalMoves;
        else
            return "Position:\tnull\nName:\t" + this.name + "\tColor:\t" + this.color +"\tAlive:\t" + this.alive;
    }
   /* public Position moveH(int x){
        return new Position(this.position.getX() + x,this.position.getY());
    }*/
    /*public Position moveV(int y){
        return new Position(this.position.getX(),(this.position.getY() + y));
    }*/
    public Position moveD(int x,int y){return new Position(this.position.getX()+ x,this.position.getY() + y);}
    /*
    public ArrayList<Position> getLegalMoves(Position[][] positions) {
        ArrayList<Position> legalPositions = new ArrayList<>();
        for(int c =1; c < 9; c++)
            for(int r =1; r < 9;r++) {
                Position position = positions[r-1][c-1];
                if (isMoveLegal(position)) legalPositions.add(position);
            }
        return legalPositions;
    }*/
    public ArrayList<Position> getLegalMoves(){return legalMoves;}
    //protected Position[] getAvailablePositions(){return this.availablePositions; }
    private void updateAllowedLanes(int i) {
        for (int j = 0; j < xyMoves.length; j++){
            int x = xyMoves[j][0] * i;
            int y = xyMoves[j][1] * i;
            Position position = Position.getBoardPosition(this.moveD(x, y));
            this.allowedLanes[j] = (position.isEmpty()) ? this.allowedLanes[j] : false;
        }
    }
    protected void updateAvailablePositions(){
        this.allowedLanes = new boolean[] {true,true,true,true,true,true,true,true};
        this.availablePositions = new ArrayList<Position>();
        for(int i = 1; i<= this.maxMove; i ++) {
            for (int j = 0; j < this.xyMoves.length; j++) {
                int x = this.xyMoves[j][0] * i;
                int y = this.xyMoves[j][1] * i;
                if (this.allowedLanes[j]) this.availablePositions.add(this.moveD(x, y));
            }
            this.updateAllowedLanes(i);
        }
    }
    //protected void updateAvailablePositions(Position[][] positions){}

        public void updatePseudoLegalMoves(){
        pseudoLegalMoves = new ArrayList<Position>();
        this.updateAvailablePositions();
        for (int i =0; i < this.availablePositions.size(); i++) {
            if (this.availablePositions.get(i) != null) {
                Position position = this.availablePositions.get(i);
                Position boardPosition = Position.getBoardPosition(position);
                if (boardPosition.isValid()) {
                    if (boardPosition.isEmpty() || this.isWhite() != boardPosition.getPiece().isWhite()) {
                        this.pseudoLegalMoves.add(boardPosition);
                    }
            }
        }
    }
    }

    public void setLegalMoves(ArrayList<Position> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public void updateLegalMoves(GameBoard gameBoard){
       // System.out.println(this);
        ArrayList<Position> newLegalMoves = new ArrayList<Position>();
        updatePseudoLegalMoves();
        for(int i =0; i < this.pseudoLegalMoves.size(); i++){
            if(!gameBoard.kingChecked(pseudoLegalMoves.get(i),this))
               // System.out.println(this.toString() + pseudoLegalMoves.get(i));
                newLegalMoves.add(pseudoLegalMoves.get(i));
        }
        this.legalMoves = newLegalMoves;
        //System.out.println(legalMoves);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    /*
    private void chooseUpdateAvailablePositions(Position[][] positions){
        if (this instanceof Pawn || this instanceof Queen || this instanceof Knight)
            updateAvailablePositions();
        else updateAvailablePositions(positions);
    }/*
    public void updateLegalMoves(){
        legalMoves = new ArrayList<Position>();
        this.updateAvailablePositions();
        for (int i =0; i < this.availablePositions.size(); i++) {
            if (this.availablePositions.get(i) != null) {
                Position position = this.availablePositions.get(i);
                Position boardPosition = Position.getBoardPosition(position);
                if (boardPosition.isValid()) {
                    if (boardPosition.isEmpty()) {     // check if board position is empty
                        if (this instanceof Pawn) {
                            if (i == 0 || i == 1)
                                this.legalMoves.add(position);  // if piece is pawn and first available position is chosen
                        } else this.legalMoves.add(position);         // if piece isn't pawn
                    } else if (this.isWhite() != boardPosition.getPiece().isWhite()) {
                        this.legalMoves.add(boardPosition);
                    }      // if board position not empty and board position piece color different
                }
            }
        }
    }*/
    public boolean isPseudoMoveLegal(Position position) {
        for (Position pseudoLegalMove: this.pseudoLegalMoves)
            if (pseudoLegalMove.equals(position)) return true;
        return false;
    }

    public boolean isMoveLegal(Position position) {
        for (Position legalMove: this.legalMoves)
            if (legalMove.equals(position)) return true;
        return false;
    }
    public void move(Position position)  {this.position = position;}
    public boolean isKillingPiece(){return !this.position.isEmpty() && this.isWhite() != this.position.getPiece().isWhite();}
    public void kill() {this.alive = false;}
}
class TestPiece{
    public static void main(String[] args){
        Piece[] pieces = new Piece[3];
        Position[] positions = new Position[3];
        positions[0] = new Position(1,2);
        positions[1] = new Position(5,6);
        positions[2] = new Position(4,6);
        for (Position position: positions){
            System.out.println(position);
        }
        pieces[0]  = new Piece("pawn",true,positions[0]);
        pieces[1]  = new Piece("bishop",true,positions[1]);
        pieces[2]  = new Piece("queen",false,positions[2]);
        int i = 0;
        for(Piece piece : pieces){
            //System.out.println(piece.getPosition() +"\n" +  piece.getColor() + "\nposition_empty: " + piece.getPosition().isEmpty() );
            //piece.move(new Position(i++,i+1));
            //System.out.println(piece.getPosition());
            //System.out.println(piece);
            //System.out.println(piece.moveH(8));
            //System.out.println(piece.moveV(3));
            //System.out.println(piece.moveD(3,3));


        }
    }
}

