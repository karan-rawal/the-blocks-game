package com.buggy.blocks;

import com.badlogic.gdx.Game;
import com.buggy.blocks.screens.SplashScreen;
import com.buggy.blocks.utils.AndroidInterfaces;
import com.buggy.blocks.utils.AudioManager;
import com.buggy.blocks.utils.GameConfig;
import com.buggy.blocks.utils.GameManager;
import com.buggy.blocks.utils.PreferencesManager;

/**
 * Main game class.
 */
public class BuggyGame extends Game {

    public static AndroidInterfaces android = null;

    public BuggyGame() {

    }

    public BuggyGame(AndroidInterfaces androidInterface) {
        android = androidInterface;
        android.initializeAd();
        android.initializeGamesServices();
    }

    public static boolean isSignedIn(){
        return android.isSignedIn();
    }

    public static void signIn(){
        android.signIn();
    }

    public static void signOut(){
        android.signOut();
    }

    public static void showLeaderboard(){
        android.showScore();
    }

    public static void submitScore(int score){
        android.submitScore(score);
    }

    public static void requestNewAd() {
        android.requestNewInterstitial();
    }

    public static void showAd() {
        android.showAd();
    }

    @Override
    public void create() {
        PreferencesManager.initialize();
        AudioManager.initialize();
        GameManager.initialize(this);
    }

    @Override
    public void dispose() {
        GameManager.getTextureAtlas().dispose();
        AudioManager.dispose();
        PreferencesManager.flush();
        super.dispose();
    }
}
