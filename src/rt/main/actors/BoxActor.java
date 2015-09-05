package rt.main.actors;


import org.lwjgl.opengl.GL11;

import rt.main.Actor;
import rt.main.Scene;

public abstract class BoxActor extends Actor {
	
	/*
	 * Setting some default variables.
	 */
	private float width = 16;
	private float height = 16;
	private float length = 16;
	
	/*
	 * Creating the faces for a box (6).
	 */
	public BoxSide[] sides = new BoxSide[]{
			new BoxSide(this, "right"),
			new BoxSide(this, "front"),
			new BoxSide(this, "left"),
			new BoxSide(this, "back"),
			new BoxSide(this, "top"),
			new BoxSide(this, "bottom")
	};

	public BoxActor(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	public void update(int delta){
		tick(delta);
		if(draw){
			draw(delta);
			updateSides();
		}
	}  

	/**
	 * This function is used to set the size of the box.
	 * 
	 * @param width length of the x-axis.
	 * @param height the length of the y-axis.
	 * @param length the length of the z-axis.
	 */
	public void setSize(float width, float height, float length){
		for(int i = 0; i < sides.length; i++){
			sides[i].width = width;
			sides[i].height = height;
			sides[i].length = length;
		}
		this.width = width;
		this.height = height;
		this.length = length;
		getHitbox().setHeight(height);
		getHitbox().setWidth(width);
		getHitbox().setLength(length);
	}

	/**
	 * 
	 * @return the length of the x-axis.
	 */
	public float getWidth(){
		return this.width;
	}

	/**
	 * 
	 * @return the length of the y-axis.
	 */
	public float getHeight(){
		return this.height;
	}

	/**
	 * 
	 * @return the length of the z-axis.
	 */
	public float getLength(){
		return this.length;
	}

	/**
	 * This function is used to update all of the sides. Logic and Graphics.
	 */
	private void updateSides(){

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		for(int i = 0; i < sides.length; i++){
			sides[i].update();
		}
		GL11.glPopMatrix();
	}

	/**
	 * 
	 * @param type the name of the side. For example "left" or "top". Might be a bit confusing.
	 * @return the side object with the selected type.
	 */
	public BoxSide getSide(String type){
		for(int i = 0; i < sides.length; i++){
			if(sides[i].type.equals(type)){
				return sides[i];
			}
		}

		return null;
	}

}
