package rt.main.scenes.worlds;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.actors.Block;
import rt.main.types.BlockType;

public class Chunk extends Actor {

	public ArrayList<Actor> actors = new ArrayList<Actor>();
	public Block[][][] blocks = null;
	private boolean loaded = false;
	private boolean alive = false;
	Random random = new Random();
	public static int WIDTH = 16;
	public static int HEIGHT = 64;

	public Chunk(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	@Override
	public void init() {
	}

	public void update(int delta){
		alive = true;
		tick(delta);
		draw(delta);

		for(int i = 0; i < actors.size(); i++){
			if(actors.get(i) != null){
				actors.get(i).update(delta);
				actors.get(i).getHitbox().update();



				if(!actors.get(i).isInitialized()){
					actors.get(i).initialize();
				}
			}
		}
	}

	@Override
	public void tick(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey,
			float timez) {
		// TODO Auto-generated method stub

	}
	
	public void tickle(){
		for(int xx = 0; xx < blocks.length; xx++){
			for(int yy = 0; yy < blocks[xx].length; yy++){
				for(int zz = 0; zz < blocks[xx][yy].length; zz++){
					blocks[xx][yy][zz].tickle();
				}
			}
		}
	}

	public Block setBlock(int x, int y, int z, BlockType type){

		if(blocks[x][y][z] == null){
			Block block = new Block(scene, this.x+(x * (Block.SIZE)), (y * Block.SIZE), this.z+(z * (Block.SIZE)), type);
			blocks[x][y][z] = block;
			stageActor(block.tickle());

		}else{
			blocks[x][y][z].setType(type);

		}

		return blocks[x][y][z];
	}

	public Block getBlock(int x, int y, int z){
		return blocks[x][y][z];
	}

	@SuppressWarnings("unchecked")
	public void save(){
		File file = new File(getFileName());
		JSONArray blocks_json = new JSONArray();

		for(int x = 0; x < this.blocks.length; x++){
			for(int y = 0; y < this.blocks[x].length; y++){
				for(int z = 0; z < this.blocks[x][y].length; z++){
					Block block = this.blocks[x][y][z];
					JSONObject block_json = new JSONObject();
					if(block != null){
						block_json.put("x", block.x);
						block_json.put("y", block.y);
						block_json.put("z", block.z);
						block_json.put("type", block.getType().toString());
					}
					blocks_json.add(block_json);
				}
			}
		}

		JSONObject chunk_json = new JSONObject();
		chunk_json.put("blocks", blocks_json);

		try {
			PrintWriter out = new PrintWriter(file);
			String json = chunk_json.toJSONString().replace("},", "},\r\n").replace(":[", ":[\r\n");
			out.write(json);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void stageActor(Actor actor){
		actors.add(actor);
	}

	public void removeActor(Actor actor){
		actors.remove(actor);
	}

	public void removeActor(int index){
		actors.remove(index);
	}

	public String getFileName(){
		return "world/chunks/chunk_"+this.x+"_"+this.z+".json";
	}
	
	public boolean chunkFileExists(){
		File file = new File(getFileName());
		return file.exists();
	}
	
	public void load(){
		initializeBlocks();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(getFileName()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();
		
		Object obj = JSONValue.parse(content);
		JSONArray block_array = (JSONArray)((JSONObject)obj).get("blocks");
		
		for(int i = 0; i < block_array.size(); i++){
			JSONObject block =(JSONObject)block_array.get(i);
			Block b = new Block(scene, Float.parseFloat(block.get("x").toString()), Float.parseFloat(block.get("y").toString()), Float.parseFloat(block.get("z").toString()), BlockType.valueOf(block.get("type").toString()));
			setBlock((int)(b.x%(16*16)) / Block.SIZE, (int)(b.y%(16*256)) / Block.SIZE, (int)(b.z%(16*16)) / Block.SIZE, b.getType());
		}
		
		this.loaded = true;
	}
	
	public void setAir(){
		initializeBlocks();
		
		for(int xx = 0; xx < WIDTH; xx++){
			for(int yy = 0; yy < HEIGHT; yy++){
				for(int zz = 0; zz < WIDTH; zz++){
					Block block = new Block(scene, this.x+(xx * (Block.SIZE)), (yy * Block.SIZE), this.z+(zz * (Block.SIZE)), BlockType.AIR);
					blocks[xx][yy][zz] = block;
					stageActor(block);
				}
			}
		}
	}
	
	public void initializeBlocks(){
		this.blocks = new Block[WIDTH][HEIGHT][WIDTH];
		this.loaded = true;
	}
	
	public void forgetBlocks(){
		this.blocks = null;
	}
	
	public void unload(){
		save();
		forgetBlocks();
		this.loaded = false;
	}
	
	public void generate(){
		
		BufferedImage map_height = null;
		BufferedImage map = null;
		try {
			map_height = ImageIO.read(new File("world/map/map_height.png"));
			map = ImageIO.read(new File("world/map/map.png"));
			
			
			map_height = map_height.getSubimage((int)x/16, (int)z/16, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				Color h_color = new Color(map_height.getRGB(x, z));
				int h_r = h_color.getRed();
				int h_g = h_color.getGreen();
				int h_b = h_color.getBlue();
				
				Color m_color = new Color(map.getRGB(x, z));
				int m_r = m_color.getRed();
				int m_g = m_color.getGreen();
				int m_b = m_color.getBlue();
				
				int height = h_r;
				BlockType type = BlockType.AIR;
				
				if(m_g == 255){
					type = BlockType.GRASS;
				}
				
				blocks[x][height][z].setType(type);
				
				// - TREE - //
				if(random.nextInt(60) == 0){
					int tree_height = 6;
					for(int i = 1; i < tree_height; i++){
						blocks[x][height+i][z].setType(BlockType.LOG);
					}
					blocks[x][height+(tree_height)][z].setType(BlockType.LEAF);
					
					int leafsize = 2 + random.nextInt(1);
					int h = height+(tree_height-leafsize);
					for(int xx = -leafsize; xx < leafsize; xx++){
						for(int yy = -leafsize; yy < leafsize; yy++){
							if(x+xx > 0 && x+xx < map_height.getWidth() && z+yy > 0 && z+yy < map_height.getHeight()){

								float distance = (float) Math.sqrt(((x+xx) - x)*((x+xx) - x) + ((z+yy) - z)*((z+yy) - z));
								if(distance < leafsize && distance > 0){
									
									int height_ = h + (int)((leafsize) - distance);
									if(x+xx > 0 && x+xx < ((World)scene).chunk_number && z+yy > 0 && z+yy < ((World)scene).chunk_number)
									blocks[x+xx][height_][z+yy].setType(BlockType.LEAF);
								}
							}
						}
					}
					
				}
				
				for(int i = 0; i < height; i++){
					blocks[x][i][z].setType(BlockType.STONE);
				}
				
				
			}
		}
		
		this.loaded = true;
	}
	
	public boolean isLoaded(){
		return this.loaded;
	}
	
	public boolean hasChunkLoader(){
		for(int i = 0; i < actors.size(); i++){
			if(actors.get(i) == null){
				System.out.println("asp");
			}
			if(actors.get(i).updatesChunks()){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasPlayer(){
		return ((World) scene).getPlayer().getChunk() == this;
	}
	
	public ArrayList<Actor> getChunkLoaders(){
		ArrayList<Actor> loaders = new ArrayList<Actor>();
		for(int i = 0; i < actors.size(); i++){
			if(actors.get(i).updatesChunks()){
				loaders.add(actors.get(i));
			}
		}
		if(((World) scene).getPlayer().getChunk() == this){
			loaders.add(((World) scene).getPlayer());
		}
		return loaders;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void kill(){
		this.alive = false;
	}
}
