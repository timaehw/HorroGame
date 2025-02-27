package object;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public boolean interactable = false;
    public int worldX,worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

        public void draw(Graphics2D g2, main.GamePanel gp) {

            //get camera location.
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //if in screen bounds, render
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                //Testing collisions
                //g2.setColor(Color.red);
                //g2.fillRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
            }

        }

}
