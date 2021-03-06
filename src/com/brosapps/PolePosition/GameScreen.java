package com.brosapps.PolePosition;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.brosapps.framework.Game;
import com.brosapps.framework.Graphics;
import com.brosapps.framework.Input.TouchEvent;
import com.brosapps.framework.Screen;

public class GameScreen extends Screen{
	int x = 0;
	int y = 0;
	ArrayList<Enemy1> enemy1s = new ArrayList<Enemy1>();
	
	Enemy1 Enemy1;
	Enemy2 Enemy2;
	
	
	
	
	
	enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;
    
    

    // Variable Setup
    // You would create game objects here.
    
    int livesLeft = 1;
    Paint paint;

    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here
        
        //CREATE BOTH ROWS OF ENEMIES
        //GIVE THEM THEIR X AND Y VALUES
        
        for(int i = 0; i < 5; i++)
        {
        	Enemy1 e = new Enemy1(10, i * 20);
        	enemy1s.add(e);
        }
        
        
        
        
        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        
        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins. 
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!
        
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        //This is identical to the update() method from our Unit 2/3 game.
        
        
        // 1. All touch input is handled here:
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_DOWN) {

                if (event.x < 640) {
                    // Move left.
                	
                	Log.i(null, "MOVING LEFT");
                	x++;
                }
               
                else if (event.x > 640) {
                    // Move Right
                	Log.i(null, "MOVING RIGHT");
                	y++;
                }

            }

            if (event.type == TouchEvent.TOUCH_UP) {

                if (event.x < 640) {
                    
                }

                else if (event.x > 640) {
                    
                }
            }

            
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        
        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();
        
       
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 300 && event.x < 980 && event.y > 100
                        && event.y < 500) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        //draw a black background...always
        g.drawRect(0, 0, 1281, 801, Color.BLACK);

        
        
        
        // First draw the game elements.

        // Example:
        // g.drawImage(Assets.background, 0, 0);
        // g.drawImage(Assets.character, characterX, characterY);
        
        for(Enemy1 e: enemy1s)
        {
        	System.out.println(enemy1s.indexOf(e) + " " + e.getY());
        	g.drawImage(Assets.enemy1, e.getX(), e.getY());
        	
        }
        
        

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap each side of the screen to move in that direction.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        
      //should draw all enemies
        
        

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {
    	state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }

}
