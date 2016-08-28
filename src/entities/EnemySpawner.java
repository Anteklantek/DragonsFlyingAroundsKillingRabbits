package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class EnemySpawner {
	
	
	public List<Entity> enemies;
	
	
	
	
	
	
	public EnemySpawner(Loader loader) {
		
		enemies = new ArrayList<>();
		
		ModelData enemyDataData = OBJFileLoader.loadOBJ("bunny");	
		RawModel enemyRawModel = loader.loadToVao(enemyDataData.getVertices(), enemyDataData.getTextureCoords(),
				enemyDataData.getNormals(), enemyDataData.getIndices());
		TexturedModel enemyTexturedModel = new TexturedModel(enemyRawModel, new ModelTexture(loader.loadTexture("blendMap")));
		
		Random random = new Random();
		
		for(int i = 0; i < 10; i++){
			Vector3f position = null;
			if(random.nextBoolean()){
			position = new Vector3f(300 + random.nextFloat()* 100,random.nextFloat() * 40, 300 + random.nextFloat() * 100);
			}else{
			position = new Vector3f(300 + random.nextFloat()* -100,random.nextFloat() * 40, 300 + random.nextFloat() * -100);
			}
			Entity entity = new Entity(enemyTexturedModel, position, 0, 0, 0, 0.3f);
			enemies.add(entity);
		}
		
	}





	public void rotateEnemies(){
		Random random = new Random();
		for(Entity entity : enemies){
			entity.increaseRotation(random.nextFloat()*4, random.nextFloat()*4, random.nextFloat()*4);
		}
	}
	

}
