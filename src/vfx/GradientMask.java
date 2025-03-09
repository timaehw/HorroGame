package vfx;
import entity.Player;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GradientMask {

    Player player;
    GamePanel gp;
    BufferedImage radialGradientImage;
    File vfxFile;
    public GradientMask(main.GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
         try {
     			radialGradientImage = ImageIO.read(getClass().getResourceAsStream("/vfx/radialGradient.png"));
     		} catch (IOException e) {
      			e.printStackTrace();
     		}
    }

    public void paintGradientMask(Graphics2D g2){
     
        //Draw Circle
        g2.setColor(Color.black);
        /*Arc2D visibleCircle = new Arc2D() {
            @Override
            public double getAngleStart() {
                return 0;
            }

            @Override
            public double getAngleExtent() {
                return 360;
            }
            @Override
            public void setArc(double x, double y, double w, double h, double angSt, double angExt, int closure) {

            }
            @Override
            public void setAngleStart(double angSt) {
                angSt = 0;
            }
            @Override
            public void setAngleExtent(double angExt) {
                angExt = 360;
            }
            @Override
            protected Rectangle2D makeBounds(double x, double y, double w, double h) {
                x= 0;
                y= 0;
                w = gp.screenWidth;
                h = gp.screenHeight;
                return null;
            }
            @Override
            public double getX() {
                return player.screenX-(gp.tileSize/2)- gp.tileSize;
            }
            @Override
            public double getY() {
                return player.screenY-gp.tileSize;
            }
            @Override
            public double getWidth() {
                return gp.screenWidth/4;
            }
            @Override
            public double getHeight() {
                return gp.screenHeight/4;
            }
            @Override
            public boolean isEmpty() {
                return false;
            }
        };*/
        
        //Alpha filters to gradient between.

        //Darkest
        AlphaComposite ac =
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f);
        //Dark
        AlphaComposite ac2 =
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        //"Normal
        AlphaComposite ac3 =
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0F);

        AlphaComposite ac4 =
        		AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.99F);
        // Gradient Paint is for entire Rectangles.
        //GradientPaint gradientPaint = new GradientPaint(50.0f,50.0f,Color.black,0.3f,0.8f,Color.black);
        //g2.setPaint(gradientPaint);
        //g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        //Set Paint needs to be set here if you're filling an entire rectangle.
        //g2.setPaint();
        //g2.draw(visibleCircle);
        //Select outside of 100% visibility

      //Entire screen gradient
        g2.setComposite(ac4);
        g2.drawImage(radialGradientImage, 0, 0, gp.screenWidth, gp.screenHeight,null);
        

    }

}
