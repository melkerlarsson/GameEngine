package com.melkerlarsson.engineTester;

import com.melkerlarsson.entities.Camera;
import com.melkerlarsson.entities.Entity;
import com.melkerlarsson.entities.Light;
import com.melkerlarsson.entities.Player;
import com.melkerlarsson.models.TexturedModel;
import com.melkerlarsson.objConverter.OBJFileLoader;
import com.melkerlarsson.objConverter.ModelData;
import com.melkerlarsson.renderEngine.*;
import com.melkerlarsson.models.RawModel;
import com.melkerlarsson.terrains.Terrain;
import com.melkerlarsson.textures.ModelTexture;
import com.melkerlarsson.textures.TerrainTexture;
import com.melkerlarsson.textures.TerrainTexturePack;
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
        TexturedModel tree = new TexturedModel(treeModel, treeTexture);

        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        RawModel grassModel = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
        grassTexture.setHasTransparency(true);
        grassTexture.setUseFakeLighting(true);
        TexturedModel grass = new TexturedModel(grassModel, grassTexture);

        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernAtlas"));
        fernTextureAtlas.setHasTransparency(true);
        fernTextureAtlas.setUseFakeLighting(true);
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(fernModel, fernTextureAtlas);


        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f( 1f, 1f, 1f));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        float terrainSize = Terrain.getSIZE();

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<400;i++){

            if (i % 20 == 0) {
                float x = random.nextFloat() * terrainSize - terrainSize/2;
                float z = random.nextFloat() * terrainSize - terrainSize/2;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 2));

                x = random.nextFloat() * terrainSize - terrainSize/2;
                z = random.nextFloat() * terrainSize - terrainSize/2;
                y = terrain.getHeightOfTerrain(x, z);

                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z),0,0,0,2));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * terrainSize - terrainSize/2;
                float z = random.nextFloat() * terrainSize - terrainSize/2;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(tree, new Vector3f(x, y, z),0,0,0,3));

            }

        }


        MasterRenderer renderer = new MasterRenderer();

        ModelData playerData = OBJFileLoader.loadOBJ("person");
        RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
        ModelTexture playerTexture = new ModelTexture(loader.loadTexture("playerTexture"));
        TexturedModel texturedPlayer = new TexturedModel(playerModel, playerTexture);

        Player player = new Player(texturedPlayer, new Vector3f(100, 0, -150), 0,0,0,1);
        Camera camera = new Camera(player);

        while (!Display.isCloseRequested()) {
            player.move(terrain);
            camera.move();

            renderer.processEntity(player);
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
