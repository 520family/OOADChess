public class Square{
    private Piece p;
    private int x; 
    private int y; 
   	
    public Square(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public Square(int x, int y, Piece p) 
    { 
        this.setPiece(p); 
        this.setX(x); 
        this.setY(y); 
    } 

     public Piece getPiece() 
    { 
        return this.p; 
    } 
  
    public void setPiece(Piece p) 
    { 
        this.p = p; 
    } 
  
    public int getX() 
    { 
        return this.x; 
    } 
  
    public void setX(int x) 
    { 
        this.x = x; 
    } 
  
    public int getY() 
    { 
        return this.y; 
    } 
  
    public void setY(int y) 
    { 
        this.y = y; 
    } 
}
