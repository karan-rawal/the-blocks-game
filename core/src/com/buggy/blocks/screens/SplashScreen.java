package com.buggy.blocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
import com.buggy.blocks.BuggyGame;
import com.buggy.blocks.stages.SplashStage;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;

/**
 * The splash screen.
 * Created by karan on 17/12/16.
 */
public class SplashScreen implements Screen {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    //the main game instance
    private BuggyGame game = null;
    private SplashStage stage;

    /**
     * Instantiates a new Splash screen.
     *
     * @param game the game
     */
    public SplashScreen(BuggyGame game) {
        this.game = game;
        this.stage = new SplashStage();
    }

    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show called");
        Gdx.input.setInputProcessor(stage);

        //Schedule a timer to load the gamescreen.
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log(LOG_TAG, "Time passed, now we can create game screen");
                GameManager.changeScreen(GameManager.MENU_SCREEN);
            }
        }, GameConfig.SPLASH_SCREEN_DURATION);
    }

    @Override
    public void render(float delta) {
        //sets the color to be used when clearing the screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        //clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        //Gdx.app.log(LOG_TAG, (1/delta) + " FPS");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(LOG_TAG, "resize called");
        stage.getViewport().update(width, height, false); //without this, touch event does not work
    }

    @Override
    public void pause() {
        Gdx.app.log(LOG_TAG, "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log(LOG_TAG, "resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log(LOG_TAG, "hide called");
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "dispose called");
        stage.dispose();
    }
}
