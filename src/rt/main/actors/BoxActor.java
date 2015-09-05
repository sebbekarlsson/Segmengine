package rt.main.actors;


import org.lwjgl.opengl.GL11;

import rt.main.Actor;
import rt.main.Scene;

public abstract class BoxActor extends Actor {

	private float width = 16;
	private float height = 16;
	private float length = 16;

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

	public float getWidth(){
		return this.width;
	}

	public float getHeight(){
		return this.height;
	}

	public float getLength(){
		return this.length;
	}

	private void updateSides(){

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		for(int i = 0; i < sides.length; i++){
			sides[i].update();
		}
		GL11.glPopMatrix();
	}

	public BoxSide getSide(String type){
		for(int i = 0; i < sides.length; i++){
			if(sides[i].type.equals(type)){
				return sides[i];
			}
		}

		return null;
	}

}
