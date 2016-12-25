package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.BoardRectActor;
import com.buggy.blocks.actors.RectActor;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.RectInputListener;
import com.buggy.blocks.utils.Swipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stage for the game screen.
 * Created by karan on 20/12/16.
 */
public class GameStage extends Stage implements RectInputListener{

    /**
     * The Log tag.
     */
    public String LOG_TAG = "SPLASH_SCREEN";

    private OrthographicCamera camera;

    private static int BOARD_COLUMNS = 4; //number of rects in one row
    private static int BOARD_ROWS = 4; //number of rects in one column
    private static int SQUARE_WIDTH = 70; //board rectangle width
    private static int SQUARE_HEIGHT = 70; //board rectangle height

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
        colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
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
    }

    @Override
    public void dispose() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                rects[i][j].dispose();
            }
        }
        super.dispose();
    }


    public void animateSwipes(BoardRectActor actor, Swipe swipe){
        int[] position = actor.getPositionInMatrix();
        int[] nextRectPosition = position.clone();

        float moveAmountX = SQUARE_WIDTH / 4;
        float moveAmountY = SQUARE_HEIGHT / 4;

        float directionX = 0;
        float directionY = 0;

        SequenceAction action = new SequenceAction();

        //in sequence
        MoveByAction moveAction = new MoveByAction();
        moveAction.setDuration(0.20f);
        moveAction.setInterpolation(Interpolation.fade);
        moveAction.setReverse(true);

        //actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
        ScaleByAction flipAction = new ScaleByAction();
        flipAction.setDuration(0.20f);
        flipAction.setAmount(0, -1f);
        flipAction.setReverse(true);

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

        BoardRectActor nextActor = rects[nextRectPosition[0]][nextRectPosition[1]];

        float amtX = moveAmountX * directionX;
        float amtY = moveAmountY * directionY;

        //animate swiped actor
        moveAction.setAmount(amtX, amtY);
        action.addAction(moveAction);
        action.addAction(flipAction);
        actor.addAction(action);

        ScaleByAction flipAction2 = new ScaleByAction();
        flipAction2.reset();
        flipAction2.setDuration(0.20f);
        flipAction2.setAmount(0, -1f);
        flipAction2.setReverse(true);
        //animate the next actor
        nextActor.addAction(flipAction2);

        Texture temp = nextActor.getTexture();
        nextActor.setTexture(actor.getTexture());
        actor.setTexture(temp);

    }

    @Override
    public void swipeUp(BoardRectActor actor) {
        animateSwipes(actor, Swipe.UP);
        Gdx.app.log("SWIPE", "UP " + actor.toString());
    }

    @Override
    public void swipeDown(BoardRectActor actor) {
        animateSwipes(actor, Swipe.DOWN);
        Gdx.app.log("SWIPE", "DOWN " + actor.toString());
    }

    @Override
    public void swipeLeft(BoardRectActor actor) {
        animateSwipes(actor, Swipe.LEFT);
        Gdx.app.log("SWIPE", "LEFT " + actor.toString());
    }

    @Override
    public void swipeRight(BoardRectActor actor) {
        animateSwipes(actor, Swipe.RIGHT);
        Gdx.app.log("SWIPE", "RIGHT " + actor.toString());
    }
}
