package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        //Add to the Main.GamePanel
        this.gp = gp;
    }
    public void checkTile (Entity entity){
        /*
            Entities.Entity position in world is calculated, and then there is a small rectangle that's
            in the center of the "tile" that the main character is.
            These integers are just the location of the straight lines of that rectangle.
            Then it works out which tile it's in, in the world.
            Then it does a check against the Tiles.Tile Manager, saying for example, "no,no, thats a tree... You can't park there."

            There is a slight overlap so that it looks like you can walk up to say the bottom of a tree graphically, this will be useful later for chests/items
            because it will look like you're an entire tile away leaving a massive strange gap that will break immersion.
            also like previously mentioned, lets you go through "narrower" gaps.

            If you left it as default you would have to line up perfectly to get through a 1 tile gap,
            which means it looks like the player should be able to get through there,
            but having to get it pixel perfect to pass through, would be boring.
         */

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX /gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

    switch (entity.direction){
        case "up":
             entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
             tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
             tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
                //System.out.println("Collision Detected on: " +tileNum1 + " = " + gp.tileM.tile[tileNum1].collision);
                //System.out.println("Collision Detected on: " +tileNum2 + " = " + gp.tileM.tile[tileNum2].collision);
        }
             break;
        case"down":
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
                //System.out.println("Collision Detected on: " +tileNum1 + " = " + gp.tileM.tile[tileNum1].collision+ " @ " + entity.worldY);
                //System.out.println("Collision Detected on: " +tileNum2 + " = " + gp.tileM.tile[tileNum2].collision + " @ " + entity.worldY);
            }
            break;

        case"right":

            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
                //System.out.println("Collision Detected on: " +tileNum1 + " = " + gp.tileM.tile[tileNum1].collision + " @ " + entity.worldX);
                //System.out.println("Collision Detected on: " +tileNum2 + " = " + gp.tileM.tile[tileNum2].collision + " @ " + entity.worldX);
            }
            break;

        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
                //System.out.println("Collision Detected on: " +tileNum1 + " = " + gp.tileM.tile[tileNum1].collision);
                //System.out.println("Collision Detected on: " +tileNum2 + " = " + gp.tileM.tile[tileNum2].collision);
            }
            break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        //default  = 999 (Represents player)a
        int index = 999;
        for(int i=0;i<gp.obj.length;i++){
            //if theres an item in the array
            if(gp.obj[i] != null){

                //Get the solid position of the object
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the objects area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }if(player == true){
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

}
