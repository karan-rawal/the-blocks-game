package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.ButtonActor;
import com.buggy.blocks.actors.Text;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;

/**
 * Created by karan on 21/1/17.
 */
public class ResultStage extends Stage {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    private OrthographicCamera camera;

    //score text label
    private Text scoreLabel;
    private Text scoreValueLabel;
    private int score;


    private ButtonActor playButton;
    private ButtonActor mainMenu;


    /**
     * Instantiates a new Splash stage.
     */
    public ResultStage(int score) {

        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        createLabels();
        createButtons();
        addListenersToButtons();
    }

    private void addListenersToButtons() {

        //attach the listeners.
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "Play Button Pressed.");
                GameManager.changeScreen(GameManager.GAME_SCREEN, -1);
                return true;
            }
        });

        mainMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "Options Button Pressed.");
                GameManager.changeScreen(GameManager.MENU_SCREEN, -1);
                return true;
            }
        });
    }

    private void createButtons() {

        float centerX = camera.viewportWidth / 2;
        float centerY = camera.viewportHeight / 2;

        float buttonWidth = camera.viewportWidth / 2.2f;
        float buttonHeight = camera.viewportHeight / 10;

        float buttonPadding = camera.viewportHeight / 30;

        playButton = new ButtonActor("Play Again", centerX, centerY, buttonWidth, buttonHeight);
        mainMenu = new ButtonActor("Main Menu", centerX, centerY - (buttonHeight + buttonPadding), buttonWidth, buttonHeight);

        playButton.setY(playButton.getY() - playButton.getHeight());
        mainMenu.setY(mainMenu.getY() - mainMenu.getHeight());

        addActor(playButton);
        addActor(mainMenu);

    }

    private void createLabels() {
        scoreLabel = new Text("You Scored", GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 80, Color.BLACK);
        scoreLabel.setY(scoreLabel.getY() + scoreLabel.getHeight() * 2.5f);
        addActor(scoreLabel);

        scoreValueLabel = new Text("" + score, GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 80, Color.BLACK);
        scoreValueLabel.setY(scoreValueLabel.getY() + scoreValueLabel.getHeight());
        addActor(scoreValueLabel);
    }

    @Override
    public void dispose() {
        scoreLabel.dispose();
        scoreValueLabel.dispose();
        playButton.dispose();
        mainMenu.dispose();
        super.dispose();
    }
}