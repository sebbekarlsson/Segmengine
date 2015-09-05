package rt.main.actors;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;



public class BoxSide{
	
	/*
	 * The type of the side. For example "left" or "top".
	 */
	public String type;
	
	/*
	 * The BoxActor that this side belongs to.
	 */
	public BoxActor boxactor;
	
	/*
	 * The width of this side.
	 */
	public float width = 4;
	
	/*
	 * The height of this side.
	 */
	public float height = 4;
	
	/*
	 * The length of this side.
	 */
	public float length = 4;
	
	/*
	 * The amount of repeat on the texture.
	 */
	public float textureRepeat = 1;
	
	/*
	 * The texture of this side.
	 */
	public Texture texture;
	
	/*
	 * We would like to know if this side should be drawn or not.
	 */
	public boolean draw = true;

	public BoxSide(BoxActor boxactor, String type) {
		this.boxactor = boxactor;
		this.type = type;
	}

	/**
	 * Updates the graphics and logic.
	 */
	public void update(){
		tick();
		if(draw){
			draw();
		}
	}
	
	/**
	 * Draws the face with it's texture depending on which type or side it is.
	 * Normals should be added here later as well.
	 */
	public void draw() {
		if(texture != null){
			//back
			if(type.equals("back")){
				GL11.glPushMatrix();
			    GL11.glCullFace(GL11.GL_BACK);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, 0, 0);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, height, 0);


				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//front
			if(type.equals("front")){
				GL11.glPushMatrix();
				GL11.glCullFace(GL11.GL_FRONT);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, height, length);


				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, height, length);

				GL11.glEnd();
				GL11.glPopMatrix();
			}





			//left
			if(type.equals("left")){
				GL11.glPushMatrix();
				GL11.glCullFace(GL11.GL_LEFT);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, 0, 0);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat,0);
				GL11.glVertex3f(0, height, length);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}



			//right
			if(type.equals("right")){
				GL11.glPushMatrix();
				GL11.glCullFace(GL11.GL_RIGHT);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, height, length);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(width, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//bottom
			if(type.equals("bottom")){
				GL11.glPushMatrix();
				//GL11.glCullFace(GL11.GL_BOTTOM);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);


				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, 0, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//top
			if(type.equals("top")){
				GL11.glPushMatrix();
				//GL11.glCullFace(GL11.GL_);
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, height, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, height, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, height, 0);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}


		}

	}
	
	/**
	 * This is where logic should be updated.
	 */
	public void tick() {

	}

}
