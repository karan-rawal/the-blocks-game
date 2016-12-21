package com.buggy.blocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.buggy.blocks.BuggyGame;
import com.buggy.blocks.actors.RectActor;
import com.buggy.blocks.stages.MenuStage;
import com.buggy.blocks.utils.GameConfig;

import org.w3c.dom.css.Rect;

/**
 * The menu screen.
 * Created by karan on 18/12/16.
 */
public class MenuScreen implements Screen {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "MENU_SCREEN";
    private BuggyGame game = null;

    //the stage for the menu screen.
    private MenuStage stage;


    /**
     * Instantiates a new Menu screen.
     *
     * @param game the game
     */
    public MenuScreen(BuggyGame game) {
        this.game = game;
        this.stage = new MenuStage();
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
