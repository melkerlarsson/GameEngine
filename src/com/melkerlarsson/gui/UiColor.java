package com.melkerlarsson.gui;

import org.lwjgl.util.vector.Vector3f;

public class UiColor {

    private float R;
    private float G;
    private float B;

    private Vector3f colorAs3dVector;

    /**
     * Creates a new UiColor.
     *
     * @param R A value between 0 and 255.
     * @param G A value between 0 and 255.
     * @param B A value between 0 and 255.
    */
    public UiColor(float R, float G, float B) {
        this.R = R;
        this.G = G;
        this.B = B;

        this.colorAs3dVector = new Vector3f(this.R/255,this.G/255,this.B/255);
    }

    public Vector3f getColorAs3dVector() {
        return this.colorAs3dVector;
    }
}
