package engineTester;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.crypto.Data;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import objConverter.OBJFileLoader;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		
		ModelData stallData  = OBJFileLoader.loadOBJ("stall");
		RawModel stall = loader.loadToVao(stallData.getVertices(), stallData.getTextureCoords(),
				stallData.getNormals(), stallData.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		texture.setReflectivity(0.02f);
		texture.setShineDamper(20);
		TexturedModel texturedStall = new TexturedModel(stall, texture);
		
		ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
		
		RawModel grassModel = loader.loadToVao(grassData.getVertices(), grassData.getTextureCoords(),
				grassData.getNormals(), grassData.getIndices());

		
		TexturedModel grassTexturedModel = new TexturedModel(grassModel,
				new ModelTexture(loader.loadTexture("grassModelTexture")));
		
		grassTexturedModel.getTexture().setHasTransparency(true);
		grassTexturedModel.getTexture().setUseFakeLightning(true);
		
		Entity stallEntity = new Entity(texturedStall, new Vector3f(20,0,30), 0,0,0,1);
		
		List<Entity> entities = new ArrayList<>();
		
		entities.add(stallEntity);
		
		Random random = new Random();
		
		for(int i = 0; i < 500; i++){
			float x = random.nextFloat() > 0.5 ? 300 + random.nextFloat() * 300 : 300 + random.nextFloat() * -300;
			float y = random.nextFloat() > 0.5 ? 300 + random.nextFloat() * 300 : 300 + random.nextFloat() * -300;
			entities.add(new Entity(grassTexturedModel, new Vector3f(x ,0,y),0, 0, 0, random.nextFloat() * 5));
		}
		
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("background"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("star"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("lightning"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("dirt"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
		
		
		
		ModelData playerData = OBJFileLoader.loadOBJ("dragon");
		
		RawModel playerRawModel = loader.loadToVao(playerData.getVertices(), playerData.getTextureCoords(),
				playerData.getNormals(), playerData.getIndices());
		
		TexturedModel playerTexturedModel = new TexturedModel(playerRawModel, new ModelTexture(loader.loadTexture("texture")));
		
		Player player = new Player(playerTexturedModel, new Vector3f(300,2,300), 0, 0, 0, 1);
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			camera.move();
			player.move();
			renderer.processEntity(player);
			for(Entity entity : entities){
				renderer.processEntity(entity);
			}
			
			renderer.processTerrain(terrain);
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
	
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
