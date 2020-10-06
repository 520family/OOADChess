
public class Player{
    //boolean representation of player side, true as blue side and false for red side
    private boolean blue_side;
    //constructor carry parameter of side
    public Player(boolean side){
        setSide(side);
    }
    //getter for boolean blueside
    public boolean isBlueSide(){
        return this.blue_side;
    } 
    //to set boolean value for variable blue_side of each player
    public void setSide(boolean side){
        this.blue_side = side;
    }
    //Low Zi Jian
    public String toString(){
        if(blue_side){
            return "blue";
        }
        return "red";
    }
}
