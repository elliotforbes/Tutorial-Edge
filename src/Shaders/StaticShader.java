package Shaders;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/Shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/Shaders/fragmentShader.txt";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void bindAttributes(){
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	
}
