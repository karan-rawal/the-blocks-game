package com.buggy.blocks.utils;

import com.buggy.blocks.actors.BoardRectActor;

/**
 * Created by karan on 25/12/16.
 */
public interface RectInputListener {
    /**
     * Called when a boardRectActor is swiped up.
     *
     * @param actor the actor
     */
    public void swipeUp(BoardRectActor actor);

    /**
     * Called when a boardRectActor is swiped down.
     *
     * @param actor the actor
     */
    public void swipeDown(BoardRectActor actor);

    /**
     * Called when a boardRectActor is swiped left.
     *
     * @param actor the actor
     */
    public void swipeLeft(BoardRectActor actor);

    /**
     * Called when a boardRectActor is swiped right.
     *
     * @param actor the actor
     */
    public void swipeRight(BoardRectActor actor);
}
