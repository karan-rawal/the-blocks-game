package com.buggy.blocks.utils;

/**
 * Created by karan on 21/1/17.
 */
public interface AndroidInterfaces {
    public void toast(final String message);
    public void initializeAd();
    public void requestNewInterstitial();
    public void showAd();

    //game services
    public void initializeGamesServices();
    public void signIn();
    public void signOut();
    public void submitScore(int score);
    public boolean isSignedIn();
    public void showScore();
    public void submitStoredScore(int highScore);

    //share the game link
    public void shareGameUrl();
}
