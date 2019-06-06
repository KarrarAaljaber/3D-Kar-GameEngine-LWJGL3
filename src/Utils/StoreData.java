package Utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class StoreData {

	
	public static  IntBuffer StoreDataInIntBuffer(int[]data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		//putting the data inside the intbuffer
		buffer.put(data);
		//flipping it so we can read from it
		buffer.flip();
		return buffer;
	}
	public static  FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		//flipping it so we can read from it
		buffer.flip();
		return buffer;
	}
}
