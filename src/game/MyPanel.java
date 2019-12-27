package game;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.*;

import static java.awt.Color.red;


public class MyPanel extends JPanel implements ActionListener, KeyListener{

   public Timer timer;
   public Rectangle head;
   public Rectangle food;
   public Random generator;
   public int setHeadX;
   public int setHeadY;
   public int setFoodX;
   public int setFoodY;
   public boolean upDir;
   public boolean downDir;
   public boolean rightDir;
   public boolean leftDir;
   public boolean gameGoOn;
   public int delay;
   public JFrame over;
   public JButton restart;
   public JButton exit;
   public JPanel panel;
   public Container container;
   public ArrayList<Rectangle> snake;
   public int key;
   public int lastUsedKey;
   public boolean lastUsedDirection;
   public JLabel score;


   MyPanel() {
        addKeyListener(this);

        setBackground(Color.black);
        setPreferredSize(new Dimension(750, 550));
        setFocusable(true);
        setLayout(null);
        setVisible(true);
        delay = 200;
        timer = new Timer(delay, this);
        timer.start();
        generator = new Random();
        upDir = false;
        downDir = true;
        rightDir = false;
        leftDir = false;
        gameGoOn = true;
        snake = new ArrayList<>();



        initGame();
        initFood();
        setScore();



    }
    public void initGame() {

       setHeadX = generator.nextInt(600) + 30;
        while(setHeadX % 10 != 0) {
            setHeadX = generator.nextInt(600) + 30;
        }
       setHeadY = generator.nextInt(150) + 30;
        while(setHeadY % 10 != 0) {
            setHeadY = generator.nextInt(150) + 30;
        }
        head = new Rectangle(setHeadX, setHeadY, 10,10);
        snake.add(head);
   }
   public void initFood() {

           setFoodX = generator.nextInt(720);
           while (setFoodX % 10 != 0) {
               setFoodX = generator.nextInt(720);
           }
           setFoodY = generator.nextInt(500);
           while (setFoodY % 10 != 0) {
               setFoodY = generator.nextInt(500);
           }
           food = new Rectangle(setFoodX, setFoodY, 10, 10);
   }

   public void setScore(){
       String value = "0";
       JLabel scoreValue = new JLabel(value);
       JLabel score = new JLabel("Your score");
       scoreValue.setForeground(Color.red);
       scoreValue.setFont(scoreValue.getFont().deriveFont(15.0f));
       score.setFont(score.getFont().deriveFont(15.0f));
       score.setForeground(Color.red);
       score.setBounds(1,-40,100,100);
       scoreValue.setBounds(100, -40, 100,100);
       add(score);
       add(scoreValue);



   }

   public void checkSpeed(){
      for(int n=1; n<snake.size()-1; n++) {
          timer.setDelay(delay-(5*n));
      }
   }

   public void rightDirection() {
       head.x += 10;

   }
   public void leftDirection() {
       head.x -= 10;

   }
   public void upDirection() {
       head.y -= 10;

   }
   public void downDirection() {
       head.y += 10;
   }

   public void move() {

       for(int i=snake.size()-1; i>0; i--){
           snake.get(i).x = snake.get(i-1).x;
           snake.get(i).y = snake.get(i-1).y;

       }

       if (rightDir) {
           rightDirection();
       }
       if(leftDir) {
            leftDirection();
        }
       if (upDir) {
            upDirection();
       }
        if(downDir){
            downDirection();
        }
   }

   public void checkIsEaten() {

        if((head.x == food.x) && (head.y == food.y)) {
            snake.add(food);
        }
    }



    public void checkGameGoOn(){

       for(int i=snake.size()-1; i>0; i--) {
           if((head.x == snake.get(i).x) && (head.y == snake.get(i).y)) {
               gameGoOn = false;
           }
       }

        if(head.x < 0 || head.x>720 || head.y<0 || head.y>500) {
            gameGoOn = false;
        }
    }
    public void checkColisionWithFood() {

        for(int i=snake.size()-1; i>0; i--) {

            while(food.x == snake.get(i).x && food.y == snake.get(i).y){
                initFood();
            }
        }

    }

    public void gameOver() {

       over = new JFrame("Game Over");
       restart = new JButton("Try Again");
       restart.addActionListener(this);
       exit = new JButton("EXIT");
       exit.addActionListener(this);
       over.getContentPane().setBackground(Color.black);
       over.setLayout(null);
       over.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       over.setBounds(400,200,500,200);

       restart.setBounds(0,130,250,30);
       exit.setBounds(250,130,250,30);

        JLabel youLoose = new JLabel("You Loose!");
        youLoose.setFont(getFont().deriveFont(26,30));
        youLoose.setBounds(175,40,200,50);

       panel = new JPanel();
       panel.setBounds(0,0,500,200);
       panel.setBackground(Color.black);
       panel.setLayout(null);


       container = new Container();
       container.setBounds(0,0,500,200);
       container.setLayout(null);
       container.setBackground(Color.black);

       panel.add(youLoose);
       panel.add(restart);
       panel.add(exit);
       container.add(panel);

       over.add(container);

        over.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {

       if(gameGoOn) {
           super.paintComponent(g);
           Graphics2D g2d = (Graphics2D) g;
           g2d.setColor(Color.white);
           g2d.fill(head);
           for (int i=0; i<snake.size(); i++) {
               if(i==0) {
                   g2d.draw(head);
               }
               g2d.draw(snake.get(i));

           }
           Graphics2D gg = (Graphics2D) g;
           gg.setColor(Color.green);
           gg.fill(food);
           gg.draw(food);

           Toolkit.getDefaultToolkit().sync();
       }


       else {
           timer.stop();
           gameOver();

       }
   }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (gameGoOn) {

            checkIsEaten();
            checkSpeed();
            checkColisionWithFood();
            move();
            checkGameGoOn();
            repaint();

        } else {
            Object source = actionEvent.getSource();
            if (source == restart) {
                new Window();

            }
            if (source == exit) {
                System.exit(0);
            }
        }
   }

   public boolean changeDirection() {

       boolean flag = true;

       if((upDir || downDir) && snake.size()>1 && head.y == snake.get(1).y){
            flag = false;
       }
       if((rightDir || leftDir) && snake.size()>1 && head.x == snake.get(1).x){
            flag = false;
       }
        
       return flag;
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {


       key = keyEvent.getKeyCode();

           if (key == KeyEvent.VK_RIGHT && !leftDir) {


               rightDir = changeDirection();
               if(!rightDir && key == KeyEvent.VK_LEFT) {
                   if(lastUsedKey == KeyEvent.VK_DOWN) {
                       downDir = true;
                       leftDir = false;
                       upDir = false;
                   }
               }
               else if(!rightDir && key == KeyEvent.VK_RIGHT) {
                   if(lastUsedKey == KeyEvent.VK_UP) {
                       upDir = true;
                       downDir = false;
                       leftDir = false;
                   }
               }
               else {
                   rightDir = true;
                   upDir = false;
                   downDir = false;
               }


           }
           if (key == KeyEvent.VK_LEFT && !rightDir)  {

               leftDir = changeDirection();
               if(!leftDir && key == KeyEvent.VK_LEFT) {
                   if (lastUsedKey == KeyEvent.VK_DOWN) {
                       downDir = true;
                       rightDir = false;
                       upDir = false;
                   }
               }
               else if(!leftDir && key == KeyEvent.VK_RIGHT) {
                   if(lastUsedKey == KeyEvent.VK_UP) {
                       upDir = true;
                       rightDir = false;
                       downDir = false;
                   }
               }
               else {
                   leftDir = true;
                   upDir = false;
                   downDir = false;
               }
           }

           if (key == KeyEvent.VK_UP && !downDir) {

               upDir = changeDirection();
               if(!upDir && key == KeyEvent.VK_DOWN) {
                   if(lastUsedKey == KeyEvent.VK_RIGHT) {
                       rightDir = true;
                       leftDir = false;
                       downDir = false;
                   }
               }
               else if(!upDir && key == KeyEvent.VK_UP) {
                   if(lastUsedKey == KeyEvent.VK_LEFT) {
                       leftDir = true;
                       rightDir = false;
                       downDir = false;
                   }
               }
               else {
                   upDir = true;
                   rightDir = false;
                   leftDir = false;
               }



           }
           if (key == KeyEvent.VK_DOWN && !upDir) {

               downDir = changeDirection();
               if(!downDir && key == KeyEvent.VK_DOWN) {
                   if(lastUsedKey == KeyEvent.VK_RIGHT) {
                       rightDir = true;
                       leftDir = false;
                       upDir = false;
                   }
               }
               else if(!downDir && key == KeyEvent.VK_UP) {
                   if(lastUsedKey == KeyEvent.VK_LEFT) {
                       leftDir = true;
                       rightDir = false;
                       upDir = false;
                   }
               }
               else {
                   downDir = true;
                   rightDir = false;
                   leftDir = false;
               }
           }


    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {



    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_RIGHT && !leftDir) {

            lastUsedKey = key;
            lastUsedDirection = rightDir;




        }
        if (key == KeyEvent.VK_LEFT && !rightDir)  {

           lastUsedKey = key;
           lastUsedDirection = leftDir;




        }

        if (key == KeyEvent.VK_UP && !downDir) {

           lastUsedKey = key;
           lastUsedDirection = upDir;




        }
        if (key == KeyEvent.VK_DOWN && !upDir) {

           lastUsedKey = key;
           lastUsedDirection = downDir;


        }




    }
}
