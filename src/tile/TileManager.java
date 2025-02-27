package tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

import static javax.imageio.ImageIO.read;

public class TileManager {
    public GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        //Game Panel set to the Game Panel.
        this.gp = gp;
        // Set a tile, add it this array allowing for notepad Maps.
        tile = new Tile[10];
        // Set the grid size of the world.
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        //Get tile Image should be more like set Tiles.Tile Images, so that the numbers in the Notepad coralate to the tile you want
        getTileImage();
        //Load the world map
        loadMap("/maps/startWMap.txt");
    }
    public void getTileImage(){
        try {
            //Obviously a tile for the wall, all walls are now set to 0. Not gonna comment on the rest but basically you just change the number on the .txt file, to the tile you want
            tile[0] = new Tile();
            tile[0].image = read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = read(getClass().getResourceAsStream("/tiles/floor_tile.png"));

            tile[2] = new Tile();
            tile[2].image = read(getClass().getResourceAsStream("/tiles/grass.png"));

            //trees has collision so collision is true, default is false so its not set on the other tiles.
            tile[3] = new Tile();
            tile[3].image = read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = read(getClass().getResourceAsStream("/tiles/sand.png"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            //Find the .txt file to load from
            InputStream is = getClass().getResourceAsStream(filePath);
            //Read the .txt file with a reader.
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //Set everything to 0 by default.
            int col = 0; int row = 0;
            // While the col is lower than the maximum, and the row lower than the maximum
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                //Read the line from the .txt file.
            String line = br.readLine();
            // Now it's being read while the col (column) is lower than the max:
                while(col < gp.maxWorldCol) {
                    // All the tile numbers are seperated by a space, everytime there is a space, it knows to select the next number
                    String numbers[] = line.split(" ");
                    // Read the number, and put it into the render as well as data for collision on / off.
                    int num = Integer.parseInt(numbers[col]);
                    //Use the column + row number in the while loop and set the number of the tile so that the collision is set as well as which image to render
                    mapTileNum[col][row] = num;
                    //Go to the next column.
                    col++;
                }
                // Once all columns have been filled, on the top row
                if (col == gp.maxWorldCol){
                    // go back to the first column
                    col = 0;
                    // then drop down a row, and start filling columns again
                    row++;
                }
                // This will be changed when I get the reader to let me do strange shapes at the moment, since its all a giant square.
            }
            //Stop reading .txt file
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void draw(Graphics2D g2){

        //EXACTLY THE SAME AS ABOVE BUT FOR GRAPHICS. This renders the world one time so when the camera moves to that part of the world only the camera part is rendered but it is stored in memory for later, from this point onwards.
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
             }
        }


    }
}
