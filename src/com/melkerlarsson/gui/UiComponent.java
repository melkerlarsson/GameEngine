package com.melkerlarsson.gui;

import org.lwjgl.util.vector.Vector2f;

public class UiComponent {

    private Vector2f position;
    private Vector2f scale;
    private float opacity;
    private UiColor color;

    public UiComponent(Vector2f position, Vector2f scale, UiColor color) {
        this(position, scale, color, 1.0f);
    }

    public UiComponent(Vector2f position, Vector2f scale, UiColor color, float opacity) {
        this.position = position;
        this.scale = scale;
        this.opacity = opacity;
        this.color = color;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getScale() {
        return this.scale;
    }
}
