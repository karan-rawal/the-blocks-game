package com.buggy.blocks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by karan on 21/1/17.
 */
public class PreferencesManager {

    public static Preferences prefs;

    public static final int PREF_SCORE = 0;
    public static String KEY_PREF_SCORE = "highscore";

    public static final int PREF_FIRST_LAUNCH = 1;
    public static String KEY_FIRST_LAUNCH = "firstLaunch";

    public static void initialize() {
        prefs = Gdx.app.getPreferences("MyApplication");
    }

    public static int getPreference(int pref) {
        switch (pref) {
            case PREF_SCORE:
                return prefs.getInteger(KEY_PREF_SCORE);
            case PREF_FIRST_LAUNCH:
                return prefs.getInteger(KEY_FIRST_LAUNCH);
        }
        return -1;
    }

    public static void setPreference(int pref, int value) {
        switch (pref) {
            case PREF_SCORE:
                prefs.putInteger(KEY_PREF_SCORE, value);
                prefs.flush();
                break;
            case PREF_FIRST_LAUNCH:
                prefs.putInteger(KEY_FIRST_LAUNCH, value);
                prefs.flush();
                break;
        }
    }

    public static void flush() {
        prefs.flush();
    }


}
