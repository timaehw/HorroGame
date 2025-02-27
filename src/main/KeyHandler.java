package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    //Flags for if a button is pressed, all default to false.
    public boolean upPressed, rightPressed, downPressed, leftPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //null <UNUSED>
    }

    @Override
    public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    // Pressed a key? switch that shit to true!
        switch (code) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // You let go of a key, so set that shit back to false!
        switch (code) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            default:
                break;
        }
    }
}
