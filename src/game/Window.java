package game;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import javax.swing.*;

public class Window extends JFrame {

    public Window() throws FileNotFoundException {
        super("S.N.A.K.E");
        JPanel panel = new MyPanel();
        addKeyListener((KeyListener) panel);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250,30,750,550);
        add(panel);
        setVisible(true);
    }



}














