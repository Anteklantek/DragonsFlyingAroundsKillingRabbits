package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class BulletSpawner {

	private Loader loader;
	private Player player;
	private List<Bullet> bullets;
	private ModelData bulletData;
	private RawModel bulletRawModel;
	private TexturedModel bulletTexturedModel;
	int frameDelayer = 0;
	
	
	
	public BulletSpawner(Loader loader, Player player) {
		this.player = player;
		this.bullets = new ArrayList<>();
		bulletData = OBJFileLoader.loadOBJ("dragon");
		bulletRawModel = loader.loadToVao(bulletData.getVertices(), bulletData.getTextureCoords(),
				bulletData.getNormals(), bulletData.getIndices());
		bulletTexturedModel = new TexturedModel(bulletRawModel, new ModelTexture(loader.loadTexture("texture")));
	}
	
	public void work() {
		detectAndSpawnBullet();
		moveBullets();
	}
	
	
	
	public List<Bullet> getBullets() {
		return bullets;
	}

	private void detectAndSpawnBullet(){
		if(frameDelayer>0){
			frameDelayer--;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			bullets.add(createNewBullet());
			frameDelayer = 50;
		}
	}

	private Bullet createNewBullet() {
		Vector3f position  = new Vector3f(player.getPosition().x,player.getPosition().y,player.getPosition().z);
		Bullet bullet = new Bullet(bulletTexturedModel, position, 0, 0, 0, 0.2f,player.faceVector);
		return bullet;
	}
	
	public void moveBullets(){
		for(Bullet bullet : bullets){
			bullet.move();
		}
		System.out.println("bullets size: " + bullets.size());
	}

	public void removeTooFarBullets(){
		for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
		    Bullet bullet = iterator.next();
		    if (bullet.distanceTraveled > 200) {
		        iterator.remove();
		    }
		}
	}


}

