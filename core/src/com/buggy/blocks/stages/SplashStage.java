package com.buggy.blocks.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.Text;
import com.buggy.blocks.utils.GameConfig;

/**
 * The stage for the splash screen.
 * Created by karan on 20/12/16.
 */
public class SplashStage extends Stage {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    private OrthographicCamera camera;

    //title of the splash screen.
    private Text title;


    /**
     * Instantiates a new Splash stage.
     */
    public SplashStage() {
        setDebugAll(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        title = new Text("Color Blocks", GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 80, Color.BLACK);
        addActor(title);
    }

    @Override
    public void dispose() {
        title.dispose();
        super.dispose();
    }
}
