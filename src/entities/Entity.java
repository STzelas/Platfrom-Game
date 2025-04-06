package entities;

import java.awt.*;

/**
 * All entities of the game. Player, enemies etc.
 */
public abstract class Entity {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initHitbox();

    }

    protected void drawHitbox(Graphics g) {
        // For debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void initHitbox() {
        hitbox = new Rectangle((int)x, (int)y, width, height);

    }

    protected void updateHitbox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
