package com.melkerlarsson.gui1;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {

    private int textureID;
    private Vector2f position;
    private Vector2f scale;

    public GuiTexture(int textureID, Vector2f position, Vector2f scale) {
        this.textureID = textureID;
        this.position = position;
        this.scale = scale;
    }

    public int getTextureID() {
        return this.textureID;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getScale() {
        return this.scale;
    }
}
