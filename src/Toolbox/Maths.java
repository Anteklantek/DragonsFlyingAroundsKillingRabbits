package Toolbox;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Player;
import terrains.Terrain;

import java.util.Vector;
import java.util.zip.ZipError;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;

public class Maths {

	
public static Matrix4f createTransformationMatrix(Entity entity){
	Matrix4f matrix = new Matrix4f();
	matrix.setIdentity();
	Matrix4f.translate(entity.getPosition(), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(entity.getRotX()), new Vector3f(1,0,0), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(entity.getRotY()), new Vector3f(0,1,0), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(entity.getRotZ()), new Vector3f(0,0,1), matrix, matrix);
	Matrix4f.scale(new Vector3f(entity.getSclae(),entity.getSclae(),entity.getSclae()), matrix, matrix);
	return matrix;
	}

public static Matrix4f updateTranslationMatrix(Player player){
	Matrix4f matrix = new Matrix4f();
	Matrix4f.translate(player.getPosition(), matrix, matrix);
	return matrix;
	}

public static Matrix4f updateRotationMatrix(Player player){
	Matrix4f matrix = new Matrix4f();
	Matrix4f.rotate((float) Math.toDegrees(player.pendingYaw), player.upVector, matrix, matrix);
	player.pendingYaw = 0;
	Matrix4f.rotate((float) Math.toDegrees(player.pendingPitch), player.rightVecor, matrix, matrix);
	player.pendingPitch = 0;
	Matrix4f.rotate((float) Math.toDegrees(player.pendingRoll), player.faceVector, matrix, matrix);
	player.pendingRoll = 0;
	changeDirectionVectors(player, matrix);
	return matrix;
	}

public static Matrix4f createTranslationMatrix(Player player){
	Matrix4f matrix = new Matrix4f();
	Matrix4f.translate(player.getPosition(), matrix, matrix);
	return matrix;
}

public static Matrix4f createIdentityMatrix(){
	Matrix4f matrix = new Matrix4f();
	return matrix;
}


private static void changeDirectionVectors(Player player, Matrix4f matrix){
	transformVector(player.faceVector, matrix);
	transformVector(player.rightVecor, matrix);
	transformVector(player.upVector, matrix);
}

private static void transformVector(Vector3f vectorForTransformation,Matrix4f transformation){
	if(vectorForTransformation==null)vectorForTransformation=new Vector3f();
	Vector4f vec4=Matrix4f.transform(transformation, new Vector4f((float)vectorForTransformation.x,(float)vectorForTransformation.y,(float)vectorForTransformation.z,1), null);
	vectorForTransformation.x=vec4.x;
	vectorForTransformation.y=vec4.y;
	vectorForTransformation.z=vec4.z;
}

public static Matrix4f createTransformationMatrix(Terrain terrain){
	Matrix4f matrix = new Matrix4f();
	matrix.setIdentity();
	Matrix4f.translate(new Vector3f(terrain.getX(),0,terrain.getZ()), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(1,0,0), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0,1,0), matrix, matrix);
	Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0,0,1), matrix, matrix);
	Matrix4f.scale(new Vector3f(1,1,1), matrix, matrix);
	return matrix;
}


public static Matrix4f createViewMatrix(Camera camera){
	
	Vector3f cameraPos = calculateCameraPos(camera);
	
	return lookAt(cameraPos, camera.player.getPosition(), new Vector3f(0,1,0));
}

private static Vector3f calculateCameraPos(Camera camera) {
	float distanceFromPlayer = 60;
	Vector3f playerPosition = camera.player.getPosition();
	Vector3f playerFaceVector = camera.player.faceVector;
	Vector3f minusFaceVector = new Vector3f(-playerFaceVector.x,-playerFaceVector.y,-playerFaceVector.z);
	minusFaceVector.normalise(minusFaceVector);
	minusFaceVector.scale(distanceFromPlayer);
	Vector3f cameraPosition = new Vector3f(playerPosition.x + minusFaceVector.x,playerPosition.y + minusFaceVector.y + 9,playerPosition.z + minusFaceVector.z);
	return cameraPosition;
}

public static Matrix4f createNewTransformationMatrix(Player player){
	Matrix4f matrix = new Matrix4f();
	Matrix4f.translate(player.getPosition(), matrix, matrix);
	return matrix;

}

public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {

    Vector3f f = Vector3f.sub(center, eye,null).normalise(null);
    Vector3f u = up.normalise(null);
    Vector3f s = Vector3f.cross(f, u, null);
    u = Vector3f.cross(s, f,null);

    Matrix4f result = new Matrix4f();
    result.m00 = s.x;
    result.m10= s.y;
    result.m20= s.z;
    result.m01= u.x;
    result.m11= u.y;
    result.m21= u.z;
    result.m02= -f.x;
    result.m12= -f.y;
    result.m22= -f.z;
        
     result.translate(new Vector3f(-eye.x,-eye.y,-eye.z));
    return result;
}


public static double distanceBeetween(Vector3f start, Vector3f end){
	float x1 = start.x;
	float y1 = start.y;
	float z1 = start.z;
	
	float x2 = end.x;
	float y2 = end.y;
	float z2 = end.z;
	
	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2));
}




} 
