package rt.main.physics;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import rt.main.Actor;

public class Hitbox{
	private float width;
	private float height;
	private float length;
	private float x,y,z = 0f;
	private Actor actor;
	private boolean draw = false;
	private Color color = Color.red;

	public Hitbox(Actor actor, float width, float height, float length){
		this.width = width;
		this.height = height;
		this.length = length;
		this.actor = actor;
	}

	public void update(){
		tick();
		draw();
	}

	public void tick(){

	}

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

	public float getWidth(){
		return this.width;
	}

	public float getHeight(){
		return this.height;
	}

	public float getLength(){
		return this.length;
	}

	public void setWidth(float width){
		this.width = width;
	}

	public void setHeight(float height){
		this.height = height;
	}

	public void setLength(float length){
		this.length = length;
	}
	
	public void setDraw(boolean draw){
		this.draw = draw;
	}
	
	public boolean isDrawing(){
		return this.draw;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public float getZ(){
		return this.z;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setZ(float z){
		this.z = z;
	}
}
