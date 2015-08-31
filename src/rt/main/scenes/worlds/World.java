package rt.main.scenes.worlds;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

import rt.main.Scene;
import rt.main.actors.Block;
import rt.main.actors.Player;
import rt.main.types.BlockType;

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


		//chunkHandler = new ChunkHandler(chunks);
		//chunkHandler.start();

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

}
