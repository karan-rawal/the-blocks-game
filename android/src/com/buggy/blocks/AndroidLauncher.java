package com.buggy.blocks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.buggy.blocks.utils.AndroidInterfaces;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements AndroidInterfaces {

    private static final String LOG_TAG = "AndroidApplication";

    private static final int INTERNET_PERMISSION_REQ_CODE = 1;
    private InterstitialAd iAd;

    //game services
    private GameHelper gameHelper;

    private BuggyGame game;

    private static final int SHOW_LEADERBOARD_REQ_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new BuggyGame((AndroidInterfaces) this);
        initialize(game, config);
    }

    /**
     * This method will be used to show a toast throughout the application.
     *
     * @param message
     */
    @Override
    public void toast(final String message) {
        System.out.println("Showing toast");

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AndroidLauncher.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initializeAd() {
        String iAdId = this.getResources().getString(R.string.iAdId);

        int internetPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        //if no internet permission, then ask for it.
        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    INTERNET_PERMISSION_REQ_CODE);
        }

        iAd = new InterstitialAd(this);
        iAd.setAdUnitId(iAdId);
        iAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                System.out.println("Ad Loaded");
            }
        });
    }

    @Override
    public void requestNewInterstitial() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice("0D0828F6AF7E99E94A92076A51F89388")
                        .build();
                iAd.loadAd(adRequest);
            }
        });
    }

    @Override
    public void showAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (iAd.isLoaded()) {
                    iAd.show();
                }
            }
        });
    }

    @Override
    public void initializeGamesServices() {
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
                Log.d(LOG_TAG, "Sign In Failed");
            }

            @Override
            public void onSignInSucceeded() {
                Log.d(LOG_TAG, "Sign In Succeeded");
                game.submitStoredScore();
            }
        };

        gameHelper.setup(gameHelperListener);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!gameHelper.isSignedIn()) {
                        Log.d(LOG_TAG, "Signing in play games.");
                        gameHelper.beginUserInitiatedSignIn();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(gameHelper.isSignedIn()) {
                        Log.d(LOG_TAG, "Signing out from play games.");
                        gameHelper.signOut();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void submitScore(int score) {
        if (isSignedIn()) {
            Log.d(LOG_TAG, "Submitting score : " + score);
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_time), score);
            AchievementsManager.unlockAchievement(score, gameHelper, this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void showScore()
    {
        if (isSignedIn()) {
            Log.d(LOG_TAG,"Requesting for scores.");
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_time)), SHOW_LEADERBOARD_REQ_CODE);
        }
    }

    @Override
    public void submitStoredScore(final int highScore) {
        Log.d(LOG_TAG, "Attempting to submit stored score.");
        if(isSignedIn()){

            PendingResult result = Games.Leaderboards.loadCurrentPlayerLeaderboardScore(gameHelper.getApiClient(), getString(R.string.leaderboard_time), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC);

            result.setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {

                @Override
                public void onResult(Leaderboards.LoadPlayerScoreResult arg0) {
                    try {
                        LeaderboardScore c = arg0.getScore();
                        Log.d("SCORE = ", c.getRawScore() + "");
                        long score = c.getRawScore();
                        if (score < highScore) {
                            toast("Your local high-score has been updated successfully. :)");
                            submitScore(highScore);
                        }
                    }catch (Exception e){
                        submitScore(highScore);
                        Log.e(LOG_TAG, e.getMessage());
                        toast("Your local high-score has been set on leaderboard. :)");
                    }
                }

            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case INTERNET_PERMISSION_REQ_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(LOG_TAG, "Internet Permission Granted");
                } else {
                    Log.d(LOG_TAG, "Internet Permission Denied");
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }
}
