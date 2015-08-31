package rt.main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import rt.main.actors.Block;
import rt.main.physics.Frustum;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.Ray;
import rt.main.scenes.worlds.World;
import rt.main.test.Building;
import rt.main.types.BlockType;

public class Camera extends Actor {

	private boolean mouseControlled = false;
	private boolean keyboardControlled = false;
	public Frustum frustum;

	public Camera(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
		frustum = Frustum.getFrustum();
	}


	@Override
	public void init() {

	}


	public void update(int delta){
		frustum = Frustum.getFrustum();
		tick(delta);
		draw(delta);

		if(isKeyboardControlled()){
			slaveForKeyboard();
		}
		if(isMouseControlled()){
			slaveForMouse(delta);
		}
	}


	@Override
	public void draw(int delta) {

	}

	@Override
	public void tick(int delta) {

	}

	public float getYaw(){
		return this.yrot;
	}

	public float getPitch(){
		return this.xrot;
	}

	public void setMouseControlled(boolean mouseControlled){
		this.mouseControlled = mouseControlled;
	}

	public void setKeyboardControlled(boolean keyboardControlled){
		this.keyboardControlled = keyboardControlled;
	}

	public boolean isMouseControlled(){
		return this.mouseControlled;
	}

	public boolean isKeyboardControlled(){
		return this.keyboardControlled;
	}

	private void slaveForMouse(int delta){
		this.xrot -= Mouse.getDY();
		this.yrot += Mouse.getDX();
	}

	private void slaveForKeyboard(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			x -= 1f * (float) Math.sin(Math.toRadians(getYaw()));
			z += 1f * (float) Math.cos(Math.toRadians(getYaw()));
			y += 1f * (float) Math.tan(Math.toRadians(getPitch()));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			z -= 0.2f;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			x -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			x += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			scene.stageActor(new Building(scene,-x,-y,-z));
		}
	}


	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {
		// TODO Auto-generated method stub

	}

	public Block getFacingBlock(){
		Ray ray = new Ray(this.scene, x, y, z);

		for(int range = 0; range < 6; range++){
			ray.x += Block.SIZE * (float) Math.sin(Math.toRadians(getYaw()));
			ray.z -= Block.SIZE * (float) Math.cos(Math.toRadians(getYaw()));
			ray.y -= Block.SIZE * (float) Math.tan(Math.toRadians(getPitch()));
			Chunk chunk = ray.getChunk();
			if(chunk.isLoaded()){
				Block[][][] blocks = chunk.blocks;
				float mod = (Block.SIZE * Chunk.HEIGHT);
				int mod_div = Block.SIZE;

				int _x = (int)ray.x%(16*16)/ 16;
				int _y = (int)(ray.y%(mod) / mod_div);
				int _z = (int)ray.z%(16*16)/ 16;

				if(_x > 0 && _x < ((World)scene).chunk_number*16 && _z >= 0 && _z < ((World)scene).chunk_number*16 && _y >= 0 && y < ((World)scene).chunk_number*16 ){

					Block block = blocks[_x][_y][_z];
					if(!block.getType().equals(BlockType.AIR)){
						return block.intersectsWithRay(ray);
					}

				}
			}
		}

		return null;
	}


}
