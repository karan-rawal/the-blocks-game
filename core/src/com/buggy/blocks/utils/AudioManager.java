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

    /**
     * The constant SOUND_BUZZER.
     */
    public static final int SOUND_BUZZER = 3;

    /**
     * The constant SOUND_BUTTON.
     */
    public static final int SOUND_BUTTON = 4;

    private static Sound flipSound;
    private static Sound wooshSound;
    private static Sound buzzerSound;
    private static Sound buttonSound;

    /**
     * Initializes the audios.
     */
    public static void initialize() {
        flipSound = Gdx.audio.newSound(Gdx.files.internal("audio/flip.wav"));
        wooshSound = Gdx.audio.newSound(Gdx.files.internal("audio/woosh.wav"));
        buzzerSound = Gdx.audio.newSound(Gdx.files.internal("audio/buzzer.wav"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("audio/button.wav"));
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
                wooshSound.play(0.3f);
                break;
            case SOUND_BUZZER:
                buzzerSound.play(0.2f);
                break;
            case SOUND_BUTTON:
                buttonSound.play();
                break;
        }
    }

    /**
     * Disposes the sounds.
     */
    public static void dispose() {
        flipSound.dispose();
        wooshSound.dispose();
        buzzerSound.dispose();
        buttonSound.dispose();
    }
}
