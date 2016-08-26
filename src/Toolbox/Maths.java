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
	Matrix4f matrix = player.rotationMatrix;
	Matrix4f.rotate((float) Math.toDegrees(player.pendingYaw), player.upVector, matrix, matrix);
	player.pendingYaw = 0;
	changeDirectionVectors(player, matrix);
	Matrix4f.rotate((float) Math.toDegrees(player.pendingPitch), player.rightVecor, matrix, matrix);
	player.pendingPitch = 0;
	changeDirectionVectors(player, matrix);
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

public static Matrix4f createRotationMatrix(Player player){
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
	Matrix4f viewMatrix = new Matrix4f();
	viewMatrix.setIdentity();
	Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
	Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
	Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix);
	Vector3f cameraPosition = camera.getPosition();
	Vector3f negativeCameraPosition = new Vector3f(-cameraPosition.x, -cameraPosition.y,-cameraPosition.z);
	Matrix4f.translate(negativeCameraPosition, viewMatrix, viewMatrix);
	return viewMatrix;
}

public static Matrix4f createNewTransformationMatrix(Player player){
	Matrix4f matrix = new Matrix4f();
	Matrix4f.translate(player.getPosition(), matrix, matrix);
	return matrix;

}

} 
