package com.buggy.blocks;

import android.Manifest;
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

public class AndroidLauncher extends AndroidApplication implements AndroidInterfaces {

    private static final String LOG_TAG = "AndroidApplication";

    private static final int INTERNET_PERMISSION_REQ_CODE = 1;
    private InterstitialAd iAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new BuggyGame((AndroidInterfaces) this), config);
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
}
