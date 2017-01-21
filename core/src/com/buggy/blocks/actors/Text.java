package com.buggy.blocks.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by karan on 17/12/16.
 */
public class Text extends Actor {
    private BitmapFont font;
    private String content;

    /**
     * Instantiates a new Text.
     *
     * @param content  the content
     * @param x        the x position
     * @param y        the y position
     * @param fontSize the font size
     * @param color    the color
     */
    public Text(String content, float x, float y, int fontSize, Color color) {
        this.content = content;

        setColor(color);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Gasalt-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(color);
        generator.dispose();

        GlyphLayout layout = new GlyphLayout(font, content);

        x = x - layout.width / 2;
        y = y - layout.height / 2;

        setBounds(x, y, layout.width, layout.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        color.a = getColor().a * parentAlpha;
        font.setColor(color);
        font.draw(batch, content, this.getX(), this.getY() + getHeight());
    }

    /**
     * Disposes the font used by this class.
     */
    public void dispose() {
        font.dispose();
    }
}
