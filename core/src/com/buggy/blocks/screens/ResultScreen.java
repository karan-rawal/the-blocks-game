package com.buggy.blocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.buggy.blocks.BuggyGame;
import com.buggy.blocks.stages.MenuStage;
import com.buggy.blocks.stages.ResultStage;

/**
 * Created by karan on 21/1/17.
 */
public class ResultScreen implements Screen {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "MENU_SCREEN";
    private BuggyGame game = null;

    //the stage for the result screen
    private ResultStage stage;


    /**
     * Instantiates a new Menu screen.
     *
     * @param game the game
     */
    public ResultScreen(BuggyGame game, int score) {
        this.game = game;
        this.stage = new ResultStage(score);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show Called");
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
        Gdx.app.log(LOG_TAG, "Resize Called");
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
        Gdx.app.log(LOG_TAG, "Pause Called");
    }

    @Override
    public void resume() {
        Gdx.app.log(LOG_TAG, "Resume Called");
    }

    @Override
    public void hide() {
        Gdx.app.log(LOG_TAG, "Hide Called");
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Dispose Called");
        stage.dispose();
    }
}
