package com.example.chess;

import javafx.geometry.Pos;

import java.util.ArrayList;

/*todo:
* organize mutable objects all across the code
* complete isLaneEMpty in bishop (use array [1,1,-1,-1] and [1,-1,1,-1] for x,y in if conditions)
* use position static function in Piece updateLegalMOves
* add select piece function in player
*
* */
public class GameBoard {
    private Position[][] positions = SingletonPositions.positions;;
    private Piece[][] whitePieces = new Piece[9][3];
    private Piece[][] blackPieces = new Piece[9][3];
    private Player white;
    private Player black;
    private Round round;
    private boolean ended;
    public GameBoard(Player white, Player black){
        initializeGame();
        white.setPieces(whitePieces);
        white.setColor("White");
        white.setTurn(true);
        black.setPieces(blackPieces);
        black.setColor("Black");
        black.setTurn(false);
        this.white = white;
        this.black = black;
        updatePiecesLegalMoves();
    }
    public Position[][] getPositions(){return this.positions;}
    private void initializeLargePieces(int x,int y,boolean white,Piece[][] pieces){
        int pieceY = 1;
        switch (x){
            case 1: pieces[x][pieceY] = new Rook("1",white,positions[x][y]);
            break;
            case 2: pieces[x][pieceY] = new Knight("1",white,positions[x][y]);
            break;
            case 3: pieces[x][pieceY] = new Bishop("1",white,positions[x][y]);
            break;
            case 4: pieces[x][pieceY] = new Queen(white,positions[x][y]);
            break;
            case 5: pieces[x][pieceY] = new King(white,positions[x][y]);
            break;
            case 6: pieces[x][pieceY] = new Bishop("2",white,positions[x][y]);
            break;
            case 7: pieces[x][pieceY] = new Knight("2",white,positions[x][y]);
            break;
            case 8: pieces[x][pieceY] = new Rook("2",white,positions[x][y]);
            break;
        }
    }

    public boolean gameEnded() {
        return ended;
    }

    private void initializeGame(){
        /* Initial board state*/
        for(int x =1; x < 9; x++)
            for(int y =1; y < 9;y++) {
                positions[x][y] = new Position(x, y);
                switch (y){
                    case 1: initializeLargePieces(x,y,true,whitePieces);
                    break;
                    case 2: whitePieces[x][2] = new Pawn(String.valueOf(x),true,positions[x][y]);
                    break;
                    case 7: blackPieces[x][2] = new Pawn(String.valueOf(x),false,positions[x][y]);
                    break;
                    case 8: initializeLargePieces(x,y,false,blackPieces);
                    break;
                }
                /*
                if (y == 2) {
                    whitePieces[x][0] = new Pawn(String.valueOf(x),true,positions[x][y]);
                }
                if (y == 7) {
                    blackPieces[x][0] = new Pawn(String.valueOf(x),false,positions[x][y]);
            }*/
        }
        /* Initial legal move state */
    }
    private void startRound(){
        this.round = (this.white.hasTurn()) ? new Round(white,true) : new Round(black,true);
        ArrayList<ArrayList<Position>> piecesLegalMoves = round.getPlayer().getPiecesLegalMoves();
        ended = true;
        for(ArrayList<Position> legalMoves : piecesLegalMoves){
            ended &= legalMoves.size() ==0;
        }

    }
    public void NextRound(){
        if(Round.getHalfNum() == 0) startRound();
        else{
            this.white.setTurn(!this.white.hasTurn());
            this.black.setTurn(!this.black.hasTurn());
            startRound();
        }

    }
    public void updatePiecesLegalMoves(){
        ArrayList<Position> emptyLegalMoves = new ArrayList<Position>();
        for(int x =1; x < 9; x++)
            for(int y =1; y < 3;y++){
                Piece wp = whitePieces[x][y];
                Piece bp = blackPieces[x][y];
                if(wp.isAlive()) wp.updateLegalMoves(this);
                else wp.setLegalMoves(emptyLegalMoves);
                if(bp.isAlive()) bp.updateLegalMoves(this);
                else bp.setLegalMoves(emptyLegalMoves);
            }
    }
    public Boolean kingChecked(Position targetPosition, Piece originalPiece){
        try{
            Player opponent = white.getColor().equalsIgnoreCase(originalPiece.getColor()) ? black : white;
            Round simRound = new Round(white.getColor().equalsIgnoreCase(originalPiece.getColor()) ? white : black,false);
            //Round simulatedRound = (Round) round.clone();
            //Piece originalPiece = position.getPiece();
            Position originalPosition = originalPiece.getPosition();
            Piece targetPiece = Position.getBoardPosition(targetPosition).getPiece();
            simRound.setPos(originalPosition);
            simRound.setPiece(originalPosition);
            simRound.setPos(targetPosition);
            simRound.moveSequence(false);
            opponent.updatePseudoLegalMoves();
            for(int x =1; x < 9; x++)
                for(int y =1; y < 3;y++){
                    Piece p = opponent.getPieces()[x][y];
                    if(p.isAlive()){
                        for(Position move :p.getPseudoLegalMoves()){
                            Position boardPosition = Position.getBoardPosition(move);
                            if(boardPosition.getPiece() instanceof King) {
                                simRound.anteMoveSequence(targetPiece,originalPosition);
                                opponent.updatePseudoLegalMoves();
                                return true;
                            }
                        }
                    }
                }
            simRound.anteMoveSequence(targetPiece,originalPosition);
            opponent.updatePseudoLegalMoves();
        }
        catch(Exception e){
            System.out.println(e);
        }

        return false;
    }
    public Round getRound(){return round;}
}
class TestGameBoard{
    public static void main(String[] args) {
        Player p1 = new Player("Nour", "White");
        Player p2 = new Player("Bot", "Black");
        GameBoard gameBoard = new GameBoard(p1, p2);
        /*
        Position selPos = SingletonPositions.positions[1][2];
        Position targetPos = SingletonPositions.positions[1][3];
        round.setPos(selPos);
        round.setPiece(selPos);
        round.setPos(targetPos);*/
        while (true) {
            gameBoard.NextRound();
            Round round = gameBoard.getRound();
            while (!round.didMove()) {
                System.out.println("Select Position\n");
                round.inputPos();
                round.setPiece(round.getPosition());
                System.out.println("Select position to move to\n");
                round.inputPos();
               if(round.getPiece() != null)
               {
                   round.moveSequence(true);
               }
            }
            round = round.endSequence(gameBoard);
        }
    }
}
/*
        System.out.println("\n\n");
        Position[][] positions = gameBoard.getPositions();
        for(int c =1; c < 9; c++)
            for(int r =1; r < 9;r++) {
                //Position position = positions[r][c];
                //System.out.println(position);
        }
        for(int x =1; x < 9; x++)
            for(int y =1; y < 3;y++) {
                //System.out.println(p1.getPieces()[x][y]);
                //System.out.println(p2.getPieces()[x][y]);
            }
        int px = 5;
        int py = 1;
        int mx = 4;
        int my = 4;
        Player p = p1;
        p.getPieces()[px][py].move(new Position(mx,my));
        SingletonPositions.positions[mx][my].setPiece(p.getPieces()[px][py]);
        p.getPieces()[px][py].updateLegalMoves();
        System.out.println(p.getPieces()[px][py]);
        SingletonPositions.positions[1][1] = new Position(1,2);
    }

}*/
