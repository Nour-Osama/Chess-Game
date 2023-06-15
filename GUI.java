package com.example.chess;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application {
    private GameBoard gameBoard;
    private Round round;
    private GridPane gridPane;
    private Position originalPos;
    private ArrayList<StackPane> currentLegalDisplayed = new ArrayList<StackPane>();
    private ArrayList<Rectangle> currentRectanglesDisplayed = new ArrayList<Rectangle>();
    private StackPane currentBorderDisplayed;
    private boolean borderDisplayed;
    private int click = 1;
    private void addImageToStackPane(String path, StackPane sp) throws IOException{
        File file = new File(System.getProperty("user.dir") + path) ;
        Image imageForFile = new Image(file.toURI().toURL().toExternalForm());
        ImageView iv = new ImageView(imageForFile);
        sp.getChildren().add(iv);
    }
    private void displayBorder(Position position){
        Rectangle rectangle = new Rectangle(80,80);
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        StackPane sp = (StackPane)getNodeByRowColumnIndex(position.getY(),position.getX());
        sp.getChildren().add(rectangle);
        currentBorderDisplayed = sp;
        borderDisplayed = true;
    }
    private void removeBorder(){
        currentBorderDisplayed.getChildren().remove(2);
        borderDisplayed = false;
    }
    private boolean isBorderDisplayed(){
        return borderDisplayed;
    }
    private void displayLegalMoves(ArrayList<Position> legalMoves){
        for(Position position:legalMoves){
            Rectangle rectangle = new Rectangle(80,80);
            rectangle.setFill(Color.GREEN);
            rectangle.setOpacity(0.5);
            StackPane sp = (StackPane)getNodeByRowColumnIndex(position.getY(),position.getX());
            sp.getChildren().add(rectangle);
            currentLegalDisplayed.add(sp);
            currentRectanglesDisplayed.add(rectangle);
        }
    }
    private void hideLegalMoves(){
        for(int i = 0;i<currentLegalDisplayed.size();i++){
            currentLegalDisplayed.get(i).getChildren().remove(currentRectanglesDisplayed.get(i));
        }
    }
    public Node getNodeByRowColumnIndex (int row, int column) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        initializeGame();
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        //pane.setPadding(new Insets(10, 10, 10, 10));
        //pane.setHgap(0);
        //pane.setVgap(0);
        Color black = Color.DARKSLATEGREY;
        Color white = Color.WHEAT;
        Color alternating = Color.DARKSLATEGREY;
        for(int x =1; x < 9; x++){
            for(int y =1; y < 9;y++) {
                StackPane sp = new StackPane();
                Rectangle rectangle = new Rectangle(80,80);
                rectangle.setFill(alternating);
                sp.getChildren().add(rectangle);
                if(y==1){
                    switch (x){
                        case 1:
                        case 8:{
                            addImageToStackPane("\\img\\wr.png",sp);
                            break;
                        }
                        case 2:
                        case 7:{
                            addImageToStackPane("\\img\\wk.png",sp);
                            break;
                        }
                        case 3:
                        case 6:{
                            addImageToStackPane("\\img\\wb.png",sp);
                            break;
                        }
                        case 4:{
                            addImageToStackPane("\\img\\wq.png",sp);
                            break;
                        }
                        case 5:{
                            addImageToStackPane("\\img\\wki.png",sp);
                            break;
                        }
                    }
                }
                else if(y==2){
                        addImageToStackPane("\\img\\wp.png",sp);
                }
                else  if(y==7){
                    addImageToStackPane("\\img\\bp.png",sp);
                }
                else if(y==8){
                    switch (x){
                        case 1:
                        case 8:{
                            addImageToStackPane("\\img\\br.png",sp);
                            break;
                        }
                        case 2:
                        case 7:{
                            addImageToStackPane("\\img\\bk.png",sp);
                            break;
                        }
                        case 3:
                        case 6:{
                            addImageToStackPane("\\img\\bb.png",sp);
                            break;
                        }
                        case 4:{
                            addImageToStackPane("\\img\\bq.png",sp);
                            break;
                        }
                        case 5:{
                            addImageToStackPane("\\img\\bki.png",sp);
                            break;
                        }
                    }
                }
                 final int xFin = x;
                 final int yFin = y;
                sp.setOnMouseClicked(e->OnMouseClick(xFin,yFin));
                gridPane.add(sp,x,y);
                alternating = (alternating == black) ? white: black;
            }
        alternating = (alternating == black) ? white: black;
    }
        Scene scene = new Scene(gridPane,1280,720);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }
    private void removePiece(int x, int y){
        StackPane sp = (StackPane)getNodeByRowColumnIndex(y,x);
        sp.getChildren().remove(1);
    }
    private void addPiece(int x, int y,Piece piece) {
        try{
        StackPane sp = (StackPane)getNodeByRowColumnIndex(y,x);
        addImageToStackPane("\\img\\" + piece.getName() + ".png",sp);}
        catch(IOException e){}
    }
    private void movePiece(Piece alivePiece,Piece deadPiece){
        if(deadPiece != null) removePiece(deadPiece.getPosition().getX(),deadPiece.getPosition().getY());
        removePiece(originalPos.getX(),originalPos.getY());
        addPiece(alivePiece.getPosition().getX(),alivePiece.getPosition().getY(),alivePiece);
    }
    private void attemptToMove(Position pos){
        System.out.println("clicked for second time");
        hideLegalMoves();
        round.setPos(pos);
        Piece piece = round.getPosition().getPiece();
        if( piece != null && piece.getColor().equalsIgnoreCase(round.getPlayer().getColor())){
            System.out.println("RETURN to  selected");
            selectPiece(pos);
        }
        else {
            Piece target = pos.getPiece();
            round.moveSequence(true);
            click = 1;
            if (round.didMove()) {
                movePiece(round.getPiece(),target);
                round = round.endSequence(gameBoard);
                if (gameBoard.gameEnded()) endGame();
            }
        }
    }
    private void selectPiece(Position pos){
        round.setPos(pos);
        originalPos = round.getPosition();
        round.setPiece(round.getPosition());
        if(isBorderDisplayed()) removeBorder();
        if(round.getPiece() != null){
            //System.out.println(round.getPiece().getLegalMoves());
            ArrayList<Position> legalMoves = round.getPiece().getLegalMoves();
            if(legalMoves.size() > 0){
                displayLegalMoves(legalMoves);
                click = 2;
            }
            else{
                displayBorder(pos);
                System.out.println("NO MOVE!");
            }
            System.out.println(round.getPosition().getPiece());
        }
    }
    private void endGame(){
        System.out.println("\nGame ENDED\n");
    }
    private void OnMouseClick(int x,int y){
        Position pos = Position.getBoardPosition(new Position(x,y));
        if(click == 1){
            selectPiece(pos);
        }
        else if (click == 2){
            attemptToMove(pos);
        }
        System.out.println(x +", " + y);
    }
    public static void main(String[] args) {
        launch();
    }
    private void initializeGame(){
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        this.gameBoard = new GameBoard(p1, p2);
        gameBoard.NextRound();
        round = gameBoard.getRound();
    }
    private void consoleGameSequence(){
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