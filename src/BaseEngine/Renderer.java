package BaseEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import Entities.Entity;

public class Renderer {

	public Renderer() {
		
	}
	
	public void prepare() {
		GL11.glClearColor(0, 0, 0.2f, 1);
	}
	
	public void render(Entity entity) {
		GL30.glBindVertexArray(entity.getModel().getRawModel().getVaoID());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, entity.getModel().getRawModel().getVertexCount());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getTextureID());
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);

		GL30.glBindVertexArray(0);
	}
}
