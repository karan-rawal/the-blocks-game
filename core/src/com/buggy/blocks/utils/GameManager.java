package com.buggy.blocks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.buggy.blocks.BuggyGame;
import com.buggy.blocks.screens.GameScreen;
import com.buggy.blocks.screens.MenuScreen;
import com.buggy.blocks.screens.ResultScreen;
import com.buggy.blocks.screens.SplashScreen;

/**
 * This class manages various aspects of the games such as assets and screens.
 * Created by karan on 17/12/16.
 */
public class GameManager {
    private static BuggyGame game;

    /**
     * The constant LOG_TAG.
     */
    public static String LOG_TAG = "GAME_MANAGER";

    /**
     * The constant GAME_SCREEN.
     */
    public static final int GAME_SCREEN = 0;
    /**
     * The constant MENU_SCREEN.
     */
    public static final int MENU_SCREEN = 1;
    /**
     * The constant SPLASH_SCREEN.
     */
    public static final int SPLASH_SCREEN = 2;

    /**
     * The constant RESULT_SCREEN.
     */
    public static final int RESULT_SCREEN = 3;

    private static TextureAtlas textureAtlas;

    /**
     * Initializes the game, starts the splashscreen.
     *
     * @param game the game
     */
    public static void initialize(BuggyGame game) {
        GameManager.game = game;
        textureAtlas = new TextureAtlas("images/blockpack.atlas");
        GameManager.game.setScreen(new MenuScreen(game));
    }

    /**
     * Changes the game screen.
     *
     * @param screen_no the screen no
     */
    public static void changeScreen(int screen_no, int score) {
        Gdx.app.log(LOG_TAG, "Changing screen.");
        Screen newScreen = null;

        switch (screen_no) {
            case GAME_SCREEN:
                newScreen = new GameScreen(game);
                break;
            case SPLASH_SCREEN:
                newScreen = new SplashScreen(game);
                break;
            case MENU_SCREEN:
                newScreen = new MenuScreen(game);
                break;
            case RESULT_SCREEN:
                newScreen = new ResultScreen(game, score);
                break;
            default:
                Gdx.app.error(LOG_TAG, "Invalid Screen");
                return;
        }

        Screen oldScreen = game.getScreen();
        game.setScreen(newScreen);
        if (oldScreen != null)
            oldScreen.dispose();
    }


    /**
     * Returns the texture atlas.
     *
     * @return the texture atlas
     */
    public static TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public static Texture getTextureForTheColor(Color color)
    {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA4444); // or RGBA8888
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap); // must be manually disposed
        pixmap.dispose();
        return texture;
    }

}
