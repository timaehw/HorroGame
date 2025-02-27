package entity;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
     public int solidDefaultAreaX, solidDefaultAreaY;
    // Set Panel for render of the character, key handler to do movement controls
     public GamePanel gp;
     public KeyHandler keyH;

     // Camera size
     public final int screenX;
     public final int screenY;

     int hasKey = 0;

        public Player(GamePanel gp, KeyHandler keyH){
            //Set "This" in Main.GamePanel + set Main.KeyHandler from Main.GamePanel Class
            this.gp = gp;
            this.keyH = keyH;

            // Set the size of the camera (not set above so that a zoom in and zoom out function can be added later, these are just default for now.
            screenX = gp.screenWidth/2 - (gp.tileSize/2);
            screenY = gp.screenHeight/2 - (gp.tileSize/2);

            //solidArea = new Rectangle();

            // Divided by 12 & 6 here so you can get through narrow areas.
            // Ignore above line, didn't work
            // Set static numbers so the collision detection works ~teehee~
            //solidArea.x = 8;
            //solidArea.y = 16;

            //Testing



            //Set collisions to objects
            //solidDefaultAreaX = solidArea.x;
            //solidDefaultAreaY = solidArea.y;

            // Set it so the entire character isn't blocked, by touching one tiny pixel over the entire tile sized main character.
            //solidArea.width = 32;
            //solidArea.height = 32;

            // Set the values
            setDefaultValues();

            // Get the Graphics for the main character
            getPlayerImage();
        }

    public void getPlayerImage() {
                // FAILED TEST:
               /* File f1 = new File("assets/boy_up_1");
                File f2 = new File("assets/boy_up_2");
                File f3 = new File("assets/boy_down_1");
                File f4 = new File("assets/boy_down_2");
                File f5 = new File("assets/boy_left_1");
                File f6 = new File("assets/boy_left_2");
                File f7 = new File("assets/boy_right_1");
                File f8 = new File("assets/boy_right_2");


                up1 = ImageIO.read(f1);
                up2 = ImageIO.read(f2);
                down1 = ImageIO.read(f3);
                down2 = ImageIO.read(f4);
                right1 = ImageIO.read(f5);
                right2 = ImageIO.read(f6);
                left1 = ImageIO.read(f7);
                left2 = ImageIO.read(f8);*/

            try {
                //Read the files (2 sprites each for a basic walking animation, in all directions, diagonal directions later?
                up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    public void setDefaultValues(){
            //Set X & Y position of main character
            worldX = gp.tileSize*25;
            worldY = gp.tileSize*25;
            // Set Speed of character
            speed = 4;
            //Default direction = Down so he's "Facing the camera"
            direction="down";
        }
        public void update(){
            /*
            Just gonna leave this paragraph here for this whole thing to not fuck up my formatting for this one.
            So, if any key is pressed, work out which key is pressed
            Then change the direction the character is looking by setting a string telling me which way he's looking.
            That then updates the graphics.

            CollisionOn is false because it assumes you're on a tile that you can walk around on
            If you're walking on a tile which has no collision,
            Move the character to the amount of the speed, in that direction.
            SpriteCounter++, changes the graphics so legs move.
            If you change the sprite counter it can look like he's running because it just counts faster.
            Once the legs have moved, they then move back to the default position,
            the way this is set up it means if you stop pressing any WASD key, he goes back to "stood still," graphically.

            Draw Images below, are just the legs moving but updated graphically, instead of the Game Logic.
             */

            if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
                if (keyH.upPressed) {
                    direction = "up";
                }
                if (keyH.downPressed) {
                    direction = "down";
                }
                if (keyH.rightPressed) {
                    direction = "right";
                }
                if (keyH.leftPressed) {
                    direction = "left";
                }

                //collision check
                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                //Checking object collision
                int objIndex = gp.collisionChecker.checkObject(this,true);
                pickUpObject(objIndex);

                /*
                Add Object interaction for world items that get used.
                if(keyH.spacePressed){
                 switch(direction){
                     case: "up":

                         break;
                     case "down":

                         break;
                     case "right":

                         break;
                     case "left":

                         break;
                 }
                }*/
                // false = moving, true = not.
                if (collisionOn == false){
                    switch(direction){
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }

                spriteCounter++;
            }

            if(spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }


        }
        //Picking items up index = identifier
        public void pickUpObject(int i){
            //Allows only the player to pick up items
            if(i != 999){
                //Grab the name of the key so we can check --
             String objName = gp.obj[i].name;
                // if its a key, we have a key, add a key
             switch (objName){
                 case "Key":
                     hasKey++;
                     gp.obj[i] = null;
                     break;
                 case "Door":
                     //open a door, lose a key cannot open if you dont have a key.
                     if(hasKey > 0){
                         gp.obj[i] = null;
                         hasKey--;
                     }
                     break;
             }
            }
        }

        public void draw(Graphics2D g2){
          //  g2.setColor(Color.white);
          //  g2.fillRect(x,y,gp.tileSize,gp.tileSize);
            BufferedImage image = null;
            switch(direction){
                case "up":
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if (spriteNum == 2){
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if (spriteNum == 2){
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if (spriteNum == 2){
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if (spriteNum == 2){
                        image = right2;
                    }
                    break;
            }
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize, null);

            //Testing collisions
            //g2.setColor(Color.blue);
            //g2.fillRect(screenX+solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);

        }
}
