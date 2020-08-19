public class PieceFactory {
    public static Piece makePiece(String pieceName,boolean isBlue){
        switch (pieceName) {
            case "Sun":
                return new Sun(isBlue);
                
            case "Plus":
                return new Plus(isBlue);
               
            case "Triangle":
                return new Triangle(isBlue);
               
            case "Chevron":
                return new Chevron(isBlue);
                
            case "Arrow":
                return new Arrow(isBlue);
                
            default:
                return null;       
        }      
    }
}
