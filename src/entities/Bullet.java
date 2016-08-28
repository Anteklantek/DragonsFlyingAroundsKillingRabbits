package entities;

import java.awt.datatransfer.FlavorTable;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Bullet extends Entity{
	
	private static final float BULLET_SPEED = 2.3f;
	
	Vector3f faceVector;
	public float distanceTraveled = 0;

	public Bullet(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float sclae,Vector3f playerFaceVector) {
		super(model, position, rotX, rotY, rotZ, sclae);
		this.faceVector = new Vector3f(playerFaceVector.x,playerFaceVector.y,playerFaceVector.z);
		faceVector.normalise();
		faceVector.scale(BULLET_SPEED);
	}
	
	public void move(){
		this.increasePosition(faceVector.x, faceVector.y, faceVector.z);
		this.distanceTraveled += BULLET_SPEED;
	}

}
