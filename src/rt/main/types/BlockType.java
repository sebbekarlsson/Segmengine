package rt.main.types;

import org.newdawn.slick.opengl.Texture;

import rt.main.TextureBank;

public enum BlockType {
	
	AIR(null, null, null, null, null, null),
	GRASS(TextureBank.DIRT, TextureBank.DIRT, TextureBank.DIRT, TextureBank.DIRT, TextureBank.GRASS, TextureBank.DIRT);
	
	public Texture texture_left;
	public Texture texture_right;
	public Texture texture_front;
	public Texture texture_back;
	public Texture texture_top;
	public Texture texture_bottom;
	
	BlockType(Texture texture_left, Texture texture_right, Texture texture_front, Texture texture_back, Texture texture_top, Texture texture_bottom){
		this.texture_left = texture_left;
		this.texture_right = texture_right;
		this.texture_front = texture_front;
		this.texture_back = texture_back;
		this.texture_top = texture_top;
		this.texture_bottom = texture_bottom;
	}
}
