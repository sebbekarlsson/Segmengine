package rt.main.actors;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.types.BlockType;


public class Player extends Entity {

	Random random = new Random();
	
	public Player(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	@Override
	public void tick(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			dx += (0.001f * (float) Math.sin(Math.toRadians(scene.getCamera().getYaw()))) * delta;
			dz -= (0.001f * (float) Math.cos(Math.toRadians(scene.getCamera().getYaw()))) * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			y = 640f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_M)){
			scene.stageActor(new Block(scene, x, y, z, BlockType.GRASS));
		}
	}

	@Override
	public void init() {
		getHitbox().setDraw(true);
		getHitbox().setColor(Color.blue);
		getHitbox().setWidth(16);
		getHitbox().setLength(16);
		getHitbox().setHeight(16);
		getHitbox().setX(-getHitbox().getWidth() / 2);
		getHitbox().setZ(-getHitbox().getLength() / 2);

	}

	@Override
	public void draw(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {
		
	}

}
