package rt.main.types;

import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import rt.main.TextureBank;

public enum BlockType {
	
	AIR(null, null, null, null, null, null, false, false),
	GRASS(TextureBank.DIRT, TextureBank.DIRT, TextureBank.DIRT, TextureBank.DIRT, TextureBank.GRASS, TextureBank.DIRT, true, false),
	COBBLE(TextureBank.COBBLE, TextureBank.COBBLE, TextureBank.COBBLE, TextureBank.COBBLE, TextureBank.COBBLE, TextureBank.COBBLE, true, false),
	WORKBENCH(TextureBank.WORKBENCH_SIDE, TextureBank.WORKBENCH_SIDE, TextureBank.WORKBENCH_SIDE, TextureBank.WORKBENCH_SIDE, TextureBank.WORKBENCH_TOP, TextureBank.PLANKS, true, false),
	GLASS(TextureBank.GLASS, TextureBank.GLASS, TextureBank.GLASS, TextureBank.GLASS, TextureBank.GLASS, TextureBank.GLASS, true, true),
	LOG(TextureBank.LOG, TextureBank.LOG, TextureBank.LOG, TextureBank.LOG, TextureBank.LOG_TOP, TextureBank.LOG_TOP, true, false),
	STONE(TextureBank.STONE, TextureBank.STONE, TextureBank.STONE, TextureBank.STONE, TextureBank.STONE, TextureBank.STONE, true, false),
	LEAF(TextureBank.LEAF, TextureBank.LEAF, TextureBank.LEAF, TextureBank.LEAF, TextureBank.LEAF, TextureBank.LEAF, true, true),
	SAND(TextureBank.SAND, TextureBank.SAND, TextureBank.SAND, TextureBank.SAND, TextureBank.SAND, TextureBank.SAND, true, false);
	
	public Texture texture_left;
	public Texture texture_right;
	public Texture texture_front;
	public Texture texture_back;
	public Texture texture_top;
	public Texture texture_bottom;
	public boolean solid;
	public boolean transparent;
	
	BlockType(Texture texture_left, Texture texture_right, Texture texture_front, Texture texture_back, Texture texture_top, Texture texture_bottom, boolean solid, boolean transparent){
		this.texture_left = texture_left;
		this.texture_right = texture_right;
		this.texture_front = texture_front;
		this.texture_back = texture_back;
		this.texture_top = texture_top;
		this.texture_bottom = texture_bottom;
		this.solid = solid;
		this.transparent = transparent;
	}
	
	public static BlockType getRandom(){
		Random random = new Random();
		BlockType type = null;
		type = BlockType.values()[random.nextInt(BlockType.values().length)];
		while( type.equals(BlockType.AIR)){
		type = BlockType.values()[random.nextInt(BlockType.values().length)];
		}
		return type;
	}
}
