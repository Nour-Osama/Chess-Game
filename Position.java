package com.example.chess;

public class Position {
    private int x;
    private int y;
    //private boolean empty;
    private Piece piece;
    private String color;
    private static final int X_MAX = 8;
    private static final int Y_MAX = 8;
    //public static ArrayList<Position> positions = new ArrayList<>();
    public Position(int x, int y,Piece piece, String color){
        this (x,y,piece);
        this.color = color;
    }
    public Position(int x, int y,Piece piece){
        this (x,y);
        this.piece = piece;
    }
    /*
    public Position(int x, int y,boolean empty){
        this (x,y);
        this.empty = empty;
        positions.add(this);
    }*/
    public Position(int x, int y){
            this.x = x;
            this.y = y;
            //this.empty = true;
            //this.color = "None";
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public String getColor(){return color;}
    /*
    public boolean isEmpty(){
        for (Position position :positions){
            if (this.equals(position))
                return false;}
        return true;
    }*/
    public boolean isEmpty(){return this.piece == null;}
    public boolean isValid(){
        return this.x > 0 && this.x <= X_MAX && this.y > 0 && this.y <= Y_MAX;}
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Position)
        return this.x == ((Position)obj).x && this.y == ((Position)obj).y;
        return false;
    }
    @Override
    public String toString(){
        if (!isEmpty())
            return "position:\t(" + this.x + ", " + this.y + ")\tPiece:\t" + this.piece.getColor() + " " + this.piece.getName() + "\tColor:\t" + this.color;
        else
            return  "position:\t(" + this.x + ", " + this.y + ")\tPiece:\tnull\tColor:\t" + this.color;
    }
    @Override
    public Position clone(){
        return new Position(this.getX(),this.getY(),this.getPiece(),this.getColor());
    }
    public boolean within(Position position, int x, int y,boolean positiveOnly){
        boolean positiveCond = this.x == (position.x +x) && this.y == (position.y +y);
        boolean negativeCond = this.x == (position.x -x) && this.y == (position.y -y);
        if (positiveOnly)
            return positiveCond;
        else
            return negativeCond;
    }

    public boolean within(Position position, int x, int y){
        return within(position,x,y,false);
    }
    /*
    public void setColor(String color){this.color = color;}
    public String getColor(){return this.color;}
    public String getOppositeColor(){
        if (this.color == "white") return "black";
        else if (this.color == "black") return  "white";
        return "None";
    }*/
    public void setPiece(Piece piece){this.piece = piece;}
    public Piece getPiece(){return this.piece;}
    public static Position getBoardPosition(Position position){
        /*
        Position[][] positions = SingletonPositions.positions;
        int x = position.getX();
        int y = position.getY();
        if (position.isValid()) return positions[x][y];
        else return new Position(-1,-1);*/
        return (position.isValid()) ?
                SingletonPositions.positions[position.getX()][position.getY()] : new Position(-1,-1);
    }

}
 class TestPosition{
    public static void main(String[] args){
        Position[] positions = new Position[2];
        positions[0] = new Position(1,2);
        //positions[1] = new Position(5,6,new Piece("test","white",new Position(5,6)));
        //positions[2] = new Position(8,7,true,"black");
        for(Position position: positions)
        {
            System.out.println(position );
            System.out.println(position.isEmpty());
            System.out.println(position.within(new Position(1,3),0,1));
        }
        Pawn pawn = new Pawn("1",true,positions[0]);
        positions[1].setPiece(pawn);
        System.out.println(positions[0]);
        System.out.println(positions[0].isEmpty());
        //System.out.println(positions[0].getPiece());
    }
}
