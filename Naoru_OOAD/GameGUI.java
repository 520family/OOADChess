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
    private int iconWidth, iconHeight;

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

        instruction.add(InstructionBoard()); 
        JScrollPane temp = new JScrollPane(instruction);
        add(temp, BorderLayout.EAST);    

        setVisible(true);
        setSize(1200,600);
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
                    iconWidth = buttons[i][j].getWidth();
                    iconHeight = buttons[i][j].getHeight();
                    buttons[i][j].setIcon(resizeIcon(buttons[i][j].getIcon(), buttons[i][j].getWidth(), buttons[i][j].getHeight()));
                }
            }     
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
            // ignore
    }

    @Override
    public void componentHidden(ComponentEvent e) {
            // ignore
    }
   
    public static Icon resizeIcon(Icon icon, int resizedWidth, int resizedHeight) {
        ImageIcon imgIcon = (ImageIcon) icon;
        Image img = imgIcon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }

    public JButton getStartButton(){
        return start;
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

    public void ShowWrongPieceError(){
        JOptionPane.showMessageDialog(null, "You can't move your enemy piece!");
    }

    public void ShowInvalidMoveError(){
        JOptionPane.showMessageDialog(null, "This is an invalid move for the following piece!");
    }

    public void endGameMessage(String player){
        JOptionPane.showMessageDialog(null, "Player " + player + " win!");
    }

    public JLabel InstructionBoard(){
        ImageIcon i = new ImageIcon("instruction.png");
        JLabel l = new JLabel(i);

        return l;
    }
}