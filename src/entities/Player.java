package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniIndex;
    private int aniSpeed = 15;
    private int playerAction = IDLE;
    private int playDir = -1;
    private boolean left, up, right, down;
    private boolean isMoving = false;
    private boolean isAttacking = false;
    private float playerSpeed = 2.0F;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y,256,160, null);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                isAttacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (isMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (isAttacking) {
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    public void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePosition() {
        isMoving = false;
        if (left && !right) {
            x -= playerSpeed;
            isMoving = true;
        } else if (right && !left) {
            x += playerSpeed;
            isMoving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            isMoving = true;
        } else if(down && !up) {
            y += playerSpeed;
            isMoving = true;
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[9][6];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetDirectionBooleans() {
        left = false;
        up = false;
        down = false;
        right = false;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
