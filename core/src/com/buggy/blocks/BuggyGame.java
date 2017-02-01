package com.buggy.blocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    public static final String LOG_TAG = "BuggyGame";

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

    public void submitStoredScore(){

        int firstLaunch = PreferencesManager.getPreference(PreferencesManager.PREF_FIRST_LAUNCH);
        Gdx.app.log(LOG_TAG, PreferencesManager.getPreference(PreferencesManager.PREF_FIRST_LAUNCH) + " First launch");

        if(0 == firstLaunch){
            //PreferencesManager.setPreference(PreferencesManager.PREF_FIRST_LAUNCH, 1);
            int highScore = PreferencesManager.getPreference(PreferencesManager.PREF_SCORE);
            if(0 != highScore){
                Gdx.app.log(LOG_TAG, "Preparing to submit high score.");
                android.submitStoredScore(highScore);
            }
        }
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
