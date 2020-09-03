import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

public class GameController implements ActionListener {

    protected int first_click = 0;   
    protected int start_x;
    protected int start_y;
    protected int end_x;
    protected int end_y;

    private GameGUI game_gui;
    private Game current_game;

    public GameController() {
        game_gui = new GameGUI();

        game_gui.getStartButton().addActionListener(this);
        game_gui.getStartButton().setActionCommand("start");
        game_gui.getSaveButton().addActionListener(this);
        game_gui.getSaveButton().setActionCommand("save");
        game_gui.getSaveButton().setVisible(false);
        game_gui.getLoadButton().addActionListener(this);
        game_gui.getLoadButton().setActionCommand("load");
    }

    //Low Zi Jian
    public void startGame(){
        if(current_game == null){
            current_game = new Game();
            game_gui.getStartButton().setVisible(false);
            game_gui.getSaveButton().setVisible(true);
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 7; x++){
                    game_gui.getButton(x, y).addActionListener(new MoveListener(y,x));
                }
            }
            updateVisual();
        }
    }

    //Low Zi Jian
    public void saveGame(Game current_game) {
        if(current_game == null){
            game_gui.showSaveErrorMessage();
        } else {
            File file = new File("SaveGame.txt");
            try {
                PrintWriter fout = new PrintWriter(file);
                fout.println(current_game.getCurrent_Turn() + " " + current_game.getPlayer1Moves() + " " + current_game.getPlayer2Moves());
                ChessBoard board = current_game.getBoard();
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
                game_gui.showSaveCompleteMessage();
            } catch (FileNotFoundException e) {
                game_gui.showSaveErrorMessage();
            }
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
            //current_game = new Game();
            startGame();
            current_game.setCurrent_Turn(scan.nextInt()); 
            current_game.setPlayer1Moves(scan.nextInt());
            current_game.setPlayer2Moves(scan.nextInt());
            while(scan.hasNext()){
                x = scan.nextInt();
                y = scan.nextInt();
                name = scan.next();
                if(!name.equals("empty")){
                    bool = scan.nextBoolean();
                    current_game.getBoard().getBox(x, y).setPiece(PieceFactory.makePiece(name,bool));
                } else {
                    current_game.getBoard().getBox(x, y).setPiece(null);
                }
            }
            scan.close();
            updateVisual();
            game_gui.showLoadCompleteMessage();
            game_gui.getStartButton().setVisible(false);
            game_gui.getSaveButton().setVisible(true);
        } catch (FileNotFoundException e) {
            game_gui.showLoadErrorMessage();
        } 
    }


    public void updateVisual(){
        JButton[][] buttons = game_gui.getAllButtons();
        Square[][] squares = current_game.getBoard().getAllBox();

        JLabel turn = game_gui.getTurnLabel();
        String side = current_game.getCurrent_Turn() == 0 ? "Red" : "Blue";
        turn.setText(side + " Turn");
        turn.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        if(current_game.getCurrent_Turn() == 0){
            turn.setForeground(Color.red);
        }
        else{
            turn.setForeground(Color.blue);
        }

        for(int y = 0; y < squares.length; y++){
            for(int x = 0; x < squares[y].length; x++){
                Square box = squares[y][x];
                Piece piece = box.getPiece();

                if (squares[y][x].getPiece() != null){
                    buttons[y][x].setIcon(game_gui.resizeIcon(game_gui.loadImage(piece.getIcon(current_game.getCurrent_Turn())), buttons[y][x].getWidth(), buttons[y][x].getHeight()));
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
            saveGame(current_game);
        } else if(event.getActionCommand() == "load"){
            loadGame();
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
            if(first_click == 0){
                start_x = x;
                start_y = y;
                first_click = 1;
                if(current_game.getBoard().getBox(x,y).getPiece() == null){
                    first_click = 0;
                } else {
                    Object source = e.getSource();
                    if(source instanceof JButton){
                        JButton button = (JButton) source;
                        button.setBackground(Color.GREEN);
                    }
                }
            } else if(first_click == 1){
                end_x = x;
                end_y = y;
                first_click = 0;
                JButton selected = game_gui.getButton(start_x, start_y);
                selected.setBackground(new Color(255,255,255));
                int result = current_game.movePiece(start_x,start_y,end_x,end_y);
                switch(result){
                    case Game.MOVE_ERROR_WRONG_MOVE:
                        game_gui.showInvalidMoveError();
                        break;
                    case Game.MOVE_ERROR_WRONG_SIDE:
                        game_gui.showWrongPieceError();
                        break;
                    case Game.GAME_END:
                        updateVisual();
                        System.out.println("Here");
                        game_gui.endGameMessage(current_game.getCurrentPlayer().toString());
                        current_game.restartGame();
                        game_gui.getStartButton().setVisible(true);
                        break;
                }
                updateVisual();  
            }
        }
    }
}
