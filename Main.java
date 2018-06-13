package snakerick;

import java.awt.Color;
import javax.swing.JFrame;

public class Main 
{

    public static void main(String[] args) 
    {
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();
        
        obj.setBounds(350, 100, 1200, 750);
        obj.setBackground(Color.DARK_GRAY);
        obj.setTitle("Wąż Rick");
        obj.setResizable(false);
        obj.setVisible(true);
        
        obj.add(gamePlay);
        obj.setDefaultCloseOperation(3);
    }

}


