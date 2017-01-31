package com.buggy.blocks.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by karan on 22/1/17.
 */
public class ImageButton extends Actor {
    Texture texture;

    public ImageButton(String texturePath, float x, float y) {
        texture = new Texture(Gdx.files.internal(texturePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        x = x - texture.getWidth() / 2;
        y = y - texture.getHeight() / 2;

        setBounds(x, y, texture.getWidth(), texture.getHeight());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    /**
     * Disposes the text actor used by the button.
     * Dispose.
     */
    public void dispose() {
        texture.dispose();
    }
}
