package com.buggy.blocks.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.buggy.blocks.utils.GameManager;

/**
 * Represents a rectangle.
 * Created by karan on 18/12/16.
 */
public class RectActor extends Actor {

    private NinePatch button; //** Will Point to button2 (a NinePatch) **//

    /**
     * Instantiates a new Rect actor.
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public RectActor(float x, float y, float width, float height) {

        x = x - width / 2;
        y = y - height / 2;

        setBounds(x, y, width, height);

        button = GameManager.getTextureAtlas().createPatch("button"); //** button1 - not 9 patch **//
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        button.draw(batch, getX(), getY(), getWidth(), getHeight());
    }

}
