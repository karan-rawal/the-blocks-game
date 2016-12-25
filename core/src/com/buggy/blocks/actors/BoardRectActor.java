package com.buggy.blocks.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Disposable;
import com.buggy.blocks.utils.GameManager;
import com.buggy.blocks.utils.RectInputListener;


/**
 * Represents a square/rectangle in the board.
 * Created by karan on 21/12/16.
 */
public class BoardRectActor extends Actor implements Disposable {

    private int positionInMatrix[];
    private RectInputListener listener;
    private Color rectColor;

    private Texture texture;
    /**
     * Instantiates a new Board rect actor.
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width
     * @param height the height
     * @param point  denotes the position of the rect on the board.
     */
    public BoardRectActor(float x, float y, float width, float height, int point[], final RectInputListener listener, Color rectColor) {
        positionInMatrix = point;
        setDebug(true);
        setBounds(x - (width / 2), y - (height / 2), width, height);
        setOrigin(width / 2, height / 2);
        setColor(rectColor);
        this.rectColor = rectColor;
        this.listener = listener;
        texture = GameManager.getTextureForTheColor(rectColor);
        addListener(new ActorGestureListener() {
            @Override
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if (Math.abs(velocityX) > Math.abs(velocityY)) {
                    if (velocityX > 0) {
                        listener.swipeRight(BoardRectActor.this);
                    } else {
                        listener.swipeLeft(BoardRectActor.this);
                    }
                } else {
                    if (velocityY > 0) {
                        listener.swipeUp(BoardRectActor.this);
                    } else {
                        listener.swipeDown(BoardRectActor.this);
                    }
                }
            }
        });
    }

    public int[] getPositionInMatrix() {
        return positionInMatrix;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public String toString() {
        String name = String.format("Button at [%d, %d]", positionInMatrix[0], positionInMatrix[1]);
        return name;
    }

    public Color getRectColor() {
        return rectColor;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
