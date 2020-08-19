import sun.java2d.SunCompositeContext;
import sun.security.provider.Sun;

public class PieceFactory {
    public Piece makePiece(String pieceName,boolean isBlue){
        switch (pieceName) {
            case "Sun":
                return new Sun(isBlue);
                break;
            case "Plus":
                return new Plus(isBlue);
                break;
            case "Triangle":
                return new Triangle(isBlue);
                break;
            case "Chevron":
                return new Chevron(isBlue);
                break;
            case "Arrow":
                return new Arrow(isBlue);
                break;
            default:
                return null;
                break;
        }
            
    }
}