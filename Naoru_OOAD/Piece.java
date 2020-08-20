import java.util.*;

abstract class Piece {
    private boolean dead = false;
    private boolean blue = false;
    private String name;
    public Piece(boolean blue,String name) {
        this.setBlue(blue);
        this.setName(name);
    }

    public boolean isBlue() {
        return this.blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public abstract boolean validMove(ChessBoard board, Square from, Square to);

    public abstract String getIcon(int currentTurn); // TODO: need to change to Icon
}


class Sun extends Piece {
    public Sun(boolean blue) {
       super(blue,"Sun");

    }

    public boolean validMove(ChessBoard board, Square from, Square to) {
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 

        int x = Math.abs(from.getX() - to.getX());
        int y = Math.abs(from.getY() - to.getY());
        // to check if the move is 1unit vertically or horizontally
        if (x + y == 1) {
            return true;
        }else if (x == y && x == 1) {
            return true;
        }

        return false;
    }

    public String getIcon(int currentTurn){
        if(isBlue()){
            return "BlueSun.png";
        }
        return "RedSun.png";
    }
}


class Chevron extends Piece {
    public Chevron(boolean blue) {
          super(blue,"Chevron");
    }

    public boolean validMove(ChessBoard board, Square from, Square to){
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 

        int x = Math.abs(from.getX() - to.getX()); 
        int y = Math.abs(from.getY() - to.getY()); 
        if(( x == 2 && y == 1 ) || ( x == 1 && y == 2 )){
            return true; 
        }

        return false;
    }
    
    public String getIcon(int currentTurn){
        if(isBlue()){
            if(currentTurn == 0){
                return "BlueChevron-reverse.png";
            } else {
                return "BlueChevron.png";
            }
        } else {
            if(currentTurn == 0){
                return "RedChevron.png";
            } else {
                return "RedChevron-reverse.png";
            }
        }
        
    }
}


class Triangle extends Piece {
    public Triangle(boolean blue) {
        super(blue,"Triangle");
    }

    public boolean validMove(ChessBoard board, Square from, Square to) {
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 

        int x = Math.abs(from.getX() - to.getX());
        int y = Math.abs(from.getY() - to.getY());
        if (x == y && x >= 1) {
            return true;
        }

        return false;
    }

    public String getIcon(int currentTurn){
        if(isBlue()){
            if(currentTurn == 0){
                return "BlueTriangle-reverse.png";
            } else {
                return "BlueTriangle.png";
            }
        } else {
            if(currentTurn == 0){
                return "RedTriangle.png";
            } else {
                return "RedTriangle-reverse.png";
            }
        }
        
    }
}


class Plus extends Piece {
    public Plus(boolean blue) {
        super(blue,"Plus");
    }

    public boolean validMove(ChessBoard board, Square from, Square to) {
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 

        int x = Math.abs(from.getX() - to.getX());
        int y = Math.abs(from.getY() - to.getY());
        if ((x >= 1 && y == 0) || (x == 0 && y >= 1)) {
            return true;
        }

        return false;
    }

    public String getIcon(int currentTurn){
        if(isBlue()){
            return "BluePlus.png";
        }
        return "RedPlus.png";
    }
}


class Arrow extends Piece {
    private boolean reachedEnd = false;

    public Arrow(boolean blue){
        super(blue,"Arrow");
    }

    public void setReachedEnd(boolean value) {
        this.reachedEnd = value;
    }

    public boolean hasReachedEnd() {
        return this.reachedEnd;
    }

    public boolean validMove(ChessBoard board, Square from, Square to){
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 
        int x = from.getX() - to.getX(); 
        int y = from.getY() - to.getY();

        if(reachedEnd){
            if(y < 0){ // Going backwards
                x = Math.abs(x);
                y = Math.abs(y);
                if((y == 1 || y == 2) && x == 0){
                    return true; 
                }
            }
            return false;
        } else {
            if(y > 0){ // Going forward
                if((y == 1 || y == 2) && x == 0){
                    return true; 
                }
            } else if(y < 0){ // Going backwards
                x = Math.abs(x);
                y = Math.abs(y);
                
                if((y == 1 || y == 2) && x == 0){
                    return true; 
                }
            }
            return false;
        }
    }

    public String getIcon(int currentTurn){
        if(isBlue()){
            if(currentTurn == 0){
                if(reachedEnd){
                    return "BlueArrow.png";
                }
                return "BlueArrow-reverse.png";
            } else {
                if(reachedEnd){
                    return "BlueArrow-reverse.png";
                }
                return "BlueArrow.png";
            }
        }
        if(currentTurn == 0){
            if(reachedEnd){
                return "RedArrow-reverse.png";
            }
            return "RedArrow.png";
        } else {
            if(reachedEnd){
                return "RedArrow.png";
            }
            return "RedArrow-reverse.png";
        }
    }
}
