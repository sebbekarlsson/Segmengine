package rt.main.actors;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.scenes.worlds.World;
import rt.main.types.BlockType;


public class Player extends Entity {

	Random random = new Random();

	public Player(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
		updateChunks = true;
	}

	@Override
	public void tick(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			dx += (0.0004f * (float) Math.sin(Math.toRadians(scene.getCamera().getYaw()))) * delta;
			dz -= (0.0004f * (float) Math.cos(Math.toRadians(scene.getCamera().getYaw()))) * delta;
		}
		while(Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				if(Keyboard.getEventKey() == Keyboard.KEY_LCONTROL){
					getChunk().setBlock((int)((this.x%(16*16))/Block.SIZE), (int)((this.y%(16*256))/Block.SIZE), (int)((this.z%(16*16))/Block.SIZE), BlockType.getRandom());
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_R){
					y = 120;
				}
			}
		}
		while(Mouse.next()){
			if (Mouse.getEventButtonState()){
				((World) scene).getCamera().placeBlock(BlockType.COBBLE);
			}
		}
	}

	@Override
	public void init() {
		getHitbox().setDraw(true);
		getHitbox().setColor(Color.blue);
		getHitbox().setWidth(Block.SIZE / 2);
		getHitbox().setLength(Block.SIZE / 2);
		getHitbox().setHeight((Block.SIZE * 2)-1);
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
