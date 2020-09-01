import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

//javac GameController.java GameGUI.java Piece.java ChessBoard.java Square.java Player.java

public class GameController implements ActionListener {

    protected int firstClick = 0;   
    protected int startX;
    protected int startY;
    protected int endX;
    protected int endY;

    private GameGUI gameGui;
    private Game currentGame;

    public GameController() {
        gameGui = new GameGUI();

        gameGui.getStartButton().addActionListener(this);
        gameGui.getStartButton().setActionCommand("start");
        gameGui.getSaveButton().addActionListener(this);
        gameGui.getSaveButton().setActionCommand("save");
        gameGui.getLoadButton().addActionListener(this);
        gameGui.getLoadButton().setActionCommand("load");

    }

    public void startGame(){
        if(currentGame == null){
            currentGame = new Game();

            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 7; x++){
                    gameGui.getButton(x, y).addActionListener(new MoveListener(y,x));
                }
            }
            updateVisual();
        }
    }

    public void saveGame(Game currentGame) {
        File file = new File("SaveGame.txt");
        
        try {
            PrintWriter fout = new PrintWriter(file);
            fout.println(currentGame.getCurrentTurn() + " " + currentGame.getPlayer1Moves() + " " + currentGame.getPlayer2Moves());
            ChessBoard board = currentGame.getBoard();
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
        currentGame = new Game();
        File file = new File("SaveGame.txt");
        int x;
        int y;
        String name;
        boolean bool;
        try {
           Scanner scan = new Scanner(file); 
           currentGame.setCurrentTurn(scan.nextInt()); 
           currentGame.setPlayer1Moves(scan.nextInt());
           currentGame.setPlayer2Moves(scan.nextInt());
           while(scan.hasNext()){
                x = scan.nextInt();
                y = scan.nextInt();
                name = scan.next();
               if(!name.equals("empty")){
                    bool = scan.nextBoolean();
                    currentGame.getBoard().getBox(x, y).setPiece(PieceFactory.makePiece(name,bool));
               } else {
                currentGame.getBoard().getBox(x, y).setPiece(null);
               }
           }
           scan.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No save file found.");
        } 
    }

    public void updateVisual(){
        JButton[][] buttons = gameGui.getAllButtons();
        Square[][] squares = currentGame.getBoard().getAllBox();

        JLabel turn = gameGui.getTurnLabel();
        String side = currentGame.getCurrentTurn() == 0 ? "Red" : "Blue";
        turn.setText(side + " turn");
        turn.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        if(currentTurn == 0){
        turn.setForeground(Color.red);}
        else{
        turn.setForeground(Color.blue);}

        for(int y = 0; y < squares.length; y++){
            for(int x = 0; x < squares[y].length; x++){
                Square box = squares[y][x];
                Piece piece = box.getPiece();
                // Need update Icon here
                if (squares[y][x].getPiece() != null){
                    buttons[y][x].setIcon(gameGui.resizeIcon(gameGui.loadImage(piece.getIcon(currentGame.getCurrentTurn())), buttons[y][x].getWidth(), buttons[y][x].getHeight()));
                }else{
                    buttons[y][x].setIcon(null);
                }
            }
        }
    }

    public static void main (String[] args){
        new GameController();
    }

    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand() == "start"){
            startGame();
        } else if(event.getActionCommand() == "save"){
            saveGame(currentGame);
        } else if(event.getActionCommand() == "load"){
            loadGame();
            updateVisual();
        }
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
                if(currentGame.getBoard().getBox(x,y).getPiece() == null){
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
                JButton selected = gameGui.getButton(startX, startY);
                selected.setBackground(new Color(255,255,255));
                int result = currentGame.movePiece(startX,startY,endX,endY);
                switch(result){
                    case Game.MOVE_ERROR_WRONG_MOVE:
                        gameGui.ShowInvalidMoveError();
                        break;
                    case Game.MOVE_ERROR_WRONG_SIDE:
                        gameGui.ShowWrongPieceError();
                        break;
                    case Game.GAME_END:
                        gameGui.endGameMessage(currentGame.getCurrentPlayer().toString());
                        currentGame.restart();
                        break;
                }
                updateVisual();  
            }
        }
    }
}
