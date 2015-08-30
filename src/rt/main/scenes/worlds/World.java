package rt.main.scenes.worlds;

import java.awt.Color;
import java.util.Random;

import rt.main.Scene;
import rt.main.actors.Player;

public class World extends Scene {

	private Player player;
	public Chunk[][] chunks = new Chunk[128][128];
	public ChunkHandler chunkHandler;
	Random random = new Random();

	@Override
	public void init() {
		generateChunks();
		
		backgroundColor = new Color(82,217,255);
		getCamera().setKeyboardControlled(false);
		getCamera().setMouseControlled(true);
		
		
		
		chunkHandler = new ChunkHandler(chunks);
		chunkHandler.start();
		
		player = new Player(this, 0, 128, 0);
		stageActor(player);

	}

	@Override
	public void tick(int delta) {

		//updateBlocks(delta);

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

	
}
