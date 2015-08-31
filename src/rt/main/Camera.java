package rt.main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

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
		/*Block facing_block = getFacingBlock();
		if(facing_block != null)
		facing_block.getHitbox().setDraw(true);*/
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
		
		for(int range = 0; range < 6*16; range++){
			ray.x += (float) Math.sin(Math.toRadians(getYaw()));
			ray.z -= (float) Math.cos(Math.toRadians(getYaw()));
			ray.y -= (float) Math.tan(Math.toRadians(getPitch()));
			Chunk chunk = ray.getChunk();
			if(chunk.isLoaded()){
				Block[][][] blocks = chunk.blocks;
				float mod = (Block.SIZE * Chunk.HEIGHT);
				int mod_div = Block.SIZE;

				int _x = (int)ray.x%(16*16)/ 16;
				int _y = (int)(ray.y%(mod) / mod_div);
				int _z = (int)ray.z%(16*16)/ 16;

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

				if(block.ray.getChunk().getBlock((int)(placex%(16*16) / 16), (int)(placey%(16*256) / 16), (int)(placez%(16*16) / 16)).getType().equals(BlockType.AIR)){
					block.ray.getChunk().setBlock((int)(placex%(16*16) / 16), (int)(placey%(16*256) / 16), (int)(placez%(16*16) / 16), BlockType.COBBLE);
				}



				}
			}
		}



	}
