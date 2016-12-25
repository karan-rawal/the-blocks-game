package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.ButtonActor;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;

/**
 * Stage for the menu screen.
 * Created by karan on 20/12/16.
 */
public class MenuStage extends Stage {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "MENU_STAGE";

    private OrthographicCamera camera;

    private ButtonActor playButton;


    /**
     * Instantiates a new Menu stage.
     */
    public MenuStage() {

        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        playButton = new ButtonActor("Play", GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 400, 80);

        addActor(playButton);

        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "TOUCHED!!!");
                GameManager.changeScreen(GameManager.GAME_SCREEN);
                return true;
            }
        });
    }

    @Override
    public void dispose() {
        playButton.dispose();
        super.dispose();
    }
}
