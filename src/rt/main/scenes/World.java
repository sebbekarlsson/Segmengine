package rt.main.scenes;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import rt.main.Scene;
import rt.main.actors.Block;
import rt.main.actors.Player;
import rt.main.types.BlockType;

public class World extends Scene {

	private Player player = new Player(this, 0, 128, 0);
	private Block[][][] blocks = new Block[16][16][16];
	Random random = new Random();
	
	@Override
	public void init() {
		backgroundColor = new Color(82,217,255);
		
		generateCity();
		
		stageActor(player);
		getCamera().setKeyboardControlled(false);
		getCamera().setMouseControlled(true);
		
		
	}

	@Override
	public void tick(int delta) {
		
		setAir();
		updateBlocks(delta);
		
		getCamera().x = getPlayer().x;
		getCamera().y = getPlayer().y+32;
		getCamera().z = getPlayer().z;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			generateCity();
		}
		
	}

	@Override
	public void draw(int delta) {
		
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	private void generateCity(){

		for(int x = 0; x < 16*2; x++){
			for(int z = 0; z < 16*2; z++){
				stageActor(new Block(this, 16*x, 16*1, 16*z, BlockType.GRASS));
			}
		}
		
		for(int x = 0; x < 16*1; x++){
			for(int z = 0; z < 16*1; z++){
				stageActor(new Block(this, 16*x, 16*2, 16*z, BlockType.GRASS));
			}
		}
		
		for(int x = 0; x < 16/2; x++){
			for(int z = 0; z < 16/2; z++){
				stageActor(new Block(this, 16*x, 16*3, 16*z, BlockType.GRASS));
			}
		}
			
		
	}
	
	private void updateBlocks(int delta){
		for(int x = 0; x < blocks.length; x++){
			for(int y = 0; y < blocks[x].length; y++){
				for(int z = 0; z < blocks[x][y].length; z++){
					blocks[x][y][z].update(delta);
				}
			}
		}
	}
	
	private void setAir(){
		for(int x = 0; x < blocks.length; x++){
			for(int y = 0; y < blocks[x].length; y++){
				for(int z = 0; z < blocks[x][y].length; z++){
					blocks[x][y][z] = new Block(this, x*16, y*16, z*16, BlockType.AIR);
				}
			}
		}
	}

}
