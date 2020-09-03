import java.util.*;

abstract class Piece {
    private boolean dead = false;
    private boolean blue = false;
    private String name;
    
    public Piece(boolean blue,String name) {
        this.setBlue(blue);
        this.setName(name);
    }

    //Terence Tan Kah Chee
    public boolean isBlue() {
        return this.blue;
    }

    //Terence Tan Kah Chee
    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    //Terence Tan Kah Chee
    public boolean isDead() {
        return this.dead;
    }

    //Terence Tan Kah Chee
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    //Terence Tan Kah Chee
    public void setName(String name){
        this.name = name;
    }

    //Terence Tan Kah Chee
    public String getName(){
        return name;
    }

    //Terence Tan Kah Chee
    public abstract boolean validMove(ChessBoard board, Square from, Square to);

    //Terence Tan Kah Chee
    public abstract String getIcon(int current_turn); 
}


class Sun extends Piece {
    public Sun(boolean blue) {
       super(blue,"Sun");

    }

    //Terence Tan Kah Chee
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

    public String getIcon(int current_turn){
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

    //Terence Tan Kah Chee
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
    
    //Terence Tan Kah Chee
    public String getIcon(int current_turn){
        if(isBlue()){
            if(current_turn == 0){
                return "BlueChevron-reverse.png";
            } else {
                return "BlueChevron.png";
            }
        } else {
            if(current_turn == 0){
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

    //Terence Tan Kah Chee, Tan Kuang Cheng
    public boolean validMove(ChessBoard board, Square from, Square to) {
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 

        int x = from.getX() - to.getX();
        int y = from.getY() - to.getY();
    
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

        return false;
    }

    //Terence Tan Kah Chee
    public String getIcon(int current_turn){
        if(isBlue()){
            if(current_turn == 0){
                return "BlueTriangle-reverse.png";
            } else {
                return "BlueTriangle.png";
            }
        } else {
            if(current_turn == 0){
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

    //Terence Tan Kah Chee
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
                for(int i = 1; i <= y-1;  i++){
                    if(board.getBox(from.getX(), from.getY() - i).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else if((x >= 1 && y == 0)){
                for(int i = 1; i <= x-1;  i++){
                    if(board.getBox(from.getX() - i, from.getY()).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }
        }else if((from.getX() - to.getX()) < 0 || (from.getY() - to.getY()) < 0){
             if (x == 0 && (from.getY() - to.getY()) <= -1) {
                for(int i = y-1; i > 0;  i--){
                    if(board.getBox(from.getX(), from.getY() + i).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else if((from.getX() - to.getX()) <= -1 && y == 0){
                for(int i = x-1; i > 0;  i--){
                    if(board.getBox(from.getX() + i, from.getY()).getPiece() != null){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    //Terence Tan Kah Chee
    public String getIcon(int current_turn){
        if(isBlue()){
            return "BluePlus.png";
        }
        return "RedPlus.png";
    }
}


class Arrow extends Piece {
    private boolean reached_end = false;

    public Arrow(boolean blue){
        super(blue,"Arrow");
    }

    public void setReached_End(boolean value) {
        this.reached_end = value;
    }

    public boolean hasReached_End() {
        return this.reached_end;
    }

    //Chan Jun Ting, Low Zi Jian
    public boolean validMove(ChessBoard board, Square from, Square to){
        // check if piece killing his ally
        if(to.getPiece() != null){
            if (to.getPiece().isBlue() == this.isBlue()) {
                return false;
            }
        } 
        int x = from.getX() - to.getX(); 
        int y = from.getY() - to.getY();
        
        if(reached_end){
            if(y > 0){ // Going forward
                return false;
            } else { // Going backward
                if((Math.abs(y) == 1 || Math.abs(y) == 2) && x == 0){
                    if (Math.abs(y)==2 && board.getBox(from.getX(),from.getY()+1).getPiece()!=null){
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else {
            if(y > 0){ // Going forward
                if((Math.abs(y) == 1 || Math.abs(y) == 2) && x == 0){
                    if (Math.abs(y)==2 && board.getBox(from.getX(),from.getY()-1).getPiece()!=null){
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } else { // Going backward
                return false;
            }
        }
    }

    //Terence Tan Kah Chee
    public String getIcon(int current_turn){
        if(isBlue()){
            if(current_turn == 0){
                if(reached_end){
                    return "BlueArrow.png";
                }
                return "BlueArrow-reverse.png";
            } else {
                if(reached_end){
                    return "BlueArrow-reverse.png";
                }
                return "BlueArrow.png";
            }
        }
        if(current_turn == 0){
            if(reached_end){
                return "RedArrow-reverse.png";
            }
            return "RedArrow.png";
        } else {
            if(reached_end){
                return "RedArrow.png";
            }
            return "RedArrow-reverse.png";
        }
    }
}
