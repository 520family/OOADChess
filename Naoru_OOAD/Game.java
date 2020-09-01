import java.util.*;
import java.io.*;

public class Game {

    public static final int MOVE_ERROR_EMPTY = 1;
    public static final int MOVE_ERROR_WRONG_SIDE = 2;
    public static final int MOVE_ERROR_WRONG_MOVE = 3;
    public static final int MOVE_SUCCESS = 4;
    public static final int GAME_END = 5;

    private ArrayList<Player> players = new ArrayList<>();
    private int p1Moves = 0;
    private int p2Moves = 0;
    private int currentTurn = 0;
    private ChessBoard board;
    private Player winner;

    public Game(){
        Player p1 = new Player(false);
        Player p2 = new Player(true);
        this.initialize(p1, p2);
    }

    public void initialize(Player p1, Player p2) {
        players.add(p1);
        players.add(p2);

        board = new ChessBoard();
    }

    public void switchCurrentPlayer() {
        this.currentTurn ^= 1;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.currentTurn);
    }

    public Player getWinner() {
        return this.winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isGameFinished() {
        return this.winner == null;
    }

    public void piecesSwitching() {
        ArrayList<Square> toPlus = new ArrayList<>();
        ArrayList<Square> toTriangle = new ArrayList<>();
        boolean sideState = false; // Save player’s side
        Player player = null;
        if(p1Moves > 0 && p1Moves % 2 == 0 && currentTurn == 0){
            player = this.players.get(0);
        }
        if(p2Moves > 0 && p2Moves % 2 == 0  && currentTurn == 1){
            player = this.players.get(1);
        }
        if (player instanceof Player) {
            sideState = player.isBlueSide();
            Square[][] allBox = board.getAllBox();
            for (int i = 0; i < allBox.length; i++) {
                for (int j = 0; j < allBox[i].length; j++) {
                    Square box = allBox[i][j];
                    Piece source = box.getPiece();
                    if(source instanceof Piece){
                        if (source.isBlue() == sideState) {
                            if (source instanceof Triangle) {
                                toPlus.add(box);
                            } else if (source instanceof Plus) {
                                toTriangle.add(box);
                            }
                        }
                    }
                }
            }
        }
        for (Square plus : toPlus) {
            plus.setPiece(PieceFactory.makePiece("Plus", sideState));
        }
        for (Square triangle : toTriangle) {
            triangle.setPiece(PieceFactory.makePiece("Triangle", sideState));
        }
    }
    
    public int movePiece(int startX, int startY, int endX, int endY)
    // return true when piece is moved and false otherwise
    {
        Piece source = board.getBox(startX, startY).getPiece();
        //JButton[][] buttons = gameGui.getAllButtons();
        boolean playerside = board.getBox(startX, startY).getPiece().isBlue();

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


        Square from = board.getBox(startX, startY);
        Square to = board.getBox(endX, endY);
        
        if (!source.validMove(board, from, to)) {
            return MOVE_ERROR_WRONG_MOVE;
        }
 
        // Handle moves here
        Piece target = to.getPiece();
        if (target != null) {
            target.setDead(true);
            if(target instanceof Sun){
                return GAME_END;
            }
            // remove icon from GUI
            //buttons[endY][endX].setIcon(loadImage(from.getPiece().getIcon(currentTurn)));
        }
        //buttons[startY][startX].setIcon(null);
        to.setPiece(source);
        from.setPiece(null);

        if (source instanceof Arrow) {
            // Handle arrow reaching end or some shit here
            Arrow arrowPiece = (Arrow) source;

            if(!arrowPiece.hasReachedEnd()){
                if(endY == 0){
                    arrowPiece.setReachedEnd(true);
                }
            } else {
                if(endY == 7){
                    arrowPiece.setReachedEnd(false);
                }
            }
            /*if (!arrowPiece.hasReachedEnd() && !arrowPiece.isBlue()) {
                if (endY == 0){
                    arrowPiece.setReachedEnd(true);
                }
            }else if(!arrowPiece.hasReachedEnd() && arrowPiece.isBlue()){
                if (endY == 7){
                    arrowPiece.setReachedEnd(true);
                }
            }

            if (arrowPiece.hasReachedEnd() && !arrowPiece.isBlue()) {
                if (endY == 7){
                    arrowPiece.setReachedEnd(false);
                }
            }else if(arrowPiece.hasReachedEnd() && arrowPiece.isBlue()){
                if (endY == 0){
                    arrowPiece.setReachedEnd(false);
                }
            }*/
        }


        if (this.currentTurn == 0) {
            p1Moves += 1;
        } else {
            p2Moves += 1;
        }

        this.piecesSwitching();
        this.switchCurrentPlayer();
        this.Flipboard();
        return MOVE_SUCCESS;

    }

    public void Flipboard(){
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
        this.restart();
    }

    public void restart(){
        board.clearBoxes();
        board.resetBoard();
        this.currentTurn = 0;
        p1Moves = 0;
        p2Moves = 0;
    }

    public void setCurrentTurn(int turn){
        this.currentTurn = turn;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public void setPlayer1Moves(int count){
        this.p1Moves = count;
    }

    public int getPlayer1Moves(){
        return p1Moves;
    }
    
    public void setPlayer2Moves(int count){
        this.p2Moves = count;
    }

    public int getPlayer2Moves(){
        return p2Moves;
    }

    public ChessBoard getBoard(){
        return board;
    }
    
}
