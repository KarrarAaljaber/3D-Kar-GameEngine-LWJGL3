package Utils;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class Cursor extends GLFWCursorPosCallback {

	public static int x, y;

	public void invoke(long window, double x, double y) {
		Cursor.x = (int) x;
		Cursor.y = (int) y;
		
	}

}
