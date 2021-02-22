package com.melkerlarsson.engineTester;

import com.melkerlarsson.entities.Camera;
import com.melkerlarsson.entities.Entity;
import com.melkerlarsson.entities.Light;
import com.melkerlarsson.models.TexturedModel;
import com.melkerlarsson.objConverter.OBJFileLoader;
import com.melkerlarsson.objConverter.ModelData;
import com.melkerlarsson.renderEngine.*;
import com.melkerlarsson.models.RawModel;
import com.melkerlarsson.terrains.Terrain;
import com.melkerlarsson.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();

        ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");
        RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("lowPolyTree"));
        treeTexture.setReflectivity(2);
        TexturedModel tree = new TexturedModel(treeModel, treeTexture);

        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        RawModel grassModel = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
        grassTexture.setHasTransparency(true);
        grassTexture.setUseFakeLighting(true);
        TexturedModel grass = new TexturedModel(grassModel, grassTexture);

        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
        fernTexture.setHasTransparency(true);
        fernTexture.setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(fernModel, fernTexture);




        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f( 1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        float terrainSize = terrain.getSIZE();

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<100;i++){
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * terrainSize - terrainSize/2,0,random.nextFloat() * terrainSize - terrainSize/2),0,0,0,3));
            entities.add(new Entity(tree, new Vector3f(random.nextFloat() * terrainSize - terrainSize/2,0,random.nextFloat() * terrainSize - terrainSize/2),0,0,0,2));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * terrainSize - terrainSize/2,0,random.nextFloat() * terrainSize - terrainSize/2),0,0,0,3));
        }

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            camera.move();

            renderer.processTerrain(terrain);

            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        System.exit(0);
    }
}
