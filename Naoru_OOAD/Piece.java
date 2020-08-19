import java.util.*;

abstract class Piece {
    private boolean dead = false;
    private boolean blue = false;

    public Piece(boolean blue) {
        this.setBlue(blue);
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

    public abstract boolean validMove(ChessBoard board, Square from, Square to);

    public abstract String getIcon(); // TODO: need to change to Icon
}


class Sun extends Piece {
    public Sun(boolean blue) {
        super(blue);
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
        }

        return false;
    }

    public String getIcon(){
        if(isBlue()){
            return "BlueSun.png";
        }
        return "RedSun.png";
    }
}


class Chevron extends Piece {
    public Chevron(boolean blue) {
        super(blue);
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
    
    public String getIcon(){
        if(isBlue()){
            return "BlueChevron.png";
        }
        return "RedChevron.png";
    }
}


class Triangle extends Piece {
    public Triangle(boolean blue) {
        super(blue);
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

    public String getIcon(){
        if(isBlue()){
            return "BlueTriangle.png";
        }
        return "RedTriangle.png";
    }
}


class Plus extends Piece {
    public Plus(boolean blue) {
        super(blue);
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

    public String getIcon(){
        if(isBlue()){
            return "BluePlus.png";
        }
        return "RedPlus.png";
    }
}


class Arrow extends Piece {
    private boolean reachedEnd = false;

    public Arrow(boolean blue){
        super(blue);
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
                //if((y == 1 || y == 2) && x == 0){
                    return true; 
                //}
            } else if(y <= 0){ // Going backwards
                x = Math.abs(x);
                y = Math.abs(y);
                return true;
                //if((y == 1 || y == 2) && x == 0){
                  //  return true; 
                //}
            }
            return false;
        }
    }

    public String getIcon(){
        if(isBlue()){
            if(reachedEnd){
                return "BlueArrow-reverse.png";
            }
            return "BlueArrow.png";
        }

        if(reachedEnd){
            return "RedArrow-reverse.png";
        }
        return "RedArrow.png";
    }
}
