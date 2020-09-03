public class PieceFactory {
    //Low Zi Jian
    public static Piece makePiece(String piece_name,boolean is_blue){
        switch (piece_name) {
            case "Sun":
                return new Sun(is_blue);
                
            case "Plus":
                return new Plus(is_blue);
               
            case "Triangle":
                return new Triangle(is_blue);
               
            case "Chevron":
                return new Chevron(is_blue);
                
            case "Arrow":
                return new Arrow(is_blue);
                
            default:
                return null;       
        }      
    }
}
