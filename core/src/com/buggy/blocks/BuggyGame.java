package com.buggy.blocks;

import com.badlogic.gdx.Game;
import com.buggy.blocks.screens.SplashScreen;
import com.buggy.blocks.utils.AndroidInterfaces;
import com.buggy.blocks.utils.GameManager;

/**
 * Main game class.
 */
public class BuggyGame extends Game {

    public static AndroidInterfaces android = null;

    public BuggyGame() {

    }

    public BuggyGame(AndroidInterfaces androidInterface) {
        android = androidInterface;
    }

    @Override
    public void create() {
        GameManager.initialize(this);
    }

    @Override
    public void dispose() {
        GameManager.getTextureAtlas().dispose();
        super.dispose();
    }
}
