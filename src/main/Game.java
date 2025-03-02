package main;

import entities.Player;

import java.awt.*;
import java.util.Random;

/**
 * The game implementation.
 */
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Player player;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200, 200);
    }

    private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();
    }

    /**
     * Smoother loop for the game
     */
    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;   // frequency of time of update between frames

        long previousTime = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;  // delta updates
        double deltaF = 0;  // delta frames

        while (true) {
            long currentTime = System.nanoTime();

            // deltaU will be 1.0 or more when the duration since last updates >= timePerUpdate
            // Every iteration we gain a certain % of total duration between updates
            // if deltaU is more than 1 then it is more than 100% e.g 1.05 or 105%
            // so -1 0.05 = 5% so we waste no time
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }


            // FPS(Frames per second) and UPS(Updates/ticks per second) count

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }
    }

    public void windowFocusLost() {
        player.resetDirectionBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
