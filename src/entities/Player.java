package entities;

import javax.imageio.spi.IIOServiceProvider;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Toolbox.Maths;
import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity{
	
	private static final float SPEED = 0.1f;
	private static final float TURN_SPEED = 0.8f;
	private static final float TURN_SPEED_ROLL = 10.0f;

	
	public Matrix4f transformationMatrix;
	
	public Vector3f rightVecor;
	public Vector3f faceVector;
	public Vector3f upVector;
	
	public float pendingYaw;
	public float pendingRoll;
	public float pendingPitch;
	public float pendingXTranslation;
	public float pendingYTranslation;
	public float pendingZTranslation;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float sclae) {
		super(model, position, rotX, rotY, rotZ, sclae);
		this.rightVecor = new Vector3f(0,0,-1);
		this.upVector = new Vector3f(0,1,0);
		this.faceVector = new Vector3f(-1,0,0);
		transformationMatrix = Maths.createNewTransformationMatrix(this);
	}
	
	public void move(){
		checkInputs();
		
		faceVector.normalise(faceVector);
		faceVector.scale(SPEED);
		
		pendingXTranslation = faceVector.x;
		pendingYTranslation = faceVector.y;
		pendingZTranslation = faceVector.z;
		
	}
	
	private void checkInputs(){
		
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

	public void updateTransformationMatrix(){
		this.transformationMatrix = Maths.updateTransformationMatrix(this);
	}

	@Override
	public String toString() {
		return "Player [transformationMatrix=" + transformationMatrix + ", rightVecor=" + rightVecor + ", faceVector="
				+ faceVector + ", upVector=" + upVector + ", pendingYaw=" + pendingYaw + ", pendingRoll=" + pendingRoll
				+ ", pendingPitch=" + pendingPitch + ", pendingXTranslation=" + pendingXTranslation
				+ ", pendingYTranslation=" + pendingYTranslation + ", pendingZTranslation=" + pendingZTranslation + "]";
	}
	
	
}
