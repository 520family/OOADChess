public class Player{
    //boolean representation of player side, true as blue side and false for red side
    private boolean blueSide;
    //constructor carry parameter of side
    public Player(boolean side){
        setSide(side);
    }
    //getter for boolean blueSide
    public boolean isBlueSide(){
        return this.blueSide;
    } 
    //to set boolean value for variable blueSide of each player
    public void setSide(boolean side){
        this.blueSide = side;
    }
}
