package main;

import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_PFrame;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 27*gp.tileSize;
        gp.obj[0].worldY = 25*gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23*gp.tileSize;
        gp.obj[1].worldY = 25*gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldY= 25* gp.tileSize;
        gp.obj[2].worldX= 20* gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX= 29*gp.tileSize;
        gp.obj[3].worldY= 25* gp.tileSize;

        gp.obj[4] = new OBJ_PFrame();
        gp.obj[4].worldX= 23*gp.tileSize;
        gp.obj[4].worldY= 19*gp.tileSize;


    }
}
