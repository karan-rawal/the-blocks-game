package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.BoardRectActor;
import com.buggy.blocks.actors.RectActor;
import com.buggy.blocks.actors.Text;
import com.buggy.blocks.utils.AudioManager;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;
import com.buggy.blocks.utils.RectInputListener;
import com.buggy.blocks.utils.RectTexColor;
import com.buggy.blocks.utils.Swipe;
import java.util.Random;

/**
 * Stage for the game screen.
 * Created by karan on 20/12/16.
 */
public class GameStage extends Stage implements RectInputListener, InputProcessor {

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    private OrthographicCamera camera;

    private static int BOARD_COLUMNS = 4; //number of rects in one row
    private static int BOARD_ROWS = 4; //number of rects in one column
    private static int SQUARE_WIDTH = 80; //board rectangle width
    private static int SQUARE_HEIGHT = 80; //board rectangle height

    /**
     * The list of squares/rectangles in the board.
     */
    private BoardRectActor[][] rects;

    /**
     * Represents the bounds of the board rectangle.
     */
    private Rectangle boardBounds;

    private Random random = new Random();
    private Color[] colors;

    private boolean isBusy = false;

    private Text timeLabel;
    private Text timeValue;
    private int time = 5;

    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private Timer.Task gameTimer;

    private int score = 0;
    private Text scoreLabel;

    /**
     * Instantiates a new Game stage.
     */
    public GameStage() {
        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        camera.position.set(GameConfig.GAME_WIDTH / 2, GameConfig.GAME_HEIGHT / 2, 0);
        camera.update();

        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        setViewport(viewport);

        setupColors();
        setupBoard();
    }

    private void setupColors() {
        colors = new Color[]{Color.RED, Color.GREEN};
    }

    private void setupBoard() {
        rects = new BoardRectActor[BOARD_ROWS][BOARD_COLUMNS];

        //setDebugAll(true);

        boardBounds = new Rectangle();
        boardBounds.setPosition(camera.viewportWidth/2, camera.viewportHeight/2);
        boardBounds.setWidth((5f/6) * camera.viewportWidth);
        boardBounds.setHeight(boardBounds.getWidth());

        float squareWidthWithPadding = boardBounds.getWidth() / BOARD_COLUMNS;
        float squareHeightWithPadding = boardBounds.getHeight() / BOARD_ROWS;


        RectActor rect2 = new RectActor(boardBounds.getX(), boardBounds.getY(), boardBounds.getWidth(), boardBounds.getHeight());
        addActor(rect2);

        float initialX = boardBounds.getX() - (squareWidthWithPadding * BOARD_COLUMNS/2) + (squareWidthWithPadding/2);
        float initialY = boardBounds.getY() + (squareHeightWithPadding * BOARD_ROWS/2) - (squareHeightWithPadding/2);

        float positionY;
        for (int i = 0; i < BOARD_ROWS; i++) {
            positionY = initialY - (i * squareHeightWithPadding);
            float positionX;
            for(int j = 0;j < BOARD_COLUMNS;j++){
                positionX = initialX + (j * squareWidthWithPadding);
                int randomNum = random.nextInt(colors.length) + 0;
                BoardRectActor rect = new BoardRectActor(positionX, positionY, SQUARE_WIDTH, SQUARE_HEIGHT, new int[]{i, j}, this, colors[randomNum]);
                addActor(rect);
                rects[i][j] = rect;
            }
        }

        createTimerLabels();
        createScoreLabel();

    }

    private void createScoreLabel() {
        scoreLabel = new Text(score + "", camera.viewportWidth / 2, camera.viewportHeight / 2, 50, Color.BLACK);
        scoreLabel.setY(scoreLabel.getY() - boardBounds.getHeight() / 2 - scoreLabel.getHeight() * 1.5f);

        addActor(scoreLabel);
    }

/*    private void showNotification(String message){
        notifier.setText(message);

        SequenceAction seq = new SequenceAction();

        AlphaAction a1 = new AlphaAction();
        a1.setAlpha(0);
        a1.setDuration(1);
        seq.addAction(a1);

        AlphaAction a2 = new AlphaAction();
        a2.setAlpha(1);
        a2.setDuration(1);
        seq.addAction(a2);

    }*/

    private void createTimerLabels() {

        timeValue = new Text(time + "", camera.viewportWidth / 2, camera.viewportHeight / 2, 50, Color.BLACK);
        timeValue.setY(timeValue.getY() + boardBounds.getHeight() / 2 + timeValue.getHeight() * 1.5f);

        timeLabel = new Text("TIME", camera.viewportWidth / 2, camera.viewportHeight / 2, 80, Color.BLACK);
        timeLabel.setY(timeValue.getY() + timeLabel.getHeight());

        addActor(timeLabel);
        addActor(timeValue);
    }

    @Override
    public void dispose() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                rects[i][j].dispose();
            }
        }
        if (gameTimer.isScheduled()) {
            gameTimer.cancel();
        }
        gameTimer = null;
        super.dispose();
    }

    private ScaleByAction getScaleAction() {
        ScaleByAction action = new ScaleByAction();
        action.setDuration(0.20f);
        action.setAmount(0, -1f);
        action.setReverse(true);
        action.setInterpolation(Interpolation.circleOut);
        return action;
    }

    private MoveByAction getMoveAction() {
        MoveByAction moveAction = new MoveByAction();
        moveAction.setDuration(0.10f);
        moveAction.setInterpolation(Interpolation.circleOut);
        moveAction.setReverse(true);
        return moveAction;
    }

    private MoveToAction getMoveToAction(float x, float y) {
        MoveToAction move = new MoveToAction();
        move.setDuration(0.20f);
        move.setPosition(x, y);
        move.setInterpolation(Interpolation.circleOut);
        return move;
    }

    private RectTexColor getRandomRectTexColor() {
        int randomNum = random.nextInt(colors.length) + 0;
        Texture t = GameManager.getTextureForTheColor(colors[randomNum]);
        RectTexColor color = new RectTexColor(t, colors[randomNum]);
        return color;
    }


    public void animateSwipes(final BoardRectActor actorArg, Swipe swipe) {

        final BoardRectActor swipedActor = actorArg;

        int[] position = swipedActor.getPositionInMatrix();
        int[] nextRectPosition = position.clone();

        float moveAmountX = SQUARE_WIDTH / 4;
        float moveAmountY = SQUARE_HEIGHT / 4;

        float directionX = 0;
        float directionY = 0;

        SequenceAction action = new SequenceAction();

        //in sequence
        MoveByAction moveAction = getMoveAction();

        //actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
        final ScaleByAction flipAction = getScaleAction();

        switch (swipe){
            case UP:
                directionX = 0;
                directionY = 1;
                nextRectPosition[0] -= 1;
                if (position[0] <= 0)
                    directionY = 0;
                break;
            case DOWN:
                directionX = 0;
                directionY = -1;
                nextRectPosition[0] += 1;
                if (position[0] >= BOARD_ROWS - 1)
                    directionY = 0;
                break;
            case LEFT:
                directionX = -1;
                directionY = 0;
                nextRectPosition[1] -= 1;
                if (position[1] <= 0)
                    directionX = 0;
                break;
            case RIGHT:
                directionX = 1;
                directionY = 0;
                nextRectPosition[1] += 1;
                if (position[1] >= BOARD_COLUMNS - 1)
                    directionX = 0;
                break;
            default:
                Gdx.app.error(LOG_TAG, "Invalid Swipe");
        }

        if (directionX == 0 && directionY == 0)
            return;

        final BoardRectActor nextActor = rects[nextRectPosition[0]][nextRectPosition[1]];

        float amtX = moveAmountX * directionX;
        float amtY = moveAmountY * directionY;

        //on move complete

        Action oncomplete = new Action() {
            @Override
            public boolean act(float delta) {
                ScaleByAction flipAction2 = getScaleAction();

                //animate the next actor
                nextActor.addAction(flipAction2);
                actorArg.addAction(flipAction);

                //swap the colors
                RectTexColor temp = nextActor.getTexColor();
                nextActor.setTexColor(swipedActor.getTexColor());
                swipedActor.setTexColor(temp);

                boolean match = checkMatch();
                if (!match && gameEnded) {
                    GameManager.changeScreen(GameManager.RESULT_SCREEN, score);
                }

                return true;
            }
        };

        //animate swiped actor
        moveAction.setAmount(amtX, amtY);
        action.addAction(moveAction);
        action.addAction(oncomplete);
        swipedActor.addAction(action);
    }

    @Override
    public void swipeUp(BoardRectActor actor) {
        if (isBusy)
            return;

        if (!gameStarted)
            startGame();

        AudioManager.playSound(AudioManager.SOUND_FLIP);
        animateSwipes(actor, Swipe.UP);
        Gdx.app.log("SWIPE", "UP " + actor.toString());
    }

    private void startGame() {
        gameStarted = true;
        gameTimer = new Timer.Task() {
            @Override
            public void run() {
                if (time == 0) {
                    gameTimer.cancel();
                    gameEnded = true;
                    AudioManager.playSound(AudioManager.SOUND_BUZZER);
                    if (!isBusy) {
                        GameManager.changeScreen(GameManager.RESULT_SCREEN, score);
                    }
                    return;
                }
                time--;
                timeValue.setText(time + "");
            }
        };
        new Timer().scheduleTask(gameTimer, 0, 1, time);
    }

    @Override
    public void swipeDown(BoardRectActor actor) {
        if (isBusy)
            return;

        if (!gameStarted)
            startGame();

        AudioManager.playSound(AudioManager.SOUND_FLIP);
        animateSwipes(actor, Swipe.DOWN);
        Gdx.app.log("SWIPE", "DOWN " + actor.toString());
    }

    @Override
    public void swipeLeft(BoardRectActor actor) {
        if (isBusy)
            return;

        if (!gameStarted)
            startGame();

        AudioManager.playSound(AudioManager.SOUND_FLIP);
        animateSwipes(actor, Swipe.LEFT);
        Gdx.app.log("SWIPE", "LEFT " + actor.toString());
    }

    @Override
    public void swipeRight(BoardRectActor actor) {
        if (isBusy)
            return;

        if (!gameStarted)
            startGame();

        AudioManager.playSound(AudioManager.SOUND_FLIP);
        animateSwipes(actor, Swipe.RIGHT);
        Gdx.app.log("SWIPE", "RIGHT " + actor.toString());
    }

    public void removeColumn(int colNumber) {
        incrementScore();
        for (int i = 0; i < BOARD_ROWS; i++) {

            final BoardRectActor rect = rects[i][colNumber];

            final float oldX = rect.getX();
            final float oldY = rect.getY();

            //move the the actor above camera.
            MoveToAction move = getMoveToAction(oldX, oldY - camera.viewportHeight);

            //on complete we can change color maybe.
            Action completeAction = new Action() {
                public boolean act(float delta) {
                    rect.setPosition(oldX, oldY + camera.viewportHeight);
                    rect.getTexColor().getTexture().dispose();
                    rect.setTexColor(getRandomRectTexColor());
                    return true;
                }
            };

            //move the actor back to its old position.
            MoveToAction reset = getMoveToAction(oldX, oldY);

            Action onResetComplete = new Action() {
                @Override
                public boolean act(float delta) {
                    if (rect.getPositionInMatrix()[0] == BOARD_ROWS - 1) {
                        isBusy = false;
                        boolean match = checkMatch();
                        if (!match && gameEnded) {
                            GameManager.changeScreen(GameManager.RESULT_SCREEN, score);
                        }
                    }
                    return true;
                }
            };

            SequenceAction action = new SequenceAction();
            action.addAction(move);
            action.addAction(completeAction);
            AudioManager.playSound(AudioManager.SOUND_WOOSH);
            action.addAction(reset);
            action.addAction(onResetComplete);

            rect.addAction(action);
        }
    }

    public void removeRow(int rowNumber) {
        incrementScore();
        for (int i = 0; i < BOARD_COLUMNS; i++) {

            final BoardRectActor rect = rects[rowNumber][i];

            final float oldX = rect.getX();
            final float oldY = rect.getY();


            //move left, out of the camera.
            MoveToAction move = getMoveToAction(oldX - camera.viewportHeight, oldY);

            //when moving is done, set its position above the camera.
            Action completeAction = new Action() {
                public boolean act(float delta) {
                    rect.setPosition(oldX + camera.viewportHeight, oldY);
                    //rect.getTexColor().getTexture().dispose();
                    rect.setTexColor(getRandomRectTexColor());
                    return true;
                }
            };

            //move all rects back to their position
            MoveToAction reset = getMoveToAction(oldX, oldY);

            Action onResetComplete = new Action() {
                @Override
                public boolean act(float delta) {
                    if (rect.getPositionInMatrix()[1] == BOARD_COLUMNS - 1) {
                        isBusy = false;
                        boolean match = checkMatch();
                        if (!match && gameEnded) {
                            GameManager.changeScreen(GameManager.RESULT_SCREEN, score);
                        }
                    }
                    return true;
                }
            };

            SequenceAction action = new SequenceAction();
            action.addAction(move);
            action.addAction(completeAction);
            AudioManager.playSound(AudioManager.SOUND_WOOSH);
            action.addAction(reset);
            action.addAction(onResetComplete);

            rect.addAction(action);
        }
    }

    private void incrementScore() {
        score++;
        scoreLabel.setText(score + "");
    }

    public boolean checkMatch() {
        isBusy = true;
        //to remove row
        Gdx.app.log(LOG_TAG, "check match");
        for (int i = 0; i < BOARD_ROWS; i++) {
            RectTexColor color = rects[i][0].getTexColor();
            for (int j = 1; j < BOARD_COLUMNS; j++) {
                RectTexColor nextColor = rects[i][j].getTexColor();
                if (!color.getColor().equals(nextColor.getColor()))
                    break;

                if (j == BOARD_COLUMNS - 1) {
                    Gdx.app.log(LOG_TAG, "Remove row " + i);
                    removeRow(i);
                    return true;
                }
            }
        }

        //to remove column
        for (int i = 0; i < BOARD_COLUMNS; i++) {
            RectTexColor color = rects[0][i].getTexColor();
            for (int j = 1; j < BOARD_ROWS; j++) {
                RectTexColor nextColor = rects[j][i].getTexColor();
                if (!color.getColor().equals(nextColor.getColor()))
                    break;

                if (j == BOARD_ROWS - 1) {
                    Gdx.app.log(LOG_TAG, "Remove column " + i);
                    removeColumn(i);
                    return true;
                }
            }
        }

        isBusy = false;
        return false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            Gdx.app.log(LOG_TAG, "Back key pressed.");
            GameManager.changeScreen(GameManager.MENU_SCREEN, -1);
            return true;
        }
        return false;
    }
}
