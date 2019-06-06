package RunEngine;


import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;



public class MainFrame {
	

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "3D-Kar-Engine";
	
	public static void main(String[]args) {
		Window window = new Window(WIDTH, HEIGHT, TITLE);
		window.start();
	}
	

}


