package ar.edu.utn.mdp;

import javax.swing.*;

public class Application {

    private int width = 800;
    private int height = 600;

    //Game game;
    JFrame frame;

    public Application () {
        frame = new JFrame();
    }

    public void init() {
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //frame.getContentPane().add(game);
        //frame.addKeyListener(input);
        //frame.addMouseListener(input);

        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight() {
        this.setHeight();
    }
}
