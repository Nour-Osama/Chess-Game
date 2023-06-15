package com.example.chess;

import java.util.ArrayList;

public class Player implements Cloneable{
    private String name;
    private String color;

    public void setColor(String color) {
        this.color = color;
    }

    private Piece selPiece;
    private Piece[][] pieces = new Piece[9][3];
    boolean turn;
    public Player(String name){
        this.name = name;
    }
    public Player(String name, String color) {
        this.name = name;
        this.color=color;
        if(color.equalsIgnoreCase("white")) this.turn = true;
        else this.turn = false;

    }
    public String getColor(){return this.color;}
    public Piece getSelPiece() {
        return selPiece;
    }

    public String getName() {
        return name;
    }

    public boolean hasTurn(){return this.turn;}
    public void setTurn(boolean turn){this.turn = turn;}
    public void setPieces(Piece[][] pieces){this.pieces = pieces;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Piece[][] getPieces(){return this.pieces;}
    public void selPiece(Position position){
        this.selPiece = null;
        for(int x =1; x < 9; x++)
            for(int y =1; y < 3;y++){
                Piece piece = this.pieces[x][y];
                this.selPiece = (piece.getPosition().equals(position) && piece.isAlive()) ? piece : this.selPiece;
            }
        if (this.selPiece == null) System.out.println("No piece Selected");
    }
    public void updatePseudoLegalMoves(){
        for(int x =1; x < 9; x++)
            for(int y =1; y < 3;y++){
                Piece piece = this.pieces[x][y];
                piece.updatePseudoLegalMoves();
            }
    }
    public ArrayList<ArrayList<Position>> getPiecesLegalMoves(){
        ArrayList<ArrayList<Position>> piecesLegalMoves = new ArrayList<ArrayList<Position>>();
        for(int x =1; x < 9; x++)
            for(int y =1; y < 3;y++) {
                Piece piece = this.pieces[x][y];
                piecesLegalMoves.add(piece.getLegalMoves());
            }
        return piecesLegalMoves;
    }
    public Boolean checking(){
        Boolean check = false;
        for(int x =1; x < 9; x++)
        {
            if (check) break;
            for(int y =1; y < 3;y++) {
                if (check) break;
                Piece piece = this.pieces[x][y];
                for (Position move : piece.getLegalMoves()) {
                    Position position = Position.getBoardPosition(move);
                    if(position.getPiece() instanceof King) check = true;
                }
            }
            }
        return check;
    }
    public boolean isPieceSel(){return this.selPiece != null;}
}
