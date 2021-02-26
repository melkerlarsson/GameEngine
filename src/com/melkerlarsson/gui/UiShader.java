package com.melkerlarsson.gui;

import com.melkerlarsson.shaders.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class UiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/com/melkerlarsson/gui/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/com/melkerlarsson/gui/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_color;
    private int location_opacity;

    public UiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }


    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadColor(UiColor color) {
        super.loadVector(location_color, color.getColorAs3dVector());
    }

    public void loadOpacity(float opacity) {
        super.loadFloat(location_opacity, opacity);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_color = super.getUniformLocation("color");
        location_opacity = super.getUniformLocation("opacity");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}

