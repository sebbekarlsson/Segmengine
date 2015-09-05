package rt.main.physics;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import rt.main.Actor;

public class Hitbox{
	/*
	 * The width of the hitbox. (x-axis)
	 */
	private float width;
	
	/*
	 * The height of the hitbox. (y-axis)
	 */
	private float height;
	
	/*
	 * The length of the hitbox. (z-axis)
	 */
	private float length;
	
	/*
	 * Setting the default location for the hitbox.
	 */
	private float x,y,z = 0f;
	
	/*
	 * The actor that owns this hitbox.
	 */
	private Actor actor;
	
	/*
	 * We would like to know later if this hitbox should be drawn or not.
	 */
	private boolean draw = false;
	
	/*
	 * The hitbox color.
	 */
	private Color color = Color.red;

	public Hitbox(Actor actor, float width, float height, float length){
		this.width = width;
		this.height = height;
		this.length = length;
		this.actor = actor;
	}
	
	/**
	 * This function is used to update the hitbox. Might be used do draw, tick or both.
	 */
	public void update(){
		tick();
		draw();
	}

	public void tick(){

	}

	/**
	 * This function is used to draw our HitBox.
	 * The HitBox should probably extend the BoxActor later so that we don't need to do this.
	 */
	public void draw(){
		if(draw){
			GL11.glPushMatrix();
			GL11.glTranslatef(actor.x + getX(), actor.y + getY(), actor.z + getZ());
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f);

			GL11.glBegin(GL11.GL_QUADS);

			//back
			GL11.glVertex3f(0, 0, 0);
			GL11.glVertex3f(width, 0, 0);
			GL11.glVertex3f(width, height, 0);
			GL11.glVertex3f(0, height, 0);

			//front
			GL11.glVertex3f(0, 0, length);
			GL11.glVertex3f(width, 0, length);
			GL11.glVertex3f(width, height, length);
			GL11.glVertex3f(0, height, length);

			//left
			GL11.glVertex3f(0, 0, 0);
			GL11.glVertex3f(0, 0, length);
			GL11.glVertex3f(0, height, length);
			GL11.glVertex3f(0, height, 0);


			//right
			GL11.glVertex3f(width, 0, 0);
			GL11.glVertex3f(width, 0, length);
			GL11.glVertex3f(width, height, length);
			GL11.glVertex3f(width, height, 0);

			//bottom
			GL11.glVertex3f(0, 0, length);
			GL11.glVertex3f(width, 0, length);
			GL11.glVertex3f(width, 0, 0);
			GL11.glVertex3f(0, 0, 0);

			//top
			GL11.glVertex3f(0, height, length);
			GL11.glVertex3f(width, height, length);
			GL11.glVertex3f(width, height, 0);
			GL11.glVertex3f(0, height, 0);

			GL11.glEnd();
			GL11.glColor3f(1f, 1f, 1f);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			GL11.glPopMatrix();
		}
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
	 * This function is used to set the length of the x-axis for the HitBox.
	 * 
	 * @param width the length of the x-axis
	 */
	public void setWidth(float width){
		this.width = width;
	}

	/**
	 * This function is used to set the length of the y-axis for the HitBox.
	 * 
	 * @param height the length of the y-axis
	 */
	public void setHeight(float height){
		this.height = height;
	}

	/**
	 * This function is used to set the length of the z-axis for the HitBox.
	 * 
	 * @param length the length of the z-axis
	 */
	public void setLength(float length){
		this.length = length;
	}
	
	/**
	 * This function is used to set weather or not the HitBox should be drawn.
	 * 
	 * @param draw a boolean value
	 */
	public void setDraw(boolean draw){
		this.draw = draw;
	}
	
	/**
	 * This function is used to check if the HitBox should be drawn or not.
	 * 
	 * @return a boolean value.
	 */
	public boolean isDrawing(){
		return this.draw;
	}
	
	/**
	 * 
	 * @return the color of the hitbox.
	 */
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * This function is used to set the color of the hitbox.
	 * 
	 * @param color the color of the hitbox
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * 
	 * @return the position on the x-axis.
	 */
	public float getX(){
		return this.x;
	}
	
	/**
	 * 
	 * @return the position on the y-axis.
	 */
	public float getY(){
		return this.y;
	}
	
	/**
	 * 
	 * @return the position on the z-axis.
	 */
	public float getZ(){
		return this.z;
	}
	
	/**
	 * 
	 * @param x the position on the x-axis.
	 */
	public void setX(float x){
		this.x = x;
	}
	
	/**
	 * 
	 * @param y the position on the y-axis.
	 */
	public void setY(float y){
		this.y = y;
	}
	
	/**
	 * 
	 * @param z the position on the z-axis.
	 */
	public void setZ(float z){
		this.z = z;
	}
}
