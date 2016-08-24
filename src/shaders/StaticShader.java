package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Toolbox.Maths;
import entities.Camera;
import entities.Light;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPositon;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLightning;
	private int location_skyColor;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes(){
		super.bindAttribute(0,"position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocation() {
		location_transformationMatrix = super.getUnifromLocation("transformationMatrix");
		location_projectionMatrix = super.getUnifromLocation("projectionMatrix");
		location_viewMatrix = super.getUnifromLocation("viewMatrix");
		location_lightPositon = super.getUnifromLocation("lightPosition");
		location_lightColour = super.getUnifromLocation("lightColour");
		location_shineDamper = super.getUnifromLocation("shineDamper");
		location_reflectivity = super.getUnifromLocation("reflectivity");
		location_useFakeLightning = super.getUnifromLocation("useFakeLightning");
		location_skyColor = super.getUnifromLocation("skyColor");
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadLight(Light light){
		super.loadVector(location_lightPositon, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}

	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadUseFakeLightning(boolean useFakeLightning){
		super.loadBoolean(location_useFakeLightning, useFakeLightning);
	}
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
}
