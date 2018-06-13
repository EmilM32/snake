package snakerick;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private int[] snakeXlength = new int[850];
    private int[] snakeYlength = new int[850];
    
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    
    private boolean szybko = false;
    private boolean normalnie = true;
    private boolean wolno = false;
    
    private boolean pauza = false;
    private boolean shift = false;
    

    private ImageIcon rick;
    
    private int lengthOfSnake = 1;
    
    private Timer timer;
    private int delay = 100;
    
    private ImageIcon rickImage;
    
    private int[] enemyXPos = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1050, 1100, 1150}; //razem 23 liczby
    private int[] enemyYPos = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650}; //razem 13 liczby
        
    private ImageIcon enemyImage;
    
    private Random random = new Random();
    
    private int xpos = random.nextInt(23);
    private int ypos = random.nextInt(13);
    
    private int score = 0;
    
    private int moves = 0;
    
    
    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        System.out.println(shift);
    }
    Image tlo=Toolkit.getDefaultToolkit().createImage("assets" + File.separator + "kosmos.jpg");
    @Override
    public void paint(Graphics g)
    {
        super.paintComponent(g);
        tlo.getScaledInstance(1200, 800, 0);
        g.drawImage(tlo, 0, 0, this);
        
        if(moves == 0)
        {
            snakeXlength[0] = 50;
            snakeYlength[0] = 50;
        }
               
        //rysowanie punktów
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.PLAIN, 14));
        g.drawString("Punkty: " + score, 1080, 30);
        g.drawString("P = PAUZA", 500, 30);
        
        if(szybko)
            g.drawString("Zajebiście szybko!" , 100, 30);
        else if(normalnie)
            g.drawString("Normalna prędkość" , 100, 30);
        else if(wolno)
            g.drawString("W chuj wolno" , 100, 30);
        
        if(pauza)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 30));
            g.drawString("R = WZNÓW GRĘ", 460, 250);
            
            g.setFont(new Font("Verdana", Font.PLAIN, 20));
            g.drawString("W = WOLNO", 500, 300);
            g.drawString("N = NORMALNIE", 500, 330);
            g.drawString("S = SZYBKO", 500, 360);
        }
        

            
        rick = new ImageIcon("assets" + File.separator + "rick.png");
        rick.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        
        for(int i = 0; i < lengthOfSnake; i++)
        {
            if(i == 0 && right)
            {
                 rick = new ImageIcon("assets" + File.separator + "rick.png");
                 rick.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
            
            if(i != 0)
            {
                rickImage = new ImageIcon("assets" + File.separator + "rick2.png");
                rickImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
        }
        
        enemyImage = new ImageIcon("assets" + File.separator + "morty.png");
        
        if(enemyXPos[xpos] == snakeXlength[0] && enemyYPos[ypos] == snakeYlength[0])
        {
            score++;
            lengthOfSnake++;
            xpos = random.nextInt(23);
            ypos = random.nextInt(13);
        }
        
        enemyImage.paintIcon(this, g, enemyXPos[xpos], enemyYPos[ypos]);
        
        for(int j = 1; j < lengthOfSnake; j++) //gdy waż ugryzie sie w ogon
        {
            if(snakeXlength[j] == snakeXlength[0] && snakeYlength[j] == snakeYlength[0])
            {
                right = false;
                left = false;
                down = false;
                up = false;
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Zjebałeś!", 500, 400);
                
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Naciśnij SPACJE aby kontynuować", 460, 440);
            }
        }
        
        
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent ke) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) 
    {
        if(ke.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            if(!shift)
                shift = true;
            else if(shift)
                shift = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_P)
        {
            timer.stop();
            pauza = true;
            repaint();
            System.out.println(pauza);
        }
        if(ke.getKeyCode() == KeyEvent.VK_R)
        {
            timer.start();
            pauza = false;
            repaint();
            System.out.println(pauza);
        }
        if(ke.getKeyCode() == KeyEvent.VK_S)
        {
           timer.stop();
           timer = new Timer(50, this);

           szybko = true;
           wolno = false;
           normalnie = false;

           
           timer.start();
        }
        if(ke.getKeyCode() == KeyEvent.VK_W)
        {
           timer.stop();
           timer = new Timer(200, this);
           
           szybko = false;
           wolno = true;
           normalnie = false;
           
           timer.start();
        }
        if(ke.getKeyCode() == KeyEvent.VK_N)
        {
           timer.stop();
           timer = new Timer(100, this);
           
           szybko = false;
           wolno = false;
           normalnie = true;
           
           timer.start();
        }
        if(ke.getKeyCode() == KeyEvent.VK_SPACE)  //restart gry - spacja
        {
            moves = 0;
            score = 0;
            lengthOfSnake = 1;
            repaint();
        }
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            moves++;
            if(!shift)
            {
                if(!left)
                {
                    right = true;
                }
                else
                {
                    right = false;
                    left = true;
                }

                up = false;
                down = false;
            }
            if(shift)
            {
                if(!left)
                {
                    left = true;
                }
                else
                {
                    right = true;
                    left = false;
                }

                up = false;
                down = false;
            }
            
        }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT)
        {
            moves++;
            if(!shift)
            {
                if(!right)
                {
                    left = true;
                }
                else
                {
                    left = false;
                    right = true;
                }
            }
            if(shift)
            {
                if(!right)
                {
                    right = true;
                }
                else
                {
                    left = true;
                    right = false;
                }
            }

            up = false;
            down = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP)
        {
            moves++;
            if(!shift)
            {
                if(!down)
                {
                    up = true;
                }
                else
                {
                    up = false;
                    down = true;
                }
            }
            if(shift)
            {
                if(!down)
                {
                    down = true;
                }
                else
                {
                    up = true;
                    down = false;
                }
            }

            left = false;
            right = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_DOWN)
        {
            moves++;
            if(!shift)
            {
                if(!up)
                {
                    down = true;
                }
                else
                {
                    down = false;
                    up = true;
                }
            }
            if(shift)
            {
                if(!up)
                {
                    up = true;
                }
                else
                {
                    down = true;
                    up = false;
                }
            }

            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) 
    {
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        timer.start();
        
        if(right)
        {
            for(int r = lengthOfSnake-1; r >= 0; r--)
            {
                snakeYlength[r+1] = snakeYlength[r];
            }
            for(int r = lengthOfSnake; r >=0; r--)
            {
                if(r == 0)
                {
                    snakeXlength[r] += 50;
                }
                else
                {
                    snakeXlength[r] = snakeXlength[r-1];
                }
                if(snakeXlength[r] > 1200)
                {
                    snakeXlength[r] = 0;
                }
            }
            repaint();

        }
        if(left)
        {
            for(int r = lengthOfSnake-1; r >= 0; r--)
            {
                snakeYlength[r+1] = snakeYlength[r];
            }
            for(int r = lengthOfSnake; r >=0; r--)
            {
                if(r == 0)
                {
                    snakeXlength[r] -= 50;
                }
                else
                {
                    snakeXlength[r] = snakeXlength[r-1];
                }
                if(snakeXlength[r] < 0)
                {
                    snakeXlength[r] = 1200;
                }
            }
            repaint();
        }
        if(down)
        {
            for(int r = lengthOfSnake-1; r >= 0; r--)
            {
                snakeXlength[r+1] = snakeXlength[r];
            }
            for(int r = lengthOfSnake; r >=0; r--)
            {
                if(r == 0)
                {
                    snakeYlength[r] += 50;
                }
                else
                {
                    snakeYlength[r] = snakeYlength[r-1];
                }
                if(snakeYlength[r] >= 700)
                {
                    snakeYlength[r] = 0;
                }
            }
            repaint();
        }
        if(up)
        {
            for(int r = lengthOfSnake-1; r >= 0; r--)
            {
                snakeXlength[r+1] = snakeXlength[r];
            }
            for(int r = lengthOfSnake; r >=0; r--)
            {
                if(r == 0)
                {
                    snakeYlength[r] -= 50;
                }
                else
                {
                    snakeYlength[r] = snakeYlength[r-1];
                }
                if(snakeYlength[r] < 0)
                {
                    snakeYlength[r] = 700;
                }
            }
            repaint();
        }
    }

}


