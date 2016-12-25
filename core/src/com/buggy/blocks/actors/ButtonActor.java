package com.buggy.blocks.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Represents a button.
 * Created by karan on 20/12/16.
 */
public class ButtonActor extends Group {
    private Text text;
    private RectActor buttonBorder;
    private Rectangle buttonBounds;

    private final int FONT_SIZE = 50;
    private final Color BUTTON_COLOR = Color.DARK_GRAY;

    /**
     * Instantiates a new Button actor.
     *
     * @param label  the label of the button
     * @param x      the x position of the button
     * @param y      the y position of the button
     * @param width  the width
     * @param height the height
     */
    public ButtonActor(String label, float x, float y, float width, float height) {

        setDebug(true);

        text = new Text(label, width / 2, height / 2, FONT_SIZE, BUTTON_COLOR);
        buttonBorder = new RectActor(width / 2, height / 2, width, height);
        addActor(text);
        addActor(buttonBorder);

        buttonBounds = new Rectangle(buttonBorder.getX(), buttonBorder.getY(), buttonBorder.getWidth(), buttonBorder.getHeight());

        x -= width / 2;
        y -= height / 2;
        setBounds(x, y, width, height);
    }

    /**
     * Disposes the text actor used by the button.
     * Dispose.
     */
    public void dispose() {
        text.dispose();
    }

}
