package rt.main;

import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureBank {
	public static Texture TEST = loadTexture("res/test/building.jpg", "jpg");
	public static Texture TEST2 = loadTexture("res/test/asphalt2.jpg", "jpg");
	public static Texture TEST3 = loadTexture("res/test/grass.jpg", "jpg");
	public static Texture DIRT = loadTexture("res/test/dirt.png", "png");
	public static Texture GRASS = loadTexture("res/test/grass.png", "gif");
	public static Texture COBBLE = loadTexture("res/test/cobble.png", "png");
	public static Texture WORKBENCH_TOP = loadTexture("res/test/workbench_top.png", "png");
	public static Texture WORKBENCH_SIDE = loadTexture("res/test/workbench_side.png", "png");
	public static Texture PLANKS = loadTexture("res/test/planks.png", "png");
	public static Texture GLASS = loadTexture("res/test/glass.png", "png");
	public static Texture LOG = loadTexture("res/test/log.png", "png");
	public static Texture LOG_TOP = loadTexture("res/test/log_top.png", "png");
	public static Texture STONE = loadTexture("res/test/stone.png", "png");
	public static Texture LEAF = loadTexture("res/test/leaf.png", "png");
	
	public static Texture loadTexture(String path, String format){
		Texture t = null;
		try {
			t = TextureLoader.getTexture(format, new FileInputStream(path));
			GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}
}
