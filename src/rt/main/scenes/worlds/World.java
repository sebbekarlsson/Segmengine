package rt.main.scenes.worlds;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

import rt.main.Scene;
import rt.main.actors.Block;
import rt.main.actors.Player;
import rt.main.types.BlockType;
import rt.main.types.DirectionType;

public class World extends Scene {

	private Player player;
	public WorldGenerator worldgenerator = new WorldGenerator(this);
	public int chunk_number = 128;
	public Chunk[][] chunks = new Chunk[chunk_number][chunk_number];
	public ChunkHandler chunkHandler;
	Random random = new Random();

	@Override
	public void init() {
		backgroundColor = new Color(82,217,255);
		getCamera().setKeyboardControlled(false);
		getCamera().setMouseControlled(true);

		generateChunks();
		if(worldgenerator.generate()){System.out.println("Done!");}


		chunkHandler = new ChunkHandler(chunks);
		chunkHandler.start();

		player = new Player(this, 0, 128, 0);
		stageActor(player);


	}

	@Override
	public void tick(int delta) {

		//updateBlocks(delta);
		/*Block block = getCamera().getFacingBlock();
		if(block != null){
			block.getHitbox().setDraw(true);
		}*/

		getCamera().x = getPlayer().x;
		getCamera().y = getPlayer().y+24f;
		getCamera().z = getPlayer().z;

	}

	@Override
	public void draw(int delta) {

	}

	public Player getPlayer(){
		return this.player;
	}


	public void generateChunks(){
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				chunks[x][z] = new Chunk(this, x*16*16, 0, z*16*16);
			}
		}
	}

	public Chunk getChunk(int x, int z){
		return chunks[x][z];
	}

	public void placeBlock(BlockType type){
		Block block = getCamera().getFacingBlock();
		DirectionType dir = DirectionType.None;
		if(block != null){
			if(block.ray != null){
				block.getHitbox().setDraw(true);
				Vector3f b_v = new Vector3f(block.x, block.y, block.z);
				Vector3f hit = new Vector3f(block.ray.x, block.ray.y, block.ray.z);
				Vector3f incomingVec = new Vector3f();
				
				
				float angle =Vector3f.angle(b_v, hit);
				System.out.println(Math.toDegrees(angle));
				
				
				
				//top
				//if (angle >= 180 && angle <= 0)
				/*int placex = (int)((b_v.x%(16*16))/16);
				int placey = (int)(((b_v.y+1)%(16*256))/16);
				int placez = (int)((b_v.z%(16*16))/16);
			
				block.ray.getChunk().setBlock(placex, placey, placez, BlockType.COBBLE);*/



			}else{
				System.out.println("ray is null");
			}
		}else{
			System.out.println("block is null");
		}

		System.out.println(dir);
	}


}
