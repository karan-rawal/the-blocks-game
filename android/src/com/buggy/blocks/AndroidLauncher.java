package com.buggy.blocks;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.buggy.blocks.utils.AndroidInterfaces;

public class AndroidLauncher extends AndroidApplication implements AndroidInterfaces {
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
}
