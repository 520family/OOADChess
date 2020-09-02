import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

class GameGUI extends JFrame implements ComponentListener{

    private JButton [][] buttons;
    private JButton start;
    private JButton save;
    private JButton load;
    private JLabel turn;

	public GameGUI(){
        super("Webale Chess");
    
        JPanel p = new JPanel(new FlowLayout((FlowLayout.LEFT)));
        JPanel jp = new JPanel(new GridLayout(8,7));
        JPanel instruction = new JPanel();
        buttons = new JButton [8][7];
        start = new JButton("Start");
        save = new JButton("Save");
        load = new JButton("Load");
        turn = new JLabel();
        p.add(start,  BorderLayout.WEST);
        p.add(save, BorderLayout.WEST);
        p.add(load, BorderLayout.WEST);
        p.add(turn, BorderLayout.WEST);
        add(p, BorderLayout.NORTH);
        add(jp, BorderLayout.CENTER);
        this.addComponentListener(this);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 7; j++){
                buttons[i][j] = new JButton();
                jp.add(buttons[i][j]);
                buttons[i][j].setBackground(Color.WHITE);
            }
        }

        instruction.add(showInstructionBoard()); 
        JScrollPane temp = new JScrollPane(instruction);
        add(temp, BorderLayout.EAST);    

        setVisible(true);
        setSize(1200,600);
        setMinimumSize(new Dimension(1000,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void componentShown(ComponentEvent e) {
            // ignore
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Object source = e.getSource();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 7; j++){
                if (buttons[i][j].getIcon()!=null){
                    buttons[i][j].setIcon(resizeIcon(buttons[i][j].getIcon(), buttons[i][j].getWidth(), buttons[i][j].getHeight()));
                }
            }     
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    public JButton getStartButton(){
        return start;
    }

    public JButton getButton(int x, int y){
        return buttons[y][x];
    }

    public JButton[][] getAllButtons(){
        return buttons;
    }
    
    public JButton getSaveButton(){
        return save;
    }

    public JButton getLoadButton(){
        return load;
    }

    public JLabel getTurnLabel(){
        return turn;
    }

    public void showWrongPieceError(){
        JOptionPane.showMessageDialog(null, "You can't move your enemy piece!");
    }

    public void showInvalidMoveError(){
        JOptionPane.showMessageDialog(null, "This is an invalid move for the following piece!");
    }

    public void endGameMessage(String player){
        JOptionPane.showMessageDialog(null, "Player " + player + " win!");
    }

    public void showSaveCompleteMessage(){
        JOptionPane.showMessageDialog(null,"Save Completed.");
    }

    public void showLoadCompleteMessage(){
        JOptionPane.showMessageDialog(null,"Load Successfully.");
    }
    public void showLoadErrorMessage(){
        JOptionPane.showMessageDialog(null, "Load failed. No save file found.");
    }    
    public void showSaveErrorMessage(){
        JOptionPane.showMessageDialog(null, "Save failed.");
    }

    public JLabel showInstructionBoard(){
        ImageIcon i = new ImageIcon("instruction.png");
        JLabel l = new JLabel(i);

        return l;
    }

    public Icon resizeIcon(Icon icon, int resizedWidth, int resizedHeight) {
        ImageIcon imgIcon = (ImageIcon) icon;
        Image img = imgIcon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }

    public ImageIcon loadImage(String paramString) {
        Image image1 = (new ImageIcon(getClass().getResource(paramString))).getImage();
        Image image2 = image1.getScaledInstance(62, 62, 4);
        return new ImageIcon(image2);
    }
}