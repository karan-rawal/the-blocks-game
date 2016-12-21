package com.buggy.blocks.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.Text;
import com.buggy.blocks.utils.GameConfig;

/**
 * Stage for the game screen.
 * Created by karan on 20/12/16.
 */
public class GameStage extends Stage {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    private OrthographicCamera camera;

    //title of the splash screen.
    private Text title;


    /**
     * Instantiates a new Game stage.
     */
    public GameStage() {
        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        title = new Text("Playing now :) Finally.", GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 40, Color.BLACK);
        addActor(title);
    }

    @Override
    public void dispose() {
        title.dispose();
        super.dispose();
    }
}
