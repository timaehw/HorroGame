package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // Current position
    public int worldX,worldY;
    //Movement Speed (Set elsewhere)
    public int speed;
    //Used for the player character, eventually more for monsters.
    public BufferedImage up1, up2, down1, down2, left1,left2,right1,right2;
    // Which direction is the entity facing?
    public String direction;
    // Walking counter so that it can iterate between strides.
    public int spriteCounter = 0;
    //Default numeration of Sprite.
    public int spriteNum = 1;
    // Collision is set to no for all entities by default, but can be changed.
    public boolean collisionOn = false;
    // Default sizes for collisions
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    //TESTING
    public void setBoundariesCollision(int tileSize) {
    solidArea = new Rectangle();
    solidArea.width = tileSize/2;
    solidArea.height = (int) (tileSize/1.7);
    solidArea.x = tileSize/4;
    solidArea.y = tileSize/3;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    }
}
