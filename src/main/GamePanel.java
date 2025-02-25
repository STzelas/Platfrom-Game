package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniIndex;
    private int aniSpeed = 15;
    private int playerAction = IDLE;
    private int playDir = -1;
    private boolean isMoving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
            }
        }

    }


    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
    }

    public void setDirection(int direction) {
        this.playDir = direction;
        isMoving = true;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }


    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

    private void setAnimation() {

        if (isMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePosition() {
        if (isMoving) {
            switch (playDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;

            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick();

        setAnimation();
        updatePosition();

        g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta,256,160, null);

    }




}
