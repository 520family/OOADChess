import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GameController implements ActionListener {
    private int game_id;
    private ArrayList<Player> players = new ArrayList<>();
    private int p1Moves = 0;
    private int p2Moves = 0;
    private int currentTurn = 0;
    private ChessBoard board;
    private Player winner;
    private GameGUI gameGui;
    protected int firstClick = 0;   
    protected int startX;
    protected int startY;
    protected int endX;
    protected int endY;

    public GameController() {
        gameGui = new GameGUI();

        gameGui.getStartButton().addActionListener(this);
        gameGui.getStartButton().setActionCommand("start");

    }
    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand() == "start"){
            Player p1 = new Player(false);
            Player p2 = new Player(true);

            this.initialize(p1, p2);
        }        
        JButton[][] buttons = gameGui.getAllButtons();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 7; j++){
                buttons[i][j].addActionListener(new MoveListener(i,j));
            }
        }
    }

    public void saveGame() {

    }

    private void initialize(Player p1, Player p2) {
        players.add(p1);
        players.add(p2);

        board = new ChessBoard();

        if (p1.isBlueSide()) {
            this.currentTurn = 0;
        } else {
            this.currentTurn =1;
        }

        this.updateVisual();
        // movesPlayed.clear();
    }

    public void switchCurrentPlayer() {
        this.currentTurn ^= 1;
        System.out.println(this.currentTurn);
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

    /* public void piecesSwitching() {
    ArrayList<Square> toPlus = new ArrayList<>();
    ArrayList<Square> toTriangle = new ArrayList<>();
    boolean sideState = false; // Save player’s side
    Player player = null;
    if (p1Moves % 2 == 0 || p2Moves % 2 == 0) { // Even number 2nd, 4th, 6th etc
    if (p1Moves % 2 == 0) { // VERY BAD WAY OF DOING THIS
    player = this.players.get(0);
    }
    if (p2Moves % 2 == 0) { // BUT I LAZY TO THINK RN MAYBE fix later
    player = this.players.get(1);
    }
    if(player == null){
    // throw exception or show something here
    }
    sideState = player.isBlueSide();
    Square[][] allBox = board.getAllBox();
    for(int i = 0; i < allBox.length; i++){
    for(int j = 0; j < allBox[i].length; j++){
    Square box = allBox[i][j];
    Piece source = box.getPiece();
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
    for (Square plus : toPlus) {
    plus.setPiece(new Plus(sideState));
    // Need update icon somewhere maybe in setPiece()
    }
    for (Square triangle : toTriangle) {
    triangle.setPiece(new Triangle(sideState));
    // Need update icon somewhere maybe in setPiece()
    }

    }
     */
    public boolean movePiece(int startX, int startY, int endX, int endY)
    // return true when piece is moved and false otherwise
    {
        Piece source = board.getBox(startX, startY).getPiece();
        JButton[][] buttons = gameGui.getAllButtons();
        boolean playerside = board.getBox(startX, startY).getPiece().isBlue();

        if(playerside != this.getCurrentPlayer().isBlueSide()) {
            return false;
        }
        //to ensure if accidentally pressed on empty square nothing happen
        try {
            if (source.isBlue() != playerside) {
            return false;
            }
        } catch (Exception e) {
            return false;
        }


        Square from = board.getBox(startX, startY);
        Square to = board.getBox(endX, endY);
        
        if (!source.validMove(board, from, to)) {
            return false;
        }
 
        // Handle moves here
        Piece target = to.getPiece();
        if (target != null) {
            target.setDead(true);
            // remove icon from GUI
            buttons[endX][endY].setIcon(loadImage(from.getPiece().getIcon()));
        }
        buttons[startX][startY].setIcon(null);
        to.setPiece(source);
        from.setPiece(null);

        if (source instanceof Arrow) {
            // Handle arrow reaching end or some shit here
            Arrow arrowPiece = (Arrow) source;
            if (arrowPiece.hasReachedEnd()) {
                if (endY <= 0) {
                arrowPiece.setReachedEnd(false);
                }
            } else {
                if (endY >= 8) {
                arrowPiece.setReachedEnd(true);
                }
            }
        }

        if (this.currentTurn == 0) {
            p1Moves += 1;
        } else {
            p2Moves += 1;
        }

        //this.piecesSwitching();
        this.switchCurrentPlayer();
        this.updateVisual();
        return true;

    }

    public void updateVisual(){
        JButton[][] buttons = gameGui.getAllButtons();
        Square[][] squares = board.getAllBox();

        for(int i = 0; i < squares.length; i++){
            for(int j = 0; j < squares[i].length; j++){
                Square box = squares[i][j];
                Piece piece = box.getPiece();
                // Need update Icon here
                if (squares[i][j].getPiece() != null){
                    buttons[i][j].setIcon(loadImage(piece.getIcon()));
                }
            }
        }
    }

    private ImageIcon loadImage(String paramString) {
        Image image1 = (new ImageIcon(getClass().getResource(paramString))).getImage();
        Image image2 = image1.getScaledInstance(62, 62, 4);
        return new ImageIcon(image2);
    }

    public static void main (String[] args){
        new GameController();
    }

    class MoveListener implements ActionListener{
        int x;
        int y;
        public MoveListener(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e){
            if(firstClick == 0){
                System.out.println(firstClick);
                startX = x;
                startY = y;
                firstClick = 1;
                System.out.println(firstClick);
            } else if(firstClick == 1){
                endX = x;
                endY = y;
                firstClick = 0;
                boolean moved = movePiece(startX,startY,endX,endY);   
            }
        }
    }
}

