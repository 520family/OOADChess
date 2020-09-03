import java.util.*;
import java.io.*;

public class Game {

    public static final int MOVE_ERROR_EMPTY = 1;
    public static final int MOVE_ERROR_WRONG_SIDE = 2;
    public static final int MOVE_ERROR_WRONG_MOVE = 3;
    public static final int MOVE_SUCCESS = 4;
    public static final int GAME_END = 5;

    private ArrayList<Player> players = new ArrayList<>();
    private int p1_moves = 0;
    private int p2_moves = 0;
    private int current_turn = 0;
    private ChessBoard board;

    public Game(){
        Player p1 = new Player(false);
        Player p2 = new Player(true);
        this.Initialize(p1, p2);
    }

    public void Initialize(Player p1, Player p2) {
        players.add(p1);
        players.add(p2);

        board = new ChessBoard();
    }

    public void switchCurrentPlayer() {
        this.current_turn ^= 1;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.current_turn);
    }

    public void switchPieces() {
        ArrayList<Square> to_plus = new ArrayList<>();
        ArrayList<Square> to_triangle = new ArrayList<>();
        boolean sidestate = false; // Save player’s side
        Player player = null;
        if(p1_moves > 0 && p1_moves % 2 == 0 && current_turn == 0){
            player = this.players.get(0);
        }
        if(p2_moves > 0 && p2_moves % 2 == 0  && current_turn == 1){
            player = this.players.get(1);
        }
        if (player instanceof Player) {
            sidestate = player.isBlueSide();
            Square[][] allBox = board.getAllBox();
            for (int i = 0; i < allBox.length; i++) {
                for (int j = 0; j < allBox[i].length; j++) {
                    Square box = allBox[i][j];
                    Piece source = box.getPiece();
                    if(source instanceof Piece){
                        if (source.isBlue() == sidestate) {
                            if (source instanceof Triangle) {
                                to_plus.add(box);
                            } else if (source instanceof Plus) {
                                to_triangle.add(box);
                            }
                        }
                    }
                }
            }
        }
        for (Square plus : to_plus) {
            plus.setPiece(PieceFactory.makePiece("Plus", sidestate));
        }
        for (Square triangle : to_triangle) {
            triangle.setPiece(PieceFactory.makePiece("Triangle", sidestate));
        }
    }
    
    //Terence Tan Kah Chee
    public int movePiece(int start_x, int start_y, int end_x, int end_y)
    // return true when piece is moved and false otherwise
    {
        Piece source = board.getBox(start_x, start_y).getPiece();
        boolean playerside = board.getBox(start_x, start_y).getPiece().isBlue();

        //check if player click enemy pieces
        if(playerside != this.getCurrentPlayer().isBlueSide()) {
            return MOVE_ERROR_WRONG_SIDE;

        }
        //to ensure if accidentally pressed on empty square nothing happen
        try {
            if (source.isBlue() != playerside) {
                return MOVE_ERROR_WRONG_SIDE;
            }
        } catch (Exception e) {
            return MOVE_ERROR_EMPTY;
        }


        Square from = board.getBox(start_x, start_y);
        Square to = board.getBox(end_x, end_y);
        
        if (!source.validMove(board, from, to)) {
            return MOVE_ERROR_WRONG_MOVE;
        }
 
        // Handle moves here
        Piece target = to.getPiece();
        if (target != null) {
            target.setDead(true);
            if(target instanceof Sun){
                to.setPiece(source);
                from.setPiece(null);
                return GAME_END;
            }
        }

        to.setPiece(source);
        from.setPiece(null);

        if (source instanceof Arrow) {
            Arrow arrow_piece = (Arrow) source;

            if(!arrow_piece.hasReached_End()){
                if(end_y == 0){
                    arrow_piece.setReached_End(true);
                }
            } else {
                if(end_y == 7){
                    arrow_piece.setReached_End(false);
                }
            }
        }


        if (this.current_turn == 0) {
            p1_moves += 1;
        } else {
            p2_moves += 1;
        }

        this.switchPieces();
        this.switchCurrentPlayer();
        this.flipBoard();
        return MOVE_SUCCESS;

    }

    //Terence Tan Kah Chee
    public void flipBoard(){
        Piece[][] UpperPieces = board.getReversedUpperPieces();
        Piece[][] LowerPieces = board.getReversedLowerPieces();
        
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 7; x++){
                board.getBox(x, y).setPiece(LowerPieces[y][x]);
            }
        }
 
        for(int y = 4; y < 8; y++){
            for(int x = 0; x < 7; x++){
                board.getBox(x, y).setPiece(UpperPieces[Math.abs(4-y)][x]);
            }
        }

    }

    public void endGame(){
        this.restartGame();
    }

    public void restartGame(){
        board.clearBoxes();
        board.resetBoard();
        this.current_turn = 0;
        p1_moves = 0;
        p2_moves = 0;
    }

    public void setCurrent_Turn(int turn){
        this.current_turn = turn;
    }

    public int getCurrent_Turn(){
        return current_turn;
    }

    public void setPlayer1Moves(int count){
        this.p1_moves = count;
    }

    public int getPlayer1Moves(){
        return p1_moves;
    }
    
    public void setPlayer2Moves(int count){
        this.p2_moves = count;
    }

    public int getPlayer2Moves(){
        return p2_moves;
    }

    public ChessBoard getBoard(){
        return board;
    }
    
}
