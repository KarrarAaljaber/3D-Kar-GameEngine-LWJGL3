package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public abstract class Shader {

	private int vertexShaderID, fragmentShaderID, programID;
	private String VertexFile, FragmentFile;
	
	public Shader(String VertexFile, String FragmentFile) {
		this.VertexFile = VertexFile;
		this.FragmentFile = FragmentFile;
	}
	
	
	public void create() {
		programID = GL30.glCreateProgram();
		
		if(programID == MemoryUtil.NULL) {
			System.err.println("Couldnt create program");
			System.exit(-1);
		}
		
		vertexShaderID = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
		GL30.glShaderSource(vertexShaderID, readFile(VertexFile));
		GL30.glCompileShader(vertexShaderID);
		
		if(GL30.glGetShaderi(vertexShaderID, GL30.GL_COMPILE_STATUS) == GLFW.GLFW_FALSE) {
			System.err.println("Couldnt compile VertexShader!" + GL30.glGetShaderInfoLog(vertexShaderID));
			System.exit(-1);
		}
		
		fragmentShaderID = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
		GL30.glShaderSource(fragmentShaderID, readFile(FragmentFile));
		GL30.glCompileShader(fragmentShaderID);
		
		if(GL30.glGetShaderi(fragmentShaderID, GL30.GL_COMPILE_STATUS) == GLFW.GLFW_FALSE) {
			System.err.println("Couldnt compile FragmentShader!" + GL30.glGetShaderInfoLog(fragmentShaderID));
			System.exit(-1);
		}
		
		GL30.glAttachShader(programID, vertexShaderID);
		GL30.glAttachShader(programID, fragmentShaderID);
		
		GL30.glLinkProgram(programID);
		if(GL30.glGetProgrami(programID, GL30.GL_LINK_STATUS) == GLFW.GLFW_FALSE) {
			System.err.println("Couldnt link Program!" + GL30.glGetProgramInfoLog(programID));

		}
		GL20.glValidateProgram(programID);
		if(GL30.glGetProgrami(programID, GL30.GL_VALIDATE_STATUS) == GLFW.GLFW_FALSE) {
			System.err.println("Couldnt validate Program!" + GL30.glGetProgramInfoLog(programID));

		}
		
	}
	public void bind() {
		GL30.glUseProgram(programID);
	}
	public void unbind() {
		GL30.glUseProgram(0);
	}
	protected abstract void bindAttributes();
	protected void bindAttribute (int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	public void remove() {
		GL30.glDetachShader(programID, vertexShaderID);
		GL30.glDetachShader(programID, fragmentShaderID);
		GL30.glDeleteShader(vertexShaderID);
		GL30.glDeleteShader(fragmentShaderID);
		GL30.glDeleteProgram(programID);


	}
	
	public String readFile(String file)	{
		BufferedReader reader = null;
		StringBuilder string = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader( file ));
			String line;
			while((line = reader.readLine())!=null) {
				string.append(line).append("\n");
			}
			reader.close();
		}catch(IOException e) {
			System.err.println("Couldnt find fragment or vertexShader's file!");
			System.exit(-1);
		}
		return string.toString();
	}
	
}
