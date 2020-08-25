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
        if (!isBlue()){
        if (x < 0 && Math.abs(x) == Math.abs(y) && y > 0){
        int j = from.getY()-1;
        for( int i = from.getX()+1 ; i < to.getX() ; i++){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;}
                j--;
                }  
              return true;
            }
        else if(x > 0 && Math.abs(x) == Math.abs(y) && y > 0){
            int j = from.getY()-1;
            for( int i = from.getX()-1 ; i > to.getX() ; i--){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;}
                j--;
                }
               return true;
            }
        else if( x > 0 && Math.abs(x) == Math.abs(y) && y < 0){
            int j = from.getY()+1;
            for( int i = from.getX()-1 ; i > to.getX() ; i--){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;}
                j++;
                }
               return true;  
        }
        else if (x < 0 && Math.abs(x) == Math.abs(y) && y < 0){
        int j = from.getY()+1;
        for( int i = from.getX()+1 ; i < to.getX() ; i++){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;}
                j++;
                }  
              return true;
            }
    }
       else if (isBlue()){
        if (x > 0 && Math.abs(x) == Math.abs(y)){
               int j = from.getY()+1;
               for(int i = from.getX()-1 ; i > to.getX() ; i--  ){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;
                }
                j++;
                }  
              return true;
            }
        else if(x < 0 && Math.abs(x)==Math.abs(y)){
            int j = from.getY()+1;
            for(int i = from.getX()+1 ; i < to.getX() ; i++){
                if (board.getBox(i,j).getPiece()!=null){
                    return false;
                }
                j++;
                }
               return true;  
        }    
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
        if((from.getX() - to.getX()) > 0 || (from.getY() - to.getY()) > 0){
            if ((x == 0 && y >= 1)) {
                for(int i = 1; i <= y;  i++){
                    if(board.getBox(from.getX(), from.getY() - i).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else if((x >= 1 && y == 0)){
                for(int i = 1; i <= x;  i++){
                    if(board.getBox(from.getX() - i, from.getY()).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }
        }else if((from.getX() - to.getX()) < 0 || (from.getY() - to.getY()) < 0){
             if (x == 0 && (from.getY() - to.getY()) <= -1) {
                for(int i = y; i > 0;  i--){
                    if(board.getBox(from.getX(), from.getY() + i).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else if((from.getX() - to.getX()) <= -1 && y == 0){
                for(int i = x; i > 0;  i--){
                    if(board.getBox(from.getX() + i, from.getY()).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }
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
            if(y < 0){ // blue going forward, red going backward
                if((Math.abs(y) == 1 || Math.abs(y) == 2) && x == 0){
                    if (Math.abs(y)==2 && board.getBox(from.getX(),from.getY()+1).getPiece()!=null){
                        return false;
                    }

                    if(isBlue()){
                        return false;
                    } else {
                        if((y == -1 || y == -2) && x == 0){
                            return true; 
                        }
                        return false;
                    }
                }
                return false;
            } else { // red going forward, blue going backward
                if((y == 1 || y == 2) && x == 0){
                    if (y==2 && board.getBox(from.getX(),from.getY()-1).getPiece()!=null){
                        return false;
                    }

                    return isBlue();
                }
                return false;
            }
        } else {
            if(y > 0){ // red going forward, blue going backward
                if((y == 1 || y == 2) && x == 0){
                    if (y==2 && board.getBox(from.getX(),from.getY()-1).getPiece()!=null){
                        return false;
                    }

                    return !isBlue();
                }
                return false;
            } else { // blue going forward, red going backward
                if((Math.abs(y) == 1 || Math.abs(y) == 2) && x == 0){
                    if(Math.abs(y)==2 && board.getBox(from.getX(),from.getY()+1).getPiece()!=null){
                        return false;
                    }
                    
                    if(isBlue()){
                        if((y == -1 || y == -2) && x == 0){
                            return true; 
                        }
                        return false;
                    } else {
                        return false;
                    }
                }
                return false;
            }
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
