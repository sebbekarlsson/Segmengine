package rt.main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import rt.main.actors.Block;
import rt.main.physics.Frustum;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.Ray;
import rt.main.scenes.worlds.World;
import rt.main.types.BlockType;
import rt.main.utils.Smart;

public class Camera extends Actor {
	
	/*
	 * We would like to know if the camera should be user-controlled in any way.
	 */
	private boolean mouseControlled = false;
	private boolean keyboardControlled = false;
	
	/*
	 * Creating a frustrum object used for performance later.
	 */
	public Frustum frustum;

	public Camera(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
		frustum = Frustum.getFrustum();
	}


	@Override
	public void init() {

	}


	public void update(int delta){
		
		/*
		 * Updating our Frustrum object.
		 */
		frustum = Frustum.getFrustum();
		
		tick(delta);
		draw(delta);

		/*
		 * Testing if the camera can be user-controlled in any way.
		 */
		if(isKeyboardControlled()){
			slaveForKeyboard(delta);
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
	
	/**
	 * 
	 * @return the camera's rotation around the y-axis.
	 */
	public float getYaw(){
		return this.yrot;
	}

	/**
	 * 
	 * @return the camera's rotation around the x-axis.
	 */
	public float getPitch(){
		return this.xrot;
	}

	/**
	 * Used to set the mouseControlled variable to either true or false.
	 * 
	 * @param mouseControlled
	 */
	public void setMouseControlled(boolean mouseControlled){
		this.mouseControlled = mouseControlled;
	}

	/**
	 * Used to set the keyboardControlled variable to either true or false.
	 * 
	 * @param mouseControlled
	 */
	public void setKeyboardControlled(boolean keyboardControlled){
		this.keyboardControlled = keyboardControlled;
	}

	/**
	 * 
	 * @return true if the camera is mouse-controlled an false if it isn't.
	 */
	public boolean isMouseControlled(){
		return this.mouseControlled;
	}

	/**
	 * 
	 * @return true if the camera is keyboard-controlled an false if it isn't.
	 */
	public boolean isKeyboardControlled(){
		return this.keyboardControlled;
	}

	/**
	 * This function is called to make the camera "slave" for the mouse. (rotate in the angle the mouse is moved in)
	 * 
	 * @param delta
	 */
	private void slaveForMouse(int delta){
		this.xrot -= Mouse.getDY();
		this.yrot += Mouse.getDX();
	}
	
	/**
	 * This function is used to make the camera "slave" for the keyboard...
	 * 
	 * @param delta
	 */
	private void slaveForKeyboard(int delta){
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
	}


	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {

	}

	/**
	 * This function is used to obtain the block that the camera is currently "looking" at.
	 * 
	 * @return the block that the camera is facing.
	 */
	public Block getFacingBlock(){
		Ray ray = new Ray(this.scene, x, y, z);
		
		for(int range = 0; range < 16; range++){
			ray.x += (float) Math.sin(Math.toRadians(getYaw()));
			ray.z -= (float) Math.cos(Math.toRadians(getYaw()));
			ray.y -= (float) Math.tan(Math.toRadians(getPitch()));
			Chunk chunk = ray.getChunk();
			if(chunk.isLoaded()){
				Block[][][] blocks = chunk.blocks;

				int _x = (int)Smart.mod(ray.x, Chunk.WIDTH);
				int _y = (int)Smart.mod(ray.y, Chunk.HEIGHT);
				int _z = (int)Smart.mod(ray.z, Chunk.WIDTH);

				if(_x >= 0 && _x <= ((World)scene).chunk_number*16 && _z >= 0 && _z <= ((World)scene).chunk_number*16 && _y >= 0 && _y <= ((World)scene).chunk_number*16 ){

					Block block = blocks[_x][_y][_z];
					if(!block.getType().equals(BlockType.AIR)){
						return block.intersectsWithRay(ray);
					}

				}
			}
		}

		return null;
	}
	
	/**
	 * This function is used to "place" a block wherever the camera is looking. (it actually set's the type of the facing block)
	 * 
	 * @param type
	 */
	public void placeBlock(BlockType type){
		Block block = getFacingBlock();
		if(block != null){
			if(block.ray != null){
				Vector3f b_v = new Vector3f(block.x, block.y, block.z);
				Vector3f hit = new Vector3f(block.ray.x, block.ray.y, block.ray.z);


				float angle =Vector3f.angle(b_v, hit);


				float placex = block.ray.getX();
				float placey = block.ray.getY();
				float placez = block.ray.getZ();
				
				//ray.x += (float) Math.sin(Math.toRadians(getYaw()));
				//ray.y -= (float) Math.tan(Math.toRadians(getPitch()));
				//ray.z -= (float) Math.cos(Math.toRadians(getYaw()));
				

				placex -= Math.cos(angle)*16;
				placey -= Math.tan(angle)*16;
				placez += Math.sin(angle)*16;



				//top
				//if (angle >= 180 && angle <= 0)
				/*int placex = (int)((b_v.x%(16*16))/16);
				int placey = (int)(((b_v.y+1)%(16*256))/16);
				int placez = (int)((b_v.z%(16*16))/16);*/

				if(block.ray.getChunk().getBlock(Smart.mod(placex, Chunk.WIDTH), Smart.mod(placey, Chunk.HEIGHT), Smart.mod(placez, Chunk.WIDTH)).getType().equals(BlockType.AIR)){
					block.ray.getChunk().setBlock(Smart.mod(placex, Chunk.WIDTH), Smart.mod(placey, Chunk.HEIGHT), Smart.mod(placez, Chunk.WIDTH), BlockType.COBBLE);
					
				}

				}
			}
		}



	}
