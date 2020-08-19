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
        // initialize remain blank Square
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                boxes[i][j] = new Square(i, j);
            }
        }

        // initialize blue pieces
        boxes[0][0] = new Square(0, 0, new Plus(true));
        boxes[0][1] = new Square(0, 1, new Triangle(true));
        boxes[0][2] = new Square(0, 2, new Chevron(true));
        boxes[0][3] = new Square(0, 3, new Sun(true));
        boxes[0][4] = new Square(0, 4, new Chevron(true));
        boxes[0][5] = new Square(0, 5, new Triangle(true));
        boxes[0][6] = new Square(0, 6, new Plus(true));
        boxes[1][0] = new Square(1, 0, new Arrow(true));
        boxes[1][2] = new Square(1, 2, new Arrow(true));
        boxes[1][4] = new Square(1, 4, new Arrow(true));
        boxes[1][6] = new Square(1, 6, new Arrow(true));

        // initialize red pieces
        boxes[6][0] = new Square(6, 0, new Arrow(false));
        boxes[6][2] = new Square(6, 2, new Arrow(false));
        boxes[6][4] = new Square(6, 4, new Arrow(false));
        boxes[6][6] = new Square(6, 6, new Arrow(false));
        boxes[7][0] = new Square(7, 0, new Plus(false));
        boxes[7][1] = new Square(7, 1, new Triangle(false));
        boxes[7][2] = new Square(7, 2, new Chevron(false));
        boxes[7][3] = new Square(7, 3, new Sun(false));
        boxes[7][4] = new Square(7, 4, new Chevron(false));
        boxes[7][5] = new Square(7, 5, new Triangle(false));
        boxes[7][6] = new Square(7, 6, new Plus(false));
    }

    public Square[][] getAllBox() {
        return boxes;
    }
}

