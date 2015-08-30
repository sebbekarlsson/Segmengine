package rt.main.scenes.worlds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	Random random = new Random();
	
	public static int WIDTH = 16;
	public static int HEIGHT = 32;

	public Chunk(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	@Override
	public void init() {
	}

	public void update(int delta){
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

	public Block setBlock(int x, int y, int z, BlockType type){

		if(blocks[x][y][z] == null){
			Block block = new Block(scene, this.x+(x * (16)), (y * 16), this.z+(z * (16)), type);
			blocks[x][y][z] = block;
			stageActor(block);

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
			setBlock((int)(b.x%(16*16)) / 16, (int)(b.y%(16*256)) / 16, (int)(b.z%(16*16)) / 16, b.getType());
		}
		
		this.loaded = true;
	}
	
	public void setAir(){
		initializeBlocks();
		
		for(int xx = 0; xx < WIDTH; xx++){
			for(int yy = 0; yy < HEIGHT; yy++){
				for(int zz = 0; zz < WIDTH; zz++){
					Block block = new Block(scene, this.x+(xx * (16)), (yy * 16), this.z+(zz * (16)), BlockType.AIR);
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
		System.out.println("unloaded");
		save();
		forgetBlocks();
		this.loaded = false;
	}
	
	public void generate(){
		for(int xx = 0; xx < 16; xx++){
			for(int zz = 0; zz < 16; zz++){
				getChunk().setBlock(xx, 0, zz, BlockType.GRASS);
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
}
