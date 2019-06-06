package Entities;

import org.joml.Vector3f;

import Models.RawModel;
import Models.TexturedModel;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position,  rotation;
	public Entity(TexturedModel model, Vector3f position, Vector3f rotation) {
		super();
		this.model = model;
		this.position = position;
		this.rotation = rotation;
	}
	
	
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x +=dx;
		this.position.y +=dy;
		this.position.z +=dz;
	}
	public void increaseRotation(float rx, float ry, float rz) {
		this.rotation.x +=rx;
		this.rotation.y +=ry;
		this.rotation.z +=rz;
	}
}
