package Utils;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Mouse  extends GLFWMouseButtonCallback{

	public static boolean btns [] = new boolean[6];

	public void invoke(long window, int button, int action, int mods) {
		btns[button] = action != GLFW.GLFW_RELEASE;
	}

}
