package rt.main.actors;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.World;
import rt.main.types.BlockType;
import rt.main.utils.Smart;


public class Player extends Entity {

	public Player(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
		updateChunks = true;
	}

	@Override
	public void tick(int delta) {
		/*
		 * Move the player forward if the W key is pressed.
		 */
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			dx += (0.0002f * (float) Math.sin(Math.toRadians(scene.getCamera().getYaw()))) * delta;
			dz -= (0.0002f * (float) Math.cos(Math.toRadians(scene.getCamera().getYaw()))) * delta;
		}
		
		while(Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				/*
				 * Place a block below the player
				 */
				if(Keyboard.getEventKey() == Keyboard.KEY_LCONTROL){
					getChunk().setBlock(Smart.mod(x, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH), BlockType.getRandom());
					System.out.println(x + " = " + Smart.mod(x, Chunk.WIDTH));
				}
				/*
				 * Setting the player's Y value to 120.
				 */
				if(Keyboard.getEventKey() == Keyboard.KEY_R){
					y = 120;
				}
			}
		}
		/*
		 * Destroy the block that the player is facing. (Setting it to air)
		 */
		while(Mouse.next()){
			if (Mouse.getEventButtonState()){
				Block block = ((World)scene).getCamera().getFacingBlock();
				if(block != null){
					block.setType(BlockType.AIR);
				}
			}
		}
	}

	@Override
	public void init() {
		getHitbox().setDraw(true);
		getHitbox().setColor(Color.blue);
		getHitbox().setWidth(0.5f);
		getHitbox().setLength(0.5f);
		getHitbox().setHeight((Block.SIZE * 2) - 0.25f);
		getHitbox().setX(-getHitbox().getWidth() / 2);
		getHitbox().setZ(-getHitbox().getLength() / 2);

	}

	@Override
	public void draw(int delta) {

	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {

	}

}
