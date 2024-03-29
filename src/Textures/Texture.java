package Textures;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
public class Texture {

 private int width, height;
 private int texture;
 private int id;
 public Texture() {
  id = glGenTextures();
 }

 public static Texture loadTexture(String path) {
     ByteBuffer image;
     int width, height;
     try (MemoryStack stack = MemoryStack.stackPush()) {
         /* Prepare image buffers */
         IntBuffer w = stack.mallocInt(1);
         IntBuffer h = stack.mallocInt(1);
         IntBuffer comp = stack.mallocInt(1);

         /* Load image */
         stbi_set_flip_vertically_on_load(true);
         image = stbi_load(path, w, h, comp, 4);
         if (image == null) {
             throw new RuntimeException("Failed to load a texture file!"
                                        + System.lineSeparator() + stbi_failure_reason());
         }

         /* Get width and height of image */
         width = w.get();
         height = h.get();
     }

     return createTexture(width, height, image);
 }

 public static Texture createTexture(int width, int height, ByteBuffer data) {
     Texture texture = new Texture();
     texture.setWidth(width);
     texture.setHeight(height);

     texture.bind();

     texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
     texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
     texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
     texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

     texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

     return texture;
 }

 public void setParameter(int name, int value) {
     glTexParameteri(GL_TEXTURE_2D, name, value);
 }
 public void uploadData(int width, int height, ByteBuffer data) {
     uploadData(GL_RGBA8, width, height, GL_RGBA, data);
 }
 public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
     glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
 }
 public void delete() {
     glDeleteTextures(id);
 }
 

 public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}

public int getTexture() {
	return texture;
}

public void setTexture(int texture) {
	this.texture = texture;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public void bind() {
  glBindTexture(GL_TEXTURE_2D, texture);
 }

 public void unbind() {
  glBindTexture(GL_TEXTURE_2D, 0);
 }

 public int getTextureID() {
  return texture;
 }

}