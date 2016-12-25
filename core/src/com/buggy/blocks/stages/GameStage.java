package com.buggy.blocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.buggy.blocks.actors.BoardRectActor;
import com.buggy.blocks.actors.RectActor;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.RectInputListener;

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
    private List<BoardRectActor> rects;

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
        rects = new ArrayList<BoardRectActor>();

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
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void swipeUp(BoardRectActor actor) {
        Gdx.app.log("SWIPE", "UP " + actor.toString());
    }

    @Override
    public void swipeDown(BoardRectActor actor) {
        Gdx.app.log("SWIPE", "DOWN " + actor.toString());
    }

    @Override
    public void swipeLeft(BoardRectActor actor) {
        Gdx.app.log("SWIPE", "LEFT " + actor.toString());
    }

    @Override
    public void swipeRight(BoardRectActor actor) {
        Gdx.app.log("SWIPE", "RIGHT " + actor.toString());
    }
}
