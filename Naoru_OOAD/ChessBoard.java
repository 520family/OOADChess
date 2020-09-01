import java.util.*;

class ChessBoard
{   Square[][] boxes = new Square[8][7]; // create a spot for the pieces

    public ChessBoard(){
        this.resetBoard(); // this line is to reset the game 
	}

    public Square getBox(int x, int y)//this function is to get coordinates box of x and y axis
	{ 
		//if the user type the out of the range of x and y axis it with throw a message error
		if(x<0 || x>7 || y<0 || y>8)
        { 
            //throw new Exception("spot did not exist");
        }
	
	    return boxes[y][x];
 	}

    public void resetBoard()// this function is reset the pieces into their place
    { 
        // Initialize remain blank Square
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                boxes[y][x] = new Square(x, y);
            }
        }

        // Initialize blue pieces
        boxes[0][0] = new Square(0, 0, new Plus(true));
        boxes[0][1] = new Square(1, 0, new Triangle(true));
        boxes[0][2] = new Square(2, 0, new Chevron(true));
        boxes[0][3] = new Square(3, 0, new Sun(true));
        boxes[0][4] = new Square(4, 0, new Chevron(true));
        boxes[0][5] = new Square(5, 0, new Triangle(true));
        boxes[0][6] = new Square(6, 0, new Plus(true));
        boxes[1][0] = new Square(0, 1, new Arrow(true));
        boxes[1][2] = new Square(2, 1, new Arrow(true));
        boxes[1][4] = new Square(4, 1, new Arrow(true));
        boxes[1][6] = new Square(6, 1, new Arrow(true));

        // Initialize red pieces
        boxes[6][0] = new Square(0, 6, new Arrow(false));
        boxes[6][2] = new Square(2, 6, new Arrow(false));
        boxes[6][4] = new Square(4, 6, new Arrow(false));
        boxes[6][6] = new Square(6, 6, new Arrow(false));
        boxes[7][0] = new Square(0, 7, new Plus(false));
        boxes[7][1] = new Square(1, 7, new Triangle(false));
        boxes[7][2] = new Square(2, 7, new Chevron(false));
        boxes[7][3] = new Square(3, 7, new Sun(false));
        boxes[7][4] = new Square(4, 7, new Chevron(false));
        boxes[7][5] = new Square(5, 7, new Triangle(false));
        boxes[7][6] = new Square(6, 7, new Plus(false));
    }

    public Square[][] getAllBox() {
        return boxes;
    }

    public Piece[][] getReversedUpperPieces() {

        Piece[][] tempbox = new Piece [4][7];
        for(int y = 3; y >= 0; y--){
            for(int x = 6; x >= 0; x--){
                tempbox[3-y][6-x] = boxes[y][x].getPiece();
            }
        }
        return tempbox;
    }

    public Piece[][] getReversedLowerPieces() {

        Piece[][] tempbox = new Piece [4][7];
        for(int y = 3; y >= 0; y--){
            for(int x = 6; x >= 0; x--){
                tempbox[3-y][6-x] = boxes[y+4][x].getPiece();
            }
        }
        return tempbox;
    }

    public void setBox(int x, int y, Square square){
        boxes[y][x] = square;
    }
     
    public void clearBoxes(){
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                boxes[y][x] = new Square(x, y);
            }
        }
    }
}

