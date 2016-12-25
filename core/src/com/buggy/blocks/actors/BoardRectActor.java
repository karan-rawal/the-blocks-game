package com.buggy.blocks.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.buggy.blocks.utils.RectInputListener;


/**
 * Represents a square/rectangle in the board.
 * Created by karan on 21/12/16.
 */
public class BoardRectActor extends Actor {

    private ShapeRenderer renderer;
    private int positionInMatrix[];
    private RectInputListener listener;

    /**
     * Instantiates a new Board rect actor.
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width
     * @param height the height
     * @param point  denotes the position of the rect on the board.
     */
    public BoardRectActor(float x, float y, float width, float height, int point[], final RectInputListener listener) {
        positionInMatrix = point;
        renderer = new ShapeRenderer();
        setBounds(x - (width / 2), y - (height / 2), width, height);
        setColor(Color.BLACK);
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
        Color color = getColor();
        color.a = getColor().a * parentAlpha;
        renderer.setColor(color);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
        renderer.end();
    }

    @Override
    public String toString() {
        String name = String.format("Button at [%d, %d]", positionInMatrix[0], positionInMatrix[1]);
        return name;
    }
}
