package com.buggy.blocks.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by karan on 31/3/17.
 */
public class ArrowActor extends Actor{
    private Texture texture;
    private String arrowTexturePath = "images/arrow.png";
    private boolean isFlipped = false;

    public ArrowActor(float x, float y) {
        texture = new Texture(Gdx.files.internal(arrowTexturePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        x = x - texture.getWidth() / 2;
        y = y - texture.getHeight() / 2;
        setBounds(x, y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight(), 0, 0, 32,
        32, false, isFlipped);
    }

    public void setFlipped(boolean isFlipped){
        this.isFlipped = isFlipped;
    }

    public void dispose() {
        texture.dispose();
    }

}
