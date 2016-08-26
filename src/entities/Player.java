package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Toolbox.Maths;
import models.TexturedModel;

public class Player extends Entity{
	
	private static final float SPEED = 0.1f;
	private static final float TURN_SPEED = 0.00008f;
	private static final float TURN_SPEED_ROLL = 0.00008f;

	
	public Matrix4f translationMatrix;
	public Matrix4f rotationMatrix;
	
	private float currentSpeed = SPEED;
	
	public Vector3f rightVecor;
	public Vector3f faceVector;
	public Vector3f upVector;
	
	public float pendingYaw;
	public float pendingRoll;
	public float pendingPitch;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float sclae) {
		super(model, position, rotX, rotY, rotZ, sclae);
		this.rightVecor = new Vector3f(0,0,-1);
		this.upVector = new Vector3f(0,1,0);
		this.faceVector = new Vector3f(-1,0,0);
		translationMatrix = Maths.createTranslationMatrix(this);
		rotationMatrix = Maths.createRotationMatrix(this);
	}
	
	public void move(){
		checkInputs();
		
		faceVector.normalise(faceVector);
		if(currentSpeed == 0){
			currentSpeed = 0.0000000000001f;
		}
		faceVector.scale(currentSpeed);
		
		
		increasePosition(faceVector.x, faceVector.y, faceVector.z);
		System.out.println(this);
	}
	
	private void checkInputs(){
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
//			this.currentSpeed += SPEED;
//		} else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
//			this.currentSpeed -= SPEED;
//		} else {
//			currentSpeed = 0;
//		}
//		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.pendingYaw = -TURN_SPEED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.pendingYaw = TURN_SPEED;
		} else {
			pendingYaw = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			this.pendingRoll = TURN_SPEED_ROLL;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			this.pendingRoll = -TURN_SPEED_ROLL;
		} else {
			pendingRoll = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.pendingPitch = -TURN_SPEED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.pendingPitch = TURN_SPEED;
		} else {
			pendingPitch = 0;
		}
	}

	public void updateMatrixes(){
		translationMatrix = Maths.updateTranslationMatrix(this);
		rotationMatrix = Maths.updateRotationMatrix(this);
	}

	@Override
	public String toString() {
		return "Player [translationMatrix=" + translationMatrix + ", rotationMatrix=" + rotationMatrix
				+ ", currentSpeed=" + currentSpeed + ", rightVecor=" + rightVecor + ", faceVector=" + faceVector
				+ ", upVector=" + upVector + ", pendingYaw=" + pendingYaw + ", pendingRoll=" + pendingRoll
				+ ", pendingPitch=" + pendingPitch + "]";
	}
	
	

}
