package main;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Make the windowed mode frame
        JFrame window = new JFrame();
        // Set it so when you close it, it closes the program fully.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Can't change the size so that the fixed camera doesn't have lots of "Black space" around the edge.
        window.setResizable(false);
        //Title is just the window name at the top
        window.setTitle("2D Horror Game Alpha Testing");
        //make a game panel & all the classes it's tied to.
        GamePanel gamePanel = new GamePanel();
        // then add that to the window
        window.add(gamePanel);
        //Makes the window perfect size for the camera settings.
        window.pack();

        // Window options that you always set last
        // Sets the Window to the center of your monitor
        window.setLocationRelativeTo(null);
        // Allow it to be seen
        window.setVisible(true);
        // Game setup, AI, Objects, etc.
        gamePanel.setupGame();
        //Always last, starts the game loop, so events can happen.
        gamePanel.startGameThread();
    }
}