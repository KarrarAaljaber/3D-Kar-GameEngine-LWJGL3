package RunEngine;



import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import BaseEngine.LoadModel;
import BaseEngine.Renderer;
import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import Textures.ModelTexture;
import Textures.Texture;
import Utils.Cursor;
import Utils.KeyBoard;
import Utils.Mouse;
import shaders.StaticShader;

public class Window implements Runnable {
	
	// The window handle
		private long window;
		private Thread thread;
		private boolean isRunning = false;

		private int width, height;
		private String title;
		

		private GLFWKeyCallback keyCallback;
		private GLFWCursorPosCallback cursorCallback;
		private GLFWMouseButtonCallback mouseCallback;
		
		
		private StaticShader shader;
		private LoadModel loader;
		private Renderer renderer;
		
		private RawModel model;
		private Entity entity;
		private TexturedModel texmodel;
		private ModelTexture texture;
		public Window(int width, int height, String title) {
			this.width = width;
			this.height = height;
			this.title = title;

		
		}
		public void initObjects() {
			shader = new StaticShader();
			loader = new LoadModel();
			renderer= new Renderer();
			  float[] vertices = {
					    -0.5f, 0.5f, 0f,
					    -0.5f, -0.5f, 0f,
					    0.5f, -0.5f, 0f,
					    0.5f, -0.5f, 0f,
					    0.5f, 0.5f, 0f,
					    -0.5f, 0.5f, 0f
					  };
			  
			  float[] textureCoords = {
					 0,0,
					 0,1,
					 1,1,
					 1,0
					  };
			model = loader.LoadToVAO(vertices, textureCoords);
	
		//	texture = new ModelTexture(loader.loadTexture("res/pik.png"));
			//texmodel = new TexturedModel(model );
			entity = new Entity(model, new Vector3f(0,0,0), new Vector3f(0,0,0));
			shader.create();

		}
		public void render() {
			renderer.prepare();
			
			shader.bind();
			renderer.render(entity);
			
			GL11.glEnd();
			shader.unbind();
			GLFW.glfwSwapBuffers(window);
		}
		public void update() {
			GLFW.glfwPollEvents();
			
			if(KeyBoard.keys[GLFW.GLFW_KEY_ESCAPE]) {
				CloseDisplay();
			}
		}
		

	
		
		public void init() {
			GLFWErrorCallback.createPrint(System.err).set();
			if(!GLFW.glfwInit()) {
				System.err.println("Couldnt init GLFW");
				System.exit(-1);
			}
			
			GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
			
			window = GLFW.glfwCreateWindow(width, height, title,NULL , NULL);
			if(window == NULL) {
				System.err.println("Couldnt create window!");
				System.exit(-1);
			}
			GLFW.glfwSetKeyCallback(window, keyCallback = new KeyBoard());
			GLFW.glfwSetCursorPosCallback(window, cursorCallback = new Cursor());
			GLFW.glfwSetMouseButtonCallback(window, mouseCallback = new Mouse());
			// Get the thread stack and push a new frame
			try ( MemoryStack stack = MemoryStack.stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1); // int*
				IntBuffer pHeight = stack.mallocInt(1); // int*

				// Get the window size passed to glfwCreateWindow
				GLFW.glfwGetWindowSize(window, pWidth, pHeight);

				// Get the resolution of the primary monitor
				GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

				// Center the window
				GLFW.glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			} // the stack frame is popped automatically

			// Make the OpenGL context current
			GLFW.glfwMakeContextCurrent(window);
			// Enable v-sync
			GLFW.glfwSwapInterval(1);

			// Make the window visible
			GLFW.glfwShowWindow(window);
				
		}
		public long getWindow() {
			return window;
		}

		
 	
	
		
		private double previousTime = GLFW.glfwGetTime();
		int nbFrames = 0;
		public void run() {
			init();
			GL.createCapabilities();
			
			GL11.glClearColor(0, 0, 0,0);
			
			initObjects();
		
			while(!GLFW.glfwWindowShouldClose(window)) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				double currentTime =  GLFW.glfwGetTime();
				nbFrames++;
				if(currentTime - previousTime >= 1.0 ) {
					System.out.println(1000/(double)nbFrames + "ms " + "\t" + "16.66 ms == 60PS" + "\t" + " 33.33 ms == 30FPS");
					 nbFrames = 0;
			         previousTime += 1.0;
				}
				
				render();
				update();
			}
			isRunning = false;
			stop();
		}
		public void CloseDisplay() {
			GLFW.glfwDestroyWindow(window);
			stop();
			isRunning = false;
			
		}
		public  void start() {
			isRunning = true;
			thread = new Thread(this, "Game Engine");
			thread.start();
		}
		public  void stop() {
			if(isRunning == false) {
				shader.remove();
				loader.cleanUP();
				// Free the window callbacks and destroy the window
				Callbacks.glfwFreeCallbacks(window);
				GLFW.glfwDestroyWindow(window);

				keyCallback.free();
				cursorCallback.free();
				// Terminate GLFW and free the error callback
				GLFW.glfwTerminate();
				GLFW.glfwSetErrorCallback(null).free();
			}
		}

	}
	