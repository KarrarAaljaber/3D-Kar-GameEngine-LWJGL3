package shaders;

public class StaticShader extends Shader{

	private static final String VERTEX_SHADER = "src/shaders/vertexShader.txt", 
			FRAGMENT_SHADER = "src/shaders/fragmentShader.txt"; 
	
	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(2, "textureCoords");
		
	}

}
