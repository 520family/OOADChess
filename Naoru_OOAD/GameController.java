import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

//javac GameController.java GameGUI.java Piece.java ChessBoard.java Square.java Player.java

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
        gameGui.getSaveButton().addActionListener(this);
        gameGui.getSaveButton().setActionCommand("save");
        gameGui.getLoadButton().addActionListener(this);
        gameGui.getLoadButton().setActionCommand("load");

    }
    
    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand() == "start"){
            Player p1 = new Player(false);
            Player p2 = new Player(true);

            this.initialize(p1, p2);
                
            JButton[][] buttons = gameGui.getAllButtons();
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 7; x++){
                    buttons[y][x].addActionListener(new MoveListener(y,x));
                }
            }
        } else if(event.getActionCommand() == "save"){
            saveGame();
        } else if(event.getActionCommand() == "load"){
            board.clearBoxes();
            loadGame();
            this.updateVisual();
        }
    }

    public void saveGame() {
        File file = new File("SaveGame.txt");

        try {
             PrintWriter fout = new PrintWriter(file);
            fout.println(currentTurn + " " + p1Moves + " " + p2Moves );
            for(int y = 0 ; y < 8 ; y++){
                for(int x = 0; x < 7 ; x++){
                    if(board.getBox(x, y).getPiece() != null){
                        fout.println(x +" "+ y +" "+board.getBox(x, y).getPiece().getName()+" "+board.getBox(x, y).getPiece().isBlue());
                    } else {
                        fout.println(x +" "+ y +" "+ "empty");
                    }
                }
            }
            
            fout.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(){
        File file = new File("SaveGame.txt");
        int x;
        int y;
        String name;
        boolean bool;
        try {
           Scanner scan = new Scanner(file); 
           currentTurn = scan.nextInt();
           p1Moves = scan.nextInt();
           p2Moves = scan.nextInt();
           while(scan.hasNext()){
                x = scan.nextInt();
                y = scan.nextInt();
                name = scan.next();
               if(!name.equals("empty")){
                    bool = scan.nextBoolean();
                    board.getBox(x, y).setPiece(PieceFactory.makePiece(name,bool));
               } else {
                    board.getBox(x, y).setPiece(null);
               }
           }
           scan.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found.");
        } 
    }
    
    private void initialize(Player p1, Player p2) {
        players.add(p1);
        players.add(p2);

        board = new ChessBoard();

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
    
    public boolean movePiece(int startX, int startY, int endX, int endY)
    // return true when piece is moved and false otherwise
    {
        Piece source = board.getBox(startX, startY).getPiece();
        JButton[][] buttons = gameGui.getAllButtons();
        boolean playerside = board.getBox(startX, startY).getPiece().isBlue();

        if(firstClick == 0){
            JButton selected = buttons[startY][startX];
            selected.setBackground(new Color(255,255,255));
        }

        if(playerside != this.getCurrentPlayer().isBlueSide()) {
            gameGui.ShowWrongPieceError();
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
            buttons[endY][endX].setIcon(loadImage(from.getPiece().getIcon(currentTurn)));
        }
        buttons[startY][startX].setIcon(null);
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
        //this.Flipboard();
        this.updateVisual();
        return true;

    }

    public void Flipboard(){
        Square[][] UpperSquares = board.getUpperHalfBox();
        Square[][] LowerSquares = board.getLowerHalfBox();

        for(int y = 3; y >= 0; y--){
            for(int x = 0; x < 7; x++){
                board.SetBoxes(x, Math.abs(3-y), LowerSquares[y][x]);
            }
        }
 
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 7; x++){
                board.SetBoxes(x, 7-y, UpperSquares[y][x]);
            }
        }

    }

    public void updateVisual(){
        JButton[][] buttons = gameGui.getAllButtons();
        Square[][] squares = board.getAllBox();

        JLabel turn = gameGui.getTurnLabel();
        String side = currentTurn == 0 ? "Red" : "Blue";
        turn.setText(side + " turn");

        for(int y = 0; y < squares.length; y++){
            for(int x = 0; x < squares[y].length; x++){
                Square box = squares[y][x];
                Piece piece = box.getPiece();
                // Need update Icon here
                if (squares[y][x].getPiece() != null){
                    buttons[y][x].setIcon(loadImage(piece.getIcon(currentTurn)));
                }else{
                    buttons[y][x].setIcon(null);
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
        public MoveListener(int y, int x){
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e){
            if(firstClick == 0){
                startX = x;
                startY = y;
                firstClick = 1;
                if(board.getBox(x,y).getPiece() == null){
                    firstClick = 0;
                } else {
                    Object source = e.getSource();
                    if(source instanceof JButton){
                        JButton button = (JButton) source;
                        button.setBackground(Color.GREEN);
                    }
                }
            } else if(firstClick == 1){
                endX = x;
                endY = y;
                firstClick = 0;
                movePiece(startX,startY,endX,endY);   
            }
        }
    }
}
