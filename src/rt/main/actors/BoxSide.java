package rt.main.actors;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;



public class BoxSide{

	public String type;
	public BoxActor boxactor;
	public float width = 4;
	public float height = 4;
	public float length = 4;
	public float textureRepeat = 1;
	public Texture texture;

	public BoxSide(BoxActor boxactor, String type) {
		this.boxactor = boxactor;
		this.type = type;
	}


	public void update(){
		tick();
		draw();
	}

	public void draw() {
		if(texture != null){
			//back
			if(type.equals("back")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, 0, 0);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, height, 0);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//front
			if(type.equals("front")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, height, length);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, height, length);

				GL11.glEnd();
				GL11.glPopMatrix();
			}





			//left
			if(type.equals("left")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, 0, 0);

				GL11.glTexCoord2f(textureRepeat,0);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(0, height, length);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}



			//right
			if(type.equals("right")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, height, length);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(width, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//bottom
			if(type.equals("bottom")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);


				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, 0, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, 0, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, 0, 0);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, 0, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}




			//top
			if(type.equals("top")){
				if(texture != null){
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					texture.bind();
				}
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0, height, length);

				GL11.glTexCoord2f(textureRepeat, 0);
				GL11.glVertex3f(width, height, length);

				GL11.glTexCoord2f(textureRepeat, textureRepeat);
				GL11.glVertex3f(width, height, 0);

				GL11.glTexCoord2f(0, textureRepeat);
				GL11.glVertex3f(0, height, 0);

				GL11.glEnd();
				GL11.glPopMatrix();
			}

		}

	}

	public void tick() {

	}

}
