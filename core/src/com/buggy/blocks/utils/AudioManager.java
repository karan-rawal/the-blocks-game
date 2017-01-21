package com.buggy.blocks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.buggy.blocks.BuggyGame;

/**
 * Created by karan on 21/1/17.
 */
public class AudioManager {

    private static final String LOG_TAG = "AudioManager";

    /**
     * The constant SOUND_FLIP.
     */
    public static final int SOUND_FLIP = 1;
    /**
     * The constant SOUND_WOOSH.
     */
    public static final int SOUND_WOOSH = 2;

    private static Sound flipSound;
    private static Sound wooshSound;

    /**
     * Initializes the audios.
     */
    public static void initialize() {
        flipSound = Gdx.audio.newSound(Gdx.files.internal("audio/flip.wav"));
        wooshSound = Gdx.audio.newSound(Gdx.files.internal("audio/woosh.wav"));
    }

    /**
     * Plays the specified sound.
     *
     * @param soundId the sound id
     */
    public static void playSound(int soundId) {
        Gdx.app.log(LOG_TAG, "Playing sound " + soundId);
        switch (soundId) {
            case SOUND_FLIP:
                flipSound.play(0.5f);
                break;
            case SOUND_WOOSH:
                //we dont want flip to be played when whooshing.
                flipSound.stop();
                wooshSound.play(0.1f);
                break;
        }
    }

    /**
     * Disposes the sounds.
     */
    public static void dispose() {
        flipSound.dispose();
        wooshSound.dispose();
    }
}
