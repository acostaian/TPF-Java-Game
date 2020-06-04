package ar.edu.utn.mdp.frames;

import ar.edu.utn.mdp.content.Grid;
import ar.edu.utn.mdp.utils.Loader;
import ar.edu.utn.mdp.content.component.*;
import ar.edu.utn.mdp.content.component.Component;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class Game extends JPanel {

    private int width;
    private int height;
    private final int maxFrameRate = 60;
    private Loader loader;
    private boolean running;
    private ComponentCollection<Component> components;

    public Game (int width, int height) {
        this.width = width;
        this.height = height;
        this.loader = new Loader();
        this.components = new ComponentCollection<>();
        init();
    }

    public void init() {
        setSize(width, height);
        setBackground(Color.BLACK);
        setup();
    }

    private void setup() {
        // GRID
        Grid grid = new Grid(0, 0, 50, 10, 10);

        for (ArrayList<Sprite> tiles : grid.getTiles())
            tiles.forEach((Sprite tile)-> tile.setImage(Loader.getSprites().get("pastoFinal")));

        for (int i = 0; i < grid.getTiles().size(); i++) {
            ArrayList<Sprite> tiles = grid.getTiles().get(i);

            for (Sprite tile : tiles)
                components.set(tile);
        }

        //TEXTOS
        components.set(new Text("PlayerNombre", width-width/5, height/6, 0, 80, 40,"PLAYER ONE" ));
        components.set(new Text("combustible", width-width/5, height/4, 0, 80, 40,"COMBUSTIBLE:" ));
        components.set(new Text("NumCombustible", (width-width/5) + 90, (height/4)+20, 0, 80, 40,""));
        components.set(new Text("score", width-width/5,height/2-40, 0, 80, 40,"SCORE:" ));
        components.set(new Text("NumScore", (width-width/5) + 50, (height/2)+20 - 40, 0, 80, 40,"500"));
        components.set(new Text("velocidad", width-width/5, height-height/3, 0, 80, 40,"VELOCIDAD:"));
        components.set(new Text("NumVelocidad", (width-width/5) + 75, (height-height/3)+20, 0, 80, 40,"500"));

        // PLAYER
        components.set(new Car("Car", width/3 - 50/2, height/2 - 50/2, 0, 50, 50, "autoN1", new HitBox("Car", width/3 - 50/2 + 50/4,height/2 - 50/2, 0, 50/2,50)));
        components.set(new Player("Player", width/2 - 50/2, height/2 - 50/2, 0, 50, 50, "autoN1", new HitBox("Player", width/2 - 50/2 + 50/4,height/2 - 50/2, 0, 50/2,50), 0, 1000, 0));
    }


    private void draw() {
        Player player = (Player)components.get("Player");

        HitBox.hitboxCollision(player.getHitBox(), ((Car)components.get("Car")).getHitBox());

        player.move();

        player.editSpeedCollision();

        player.fuelConsumed();
        player.scoreCounter();

        Text textFuel = (Text)components.get("NumCombustible");
        Text textScore = (Text)components.get("NumScore");
        Text textSpeed = (Text)components.get("NumVelocidad");

        textFuel.setTexto(Integer.toString((int)player.getFuel()));
        textScore.setTexto(Integer.toString((int)player.getScore()));
        textSpeed.setTexto(Integer.toString((int)player.getSpeed()));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        try
        {
            g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets\\game_over.ttf")).deriveFont(50f));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        components.drawAll(g);
    }

    public void start() {
        running = true;

        long actualTime = System.nanoTime(); // Tiempo actual
        long nextTime = actualTime + 1000000000 / maxFrameRate; // 1 Seg dividido en N cuadros

        while (running) {
            if (actualTime >= nextTime) {
                draw();
                repaint();
                nextTime = actualTime + 1000000000 / maxFrameRate;
            }
            actualTime = System.nanoTime();
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
