package game;

import javafx.embed.swing.SwingFXUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    Window window = new Window();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }


    });



    }
}









