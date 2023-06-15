package com.example.chess;

import javafx.geometry.Pos;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(String pawnNum, boolean white, Position position){
        super("Pawn " + pawnNum,white,position);
    }
    /*
    public boolean isMoveLegal(Position position) {
    if (position.isEmpty() && this.moveV(1).equals(position))
        return true;
    else if ( (!position.isEmpty()) && this.getColor() != position.getPiece().getColor()  &&
            (this.moveD(1,1).equals(position) || this.moveD(-1,1).equals(position)))
        return true;
    else
        return false;
    }*/
    @Override
    public String getName(){
        return (isWhite() ? "w":"b") + "p";
    }
    @Override
    protected void updateAvailablePositions(){
        /*
        */
        int colorFactor = (this.isWhite()) ? 1 : -1;
        Position[] positions = new Position[4];
        positions[0] = this.moveD(0,1 * colorFactor);
        positions[1] = (this.getPosition().getY() == 7 || this.getPosition().getY() == 2) ? this.moveD(0,2 * colorFactor): null;
        positions[1] = (Position.getBoardPosition(positions[0]).isEmpty()) ? positions[1] : null;
        positions[2] = this.moveD(1, 1 * colorFactor);
        positions[3] = this.moveD(-1, 1 * colorFactor);
        this.availablePositions = new ArrayList<Position>();
        for(Position position: positions) this.availablePositions.add(position);

    }
    @Override
    public void updatePseudoLegalMoves(){
        pseudoLegalMoves = new ArrayList<Position>();
        this.updateAvailablePositions();
        for (int i =0; i < this.availablePositions.size(); i++) {
            if (this.availablePositions.get(i) != null) {
                Position position = this.availablePositions.get(i);
                Position boardPosition = Position.getBoardPosition(position);
                if (boardPosition.isValid()) {
                        if ((i == 0 || i == 1) && boardPosition.isEmpty())
                            this.pseudoLegalMoves.add(position);  // if piece is pawn and first available position is chosen and empty
                        else if ( (i==2 || i ==3) && !boardPosition.isEmpty() && this.isWhite() != boardPosition.getPiece().isWhite())
                            //System.out.println("lol");
                            this.pseudoLegalMoves.add(position);
                    }
                }
            }
        }
    /*
    public void updateLegalMoves(Position[][] positions) {
        int colorFactor = (this.isWhite()) ? 1 : -1;
        Position position1 = this.moveV(1 * colorFactor);
        Position position2 = this.moveD(1, 1 * colorFactor);
        Position position3 = this.moveD(-1, 1 * colorFactor);
        Position[] availablePositions = {position1, position2, position3};
        for (int i =0; i < availablePositions.length; i++)
        {
            Position position = availablePositions[i];
            int x = position.getX();
            int y = position.getY();
            if(Position.isValid(x,y)){              // check if position is valid
                if (positions[x][y].isEmpty())      // check if board position is empty
                    if (i == 0) this.legalMoves.add(position) ;         // if board position is empty and first availabel position is choosen
                else if (this.isWhite() != positions[x][y].getPiece().isWhite()) this.legalMoves.add(position);         // if board position not empty and board position piece color different
            }
        }
    }*/

}
class TestPawn{
    public static void main(String[] args){
        Player p1 = new Player("Nour","White");
        Player p2 = new Player("yo","Black");
        GameBoard gameBoard = new GameBoard(p1,p2);
        Position[][] positions = gameBoard.getPositions();
        Position testPos = positions[1][3];
        Piece testPiece = new Piece("test",false, testPos);
        Position testPos2 = positions[3][3];
        Piece testPiece2 = new Piece("test",false, testPos2);
        Piece[][] whitePieces = p1.getPieces();
        Piece[][] blackPieces = p2.getPieces();
        for(int x =1; x < 9; x++) {                System.out.println(whitePieces[x][0]);
                System.out.println(blackPieces[x][0]);
            }
        /*
            for(int x =1; x < 9; x++) {
                Piece piece = p1.getPieces()[x][0];
                System.out.println("p1 pawn " + (x) + ":\t" + piece);
                piece.updateLegalMoves(positions);
                System.out.println(piece.getLegalMoves());
                piece = p2.getPieces()[x][0];
                System.out.println("p2 pawn " + (x) + ":\t" + piece);
                piece.updateLegalMoves(positions);
                System.out.println(piece.getLegalMoves());
                if(x==2) break;
            }*/

        //for(Position position: pawn1.getLegalMoves()) {
         //   System.out.println(position);
         //}
    }
        /*
        for(int c =1; c < 9; c++)
            for(int r =1; r < 9;r++) {
                Position position = positions[r-1][c-1];
                System.out.println(position);
            }*/
}
