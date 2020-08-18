import java.util.*;

public class Move{
	private Player player;
	private Square fromSquare;
	private Square toSquare;
	private Piece movedPiece;
	private Piece killedPiece;
	
        public Move(Player player, Square fromSquare, Square toSquare){
            this.player = player;
            this.movedPiece = fromSquare.getPiece();
            this.fromSquare = fromSquare;
            this.toSquare = toSquare;
        }
        
        public void moved(){
        	if(toSquare.getPiece() != null){
        		if(fromSquare.getPiece().isBlue() != toSquare.getPiece().isBlue()){
        			killedPiece = toSquare.getPiece();
        			toSquare.setPiece(fromSquare.getPiece());
        			fromSquare.setPiece(null);
                        }	
                }
       }
       
       public Piece killed(){
        	return killedPiece;
       }
}
