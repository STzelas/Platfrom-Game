package entities;

/**
 * All entities of the game. Player, enemies etc.
 */
public abstract class Entity {
    protected float x;
    protected float y;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
