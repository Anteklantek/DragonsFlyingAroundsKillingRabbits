package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Toolbox.Maths;
import entities.Camera;
import entities.Light;
import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPositon;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	
	public TerrainShader() {
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
		location_skyColor = super.getUnifromLocation("skyColor");
		location_backgroundTexture = super.getUnifromLocation("backgroundTexture");
		location_rTexture = super.getUnifromLocation("rTexture");
		location_gTexture = super.getUnifromLocation("gTexture");
		location_bTexture = super.getUnifromLocation("bTexture");
		location_blendMap = super.getUnifromLocation("blendMap");
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
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
	}
}
