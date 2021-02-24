package com.melkerlarsson.gui;

import com.melkerlarsson.models.RawModel;
import com.melkerlarsson.renderEngine.Loader;
import com.melkerlarsson.toolBox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public class UiRenderer {


    private final RawModel quad;
    private UiShader shader;

    public UiRenderer(Loader loader) {
        float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
        quad = loader.loadToVAO(positions);

        shader = new UiShader();

    }

    public void render(List<UiComponent> components) {
        shader.start();

        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (UiComponent component : components) {
            Matrix4f matrix = Maths.createTransformationMatrix(component.getPosition(), component.getScale());
            shader.loadTransformationMatrix(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        shader.stop();
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
