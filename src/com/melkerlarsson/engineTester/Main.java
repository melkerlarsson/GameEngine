package com.melkerlarsson.engineTester;

import com.melkerlarsson.entities.Camera;
import com.melkerlarsson.entities.Entity;
import com.melkerlarsson.entities.Light;
import com.melkerlarsson.entities.Player;
import com.melkerlarsson.gui.UiColors;
import com.melkerlarsson.gui.UiComponent;
import com.melkerlarsson.gui.UiRenderer;
import com.melkerlarsson.gui1.GuiRenderer;
import com.melkerlarsson.gui1.GuiTexture;
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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        DisplayManager.createDisplay();

        Loader loader = new Loader();

        ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree3/LowPolyTree");
        RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("lowPolyTree3/Texture2"));
        treeTexture.setShineDamper(5);
        treeTexture.setReflectivity(1.05f);
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

        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        RawModel lampModel = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices());
        ModelTexture lampTexture = new ModelTexture(loader.loadTexture("lamp"));
        TexturedModel lamp = new TexturedModel(lampModel, lampTexture);




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

        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(Terrain.getSIZE() / 2, 40000, Terrain.getSIZE() / 2), new Vector3f( 1.5f, 1.5f, 1.5f)));
        lights.add(new Light(new Vector3f(100, terrain.getHeightOfTerrain(100, -150) + 17, -150), new Vector3f( 2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(100, terrain.getHeightOfTerrain(100, -50) + 17, -50), new Vector3f( 0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(100, terrain.getHeightOfTerrain(100, 150) + 17, 150), new Vector3f( 2, 2, 0), new Vector3f(1f, 0.001f, 0.002f)));


        entities.add(new Entity(lamp, new Vector3f(100, terrain.getHeightOfTerrain(100, -150), -150), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(100, terrain.getHeightOfTerrain(100, -50), -50), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(100, terrain.getHeightOfTerrain(100, 150), 150), 0, 0, 0, 1));

        for(int i=0;i<400;i++){
            float x = random.nextFloat() * terrainSize - terrainSize/2;
            float z = random.nextFloat() * terrainSize - terrainSize/2;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(fern, new Vector3f(x, y, z), 0, 0, 0, 2));
            if (i % 20 == 0) {
                x = random.nextFloat() * terrainSize - terrainSize/2;
                z = random.nextFloat() * terrainSize - terrainSize/2;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, new Vector3f(x, y, z), 0, 0, 0, 2));

                x = random.nextFloat() * terrainSize - terrainSize/2;
                z = random.nextFloat() * terrainSize - terrainSize/2;
                y = terrain.getHeightOfTerrain(x, z);

                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z),0,0,0,2));
            }
            if (i % 5 == 0) {
                x = random.nextFloat() * terrainSize - terrainSize/2;
                z = random.nextFloat() * terrainSize - terrainSize/2;
                y = terrain.getHeightOfTerrain(x, z);
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

        List<UiComponent> guis = new ArrayList<UiComponent>();
        UiComponent gui = new UiComponent(new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f), UiColors.GREEN, 0.5f);
        UiComponent gui2 = new UiComponent(new Vector2f(-0.5f, 0.5f), new Vector2f(0.25f, 0.25f), UiColors.GRAY, 0.95f);

        guis.add(gui);
        guis.add(gui2);

        UiRenderer UiRenderer = new UiRenderer(loader);

        while (!Display.isCloseRequested()) {
            player.move(terrain);
            camera.move();

            renderer.processEntity(player);
            renderer.processTerrain(terrain);

            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(lights, camera);

            //UiRenderer.render(guis);

            DisplayManager.updateDisplay();
        }
        UiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        System.exit(0);
    }
}
