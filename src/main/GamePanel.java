package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
import vfx.GradientMask;

import javax.swing.*;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3734137683213184267L;
	//Set Tiles.Tile Size + Scale, so you can easily just change the size or enable fullscreen
    public final int originalTileSize = 16;
    public final int scale = 3;

    //Tiles.Tile size is then calculated.
    public final int tileSize= originalTileSize*scale;

    // Camera settings
   public final int maxScreenCol = 16;
   public final int maxScreenRow = 12;
   public final int screenWidth = tileSize*maxScreenCol;
   public final int screenHeight = tileSize*maxScreenRow;

   // World Settings:
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 51;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    //Tiles.Tile Manager = World Builder
   TileManager tileM = new TileManager(this);
    // Keyboard Controls
   KeyHandler keyH = new KeyHandler();
   // Game Loop starts
   Thread gameThread;
   // Checker for Entities.Player + AI colliding with Tiles with no pass-through.
   public CollisionChecker collisionChecker = new CollisionChecker(this);
   //Set Assets (Objects)
   public AssetSetter aSetter = new AssetSetter(this);
   // Make the main character, put it on the game panel & ad keyboard control
   public Player player = new Player(this, keyH);
   // 10 objects at the moment due to testing.
   public SuperObject obj[] = new SuperObject[10];

    // Testing goes below
    GradientMask gm = new GradientMask(this, player);

    //diagnostics below again, FPS listed here as it spits to the console for debugging
    int FPS = 60;

   public GamePanel(){
       //Uses tile Size + Scale to draw the correct size window for the camera.
       this.setPreferredSize(new Dimension(screenWidth,screenHeight));
       //Any tiles out of bounds, or undefined will be black
       this.setBackground(Color.black);
       //Render needs to be doubled so you don't get a never ending blur.
       this.setDoubleBuffered(true);
       // Add keys to the window to allow movement controls
       this.addKeyListener(keyH);
       //set Focusable so that keys work
       this.setFocusable(true);
   }

   public void setupGame(){
       aSetter.setObject();
       player.setBoundariesCollision(tileSize);
       
   }
    public void startGameThread(){
       //Starting the thread that runs the game, adds it to the panel for drawing and entity/AI updates.
       gameThread = new Thread(this);
       //start it, duh.
       gameThread.start();
    }
    @Override
    public void run() {
       // One second, divided by Frames Per Second
       double drawInterval = 1000000000/FPS;
       // Delta original number, starts at 0.
       double delta = 0;
       //Set the Time down to the nanosecond
       long lastTime = System.nanoTime();
       // Time passed, set later to calculate when it's been one second.
       long currentTime;
       //Time until the next frame is drawn.
       long timer = 0;
       // Amount of drawn Frames, starting at 0, should go to 60 within 1 second.
       int drawCount = 0;

        //Thread, when called upon, instantly starts.
       while(gameThread != null){
           //Set the time right now using the system clock
           currentTime = System.nanoTime();
           // Delta is then set as a float value, divided by the amount of Frames per second it should be generating
           delta += (currentTime - lastTime) / drawInterval;
           // Timer for next frame to be drawn. (0.0016 or something crazy for 60FPS, double it for 30FPS, etc)
           timer += (currentTime-lastTime);
           //Last time then set again to show that time has passed.
           lastTime = currentTime;

           if (delta >=1 ) {
               //call updates on AI, & other Entities.
               update();
               //Render Graphics
               repaint();
               //reset the delta
               delta--;
               //set the draw count, to prove that FPS Target is hit.
               drawCount++;
           }
           // If Timer is under 1 second, (Long number because it's nano-seconds.
           if(timer >=1000000000){
               // Show FPS in Console for debug.
               System.out.println("FPS: " + drawCount);
               //Reset the draw count to make sure every second, FPS count is hit.
               drawCount=0;
               //Reset the timer so that another second can be counted.
               timer=0;
           }
       }

    }

    public void update(){
    //Updates player position when keys are pressed so the character actually moves.
    player.update();

   }

    public void paintComponent(Graphics g){
       super.paintComponent(g);
        //Set Graphics to Graphics2D for Alphas
        Graphics2D g2 = (Graphics2D) g;
        //Tiles being drawn.
        tileM.draw(g2);
        //Object being drawn (Looped through array)
        for(int i =0;i<obj.length;i++){
            //if theres an object
            if(obj[i] != null){
                //Draw the object with this graphics component, on this game panel.
                obj[i].draw(g2, this);
            }
        }

        //Entities.Player redrawn
        player.draw(g2);
        gm.paintGradientMask(g2);

        //always dispose
        g2.dispose();
   }


}
