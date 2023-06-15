package com.example.chess;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Pos;
/*todo: remove round class and add its functionality to player class*/
public class Round implements Cloneable {
    private Piece piece;
    private Position position;
    private Player player;
    private boolean moved;
    //private boolean ended;
    private static int num = 1;
    private static int halfNum = 0;
    public static int getHalfNum() {
        return halfNum;
    }

    public Round(Player player, boolean real){
        this.player = player;
        //this.player.updateLegalMoves();
        this.moved = false;
        if(real) {
            System.out.println("Current PLayer:\t" + this.player.getName() +
                    "\tColor:\t" + this.player.getColor() +
                    "\tRound Number:\t" + num);
            if (++halfNum % 2 == 0) num++;
        }
    }
    public void inputPos(){
        while(true) {
            Scanner scan = new Scanner(System.in);
            int x = scan.nextInt();
            int y = scan.nextInt();
            Position position = new Position(x,y);
            if(position.isValid())
            {
                setPos(Position.getBoardPosition(position));
                break;
            }
        }

    }
    public void setPos(Position position){
        this.position = Position.getBoardPosition(position);
    }
    public void setPiece(Position position){
            this.player.selPiece(this.position);
            this.piece = this.player.getSelPiece();
            //this.originalPosition = piece.getPosition();
    }
    public void disLegalMoves(){
        if(this.piece != null)
            System.out.println(this.piece.getLegalMoves());
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public Round(Piece piece, Position position){
        this.piece = piece;
        this.position = position;

    }
    public boolean didMove(){return this.moved;}

    public Player getPlayer() {
        return player;
    }

    public void moveSequence(boolean real){
        boolean cond = (real) ? this.piece.isMoveLegal(position):this.piece.isPseudoMoveLegal(position);
        Position old_pos = this.piece.getPosition();
        Piece target = this.position.getPiece();
        if (cond){
            this.piece.move(position);  // update selected piece position
            old_pos.setPiece(null);    // set old position piece to null
            if (this.piece.isKillingPiece())
            {
                target.kill();  // kill target piece at new position
            }
            this.position.setPiece(this.piece); // set new position piece to selected piece
            //System.out.println("Selected Piece:\t" + this.piece + "\nSelected Position:\t" + this.position + "\nTarget Piece:\t" + target);
            this.moved = true;
        }
        else{
        System.out.println("Move not legal !!");
        }
    }
    public void anteMoveSequence(Piece targetPiece, Position originalPosition){
        if(targetPiece != null){
            targetPiece.setAlive(true);
            targetPiece.move(position);
        }
        this.position.setPiece(targetPiece);
        this.piece.move(originalPosition);
        originalPosition = Position.getBoardPosition(originalPosition);
        originalPosition.setPiece(this.piece);
    }
    public Round endSequence(GameBoard gameBoard){
        gameBoard.updatePiecesLegalMoves();
        gameBoard.NextRound();
        //ended = gameBoard.gameEnded();
        return gameBoard.getRound();
        //this.player.setTurn(false);
    }
    public void selectPiece(Piece piece,Position[][] positions){
        piece.getLegalMoves();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
class TestRound{
    public static void main(String[] args){
        Piece[] pieces = new Piece[3];
        Position[] positions = new Position[3];
        positions[0] = new Position(2,2);
        positions[1] = new Position(1,3);
        positions[2] = new Position(1,3);
        for (Position position: positions){
            //System.out.println(position);
        }
        pieces[0]  = new Pawn("1",true,positions[0]);
        pieces[1]  = new Pawn("2",false,positions[1]);
        pieces[2]  = new Piece("queen",false,positions[2]);
        int i = 0;
        for(Piece piece : pieces){
            System.out.println(piece);
            new Round(piece,positions[2]);
            System.out.println(piece);
        }
    }
}
