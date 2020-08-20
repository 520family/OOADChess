import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

class GameGUI extends JFrame{

    private JButton [][] buttons;
    private JButton start;
    private JButton save;
    private JButton load;
    private JLabel turn;

	public GameGUI(){
        super("Assignment");
		JPanel p = new JPanel(new FlowLayout((FlowLayout.LEFT)));
        JPanel jp = new JPanel(new GridLayout(8,7));
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
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 7; j++){
                buttons[i][j] = new JButton();
                jp.add(buttons[i][j]);
                buttons[i][j].setBackground(Color.WHITE);
	        }
	    }     

        setVisible(true);
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
}


