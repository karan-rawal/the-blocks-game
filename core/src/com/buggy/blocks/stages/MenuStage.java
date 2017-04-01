package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.BuggyGame;
import com.buggy.blocks.actors.ButtonActor;
import com.buggy.blocks.actors.ImageButton;
import com.buggy.blocks.utils.AudioManager;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;

/**
 * Stage for the menu screen.
 * Created by karan on 20/12/16.
 */
public class MenuStage extends Stage implements InputProcessor {

    private final MoveToAction rateMoveAction;
    private final MoveToAction optionsMoveAction;
    private final MoveToAction playMoveAction;
    /**
     * The Log tag.
     */
    public String LOG_TAG = "MENU_STAGE";

    private OrthographicCamera camera;

    private ButtonActor playButton;
    private ButtonActor optionsButton;
    private ButtonActor rateButton;

    //denotes if back key was pressed.
    private boolean backAlreadyClicked = false;
    //the task that resets backAlreadyClicked.
    private Timer.Task backButtonTask;

    //image buttons
    private ImageButton leaderboardButton;

    private ImageButton splashImage;

    //share button
    private ImageButton shareButton;


    /**
     * Instantiates a new Menu stage.
     */
    public MenuStage() {

        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        float centerX = camera.viewportWidth / 2;
        float centerY = camera.viewportHeight / 2;

        float buttonWidth = camera.viewportWidth / 2.2f;
        float buttonHeight = camera.viewportHeight / 10;

        float buttonPadding = camera.viewportHeight / 30;

        playButton = new ButtonActor("Play", centerX, centerY, buttonWidth, buttonHeight);
        optionsButton = new ButtonActor("Review", centerX, centerY - (buttonHeight + buttonPadding), buttonWidth, buttonHeight);
        rateButton = new ButtonActor("Rate", centerX, centerY - (buttonHeight + buttonPadding) * 2, buttonWidth, buttonHeight);

        //store the old x and y values of the buttons.
        float playOldX = playButton.getX();
        float playOldY = playButton.getY();

        float optionsOldX = optionsButton.getX();
        float optionsOldY = optionsButton.getY();

        float rateOldX = rateButton.getX();
        float rateOldY = rateButton.getY();

        //set the x and y values of the buttons to somewhere below the camera.
        playButton.setY(-camera.viewportHeight);
        optionsButton.setY(-(camera.viewportHeight + optionsButton.getHeight()));
        rateButton.setY(-(camera.viewportHeight + rateButton.getHeight() + optionsButton.getHeight()));

        //get the animations.
        playMoveAction = getMoveAction(playOldX, playOldY);
        optionsMoveAction = getMoveAction(optionsOldX, optionsOldY);
        rateMoveAction = getMoveAction(rateOldX, rateOldY);

        //attach the listeners.
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "Play Button Pressed.");
                AudioManager.playSound(AudioManager.SOUND_BUTTON);
                BuggyGame.requestNewAd();
                GameManager.changeScreen(GameManager.GAME_SCREEN, -1);
                return true;
            }
        });

        optionsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "Rate Button Pressed.");
                AudioManager.playSound(AudioManager.SOUND_BUTTON);
                Gdx.net.openURI(GameConfig.PLAY_STORE_URL);
                return true;
            }
        });

        rateButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(LOG_TAG, "Rate Button Pressed.");
                AudioManager.playSound(AudioManager.SOUND_BUTTON);
                GameManager.changeScreen(GameManager.GAME_SCREEN, -1);
                return true;
            }
        });

        //add the actors.
        addActor(playButton);
        addActor(optionsButton);
        //addActor(rateButton);


        shareButton = new ImageButton("images/share.png", camera.viewportWidth / 2, optionsOldY - optionsButton.getHeight());
        shareButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(AudioManager.SOUND_BUTTON);
                BuggyGame.shareGameUrl();
                return true;
            }
        });

        float halfGapBetweenButtons = 10;
        float actualGap = shareButton.getWidth()/2 + halfGapBetweenButtons;

        if (BuggyGame.isSignedIn()) {
            //ImageButtons
            leaderboardButton = new ImageButton("images/leaderboard.png", camera.viewportWidth / 2, optionsOldY);
            leaderboardButton.setY(leaderboardButton.getY() - optionsButton.getHeight());
            leaderboardButton.setX(leaderboardButton.getX() + actualGap);
            shareButton.setX(shareButton.getX() - actualGap);

            addActor(leaderboardButton);

            leaderboardButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(LOG_TAG, "Leaderboard pressed");
                    AudioManager.playSound(AudioManager.SOUND_BUTTON);
                    BuggyGame.showLeaderboard();
                    return true;
                }
            });
        }

        //splash image icon
        float splashImageMarginTop = 200;

        splashImage = new ImageButton("images/splashIcon.png", camera.viewportWidth / 2, camera.viewportHeight / 2 + splashImageMarginTop);
        addActor(splashImage);
        addActor(shareButton);
    }

    /**
     * Make actors act.
     */
    public void makeActorsAct() {
        addActionToButtons(playMoveAction, optionsMoveAction, rateMoveAction);
    }


    /**
     * adds action to buttons.
     *
     * @param action1
     * @param action2
     * @param action3
     */
    private void addActionToButtons(final MoveToAction action1, final MoveToAction action2, final MoveToAction action3) {
        playButton.addAction(getAnimationSequence(action1, 0.2f));
        optionsButton.addAction(getAnimationSequence(action2, 0.4f));
        rateButton.addAction(getAnimationSequence(action3, 0.6f));
    }

    /**
     * Creates a sequence of the action with a delay before it.
     *
     * @param action
     * @param delay
     * @return
     */
    private SequenceAction getAnimationSequence(MoveToAction action, float delay) {
        SequenceAction sequence = new SequenceAction();
        sequence.addAction(new DelayAction(delay));
        sequence.addAction(action);
        return sequence;
    }

    /**
     * returns a move to action.
     *
     * @param toX
     * @param toY
     * @return
     */
    private MoveToAction getMoveAction(float toX, float toY) {
        MoveToAction action = new MoveToAction();
        action.setDuration(0.5f);
        action.setInterpolation(Interpolation.circleOut);
        action.setPosition(toX, toY);
        return action;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            Gdx.app.log(LOG_TAG, "Back key pressed.");

            //if back was already pressed, exit the application.
            if (backAlreadyClicked) {
                BuggyGame.android.toast("Thank you for trying my game :)");
                Gdx.app.exit();
                this.dispose();
                //cancel the task if it is not null.
                if (null != backButtonTask) {
                    backButtonTask.cancel();
                }
            } else {
                //if back was not pressed, make the backAlreadyClicked = true.
                backAlreadyClicked = true;
                BuggyGame.android.toast("Press back again to quit :(");

                //initialize the task
                backButtonTask = new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.log(LOG_TAG, "Back button was not pressed in time. Making backAlreadyClicked = false");
                        backAlreadyClicked = false;
                        backButtonTask = null;
                    }
                };

                //schedule the task.
                Timer.schedule(backButtonTask, 2);
            }
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        playButton.dispose();
        optionsButton.dispose();
        rateButton.dispose();
        splashImage.dispose();
    }
}
