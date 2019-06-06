package BaseEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.TextureLoader;

import Models.RawModel;
import Textures.Texture;
import Utils.StoreData;

public class LoadModel {

	private List<Integer>vaos = new ArrayList<Integer>();
	private List<Integer>vbos = new ArrayList<Integer>();
	private List<Integer>textures = new ArrayList<Integer>();

	public RawModel LoadToVAO(float positions[], float coords[]) {
		int vaoID = createVAO();
		
		storeDataInVAO(0,3, positions);
		storeDataInVAO(0,2, coords);


		unbindVAO();
		return new RawModel(vaoID, positions.length /3);
	}
	public int loadTexture(String path) {
		Texture texture = new Texture();
		Texture.loadTexture(path);
		int texid = texture.getTextureID();
		
		return texid;
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
	}
	private void unbindVAO() {
		//unbind the VAO
		GL30.glBindVertexArray(0);
	}
	
	private void storeDataInVAO(int vbonumber, int coordinateSize, float[] data ) {
		int vboID = GL15.glGenBuffers();
		
		vbos.add(vboID);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = StoreData.storeDataInFloatBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		GL30.glVertexAttribPointer(vbonumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(vboID, 0);
	}
	

	public void cleanUP()	{
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
			
		}
		for(int vbo:vbos) {
			GL30.glDeleteVertexArrays(vbo);
			
		}
		for(int texture:textures) {
			GL30.glDeleteTextures(texture);
			
		}
	}
}
