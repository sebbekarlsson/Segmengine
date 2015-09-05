package rt.main.actors;

import rt.main.Actor;
import rt.main.Camera;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.Ray;
import rt.main.types.BlockType;
import rt.main.utils.Smart;

public class Block extends BoxActor {
	
	/*
	 * Variable that holds the type of the block. For example BlockType.COBBLE
	 */
	private BlockType blocktype;
	
	/*
	 * This is probably not a solution, let's not talk about that.
	 */
	public Ray ray;
	
	/*
	 * Setting the size in units of the block.
	 * This should probably be hard-coded...
	 */
	public static int SIZE = 1;

	public Block(Scene scene, float x, float y, float z, BlockType blocktype) {
		super(scene, x, y, z);
		this.blocktype = blocktype;
	}

	@Override
	public void init() {
		setType(this.blocktype);

	}

	@Override
	public void tick(int delta) {
		Camera camera = scene.getCamera();
		if(camera.frustum.cubeInFrustum(x+SIZE, y+SIZE, z+SIZE, x, y, z)){
			draw = true;
		}else{ 
			draw = false;
		}
		tickle();
	}

	@Override
	public void draw(int delta) {

	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey,
			float timez) {

	}

	/**
	 * 
	 * @return the type of the block. (BlockType enumeration)
	 */
	public BlockType getType(){
		return this.blocktype;
	}

	/**
	 * Used to set the BlockType enumeration for the block.
	 * This function also updates the size, texture and transparency in case of a change.
	 * 
	 * @param blocktype the type of the block. For example: BlockType.COBBLE. (enumeration)
	 * @return this block.
	 */
	public Block setType(BlockType blocktype){
		this.blocktype = blocktype;
		this.setSize(SIZE, SIZE, SIZE);
		this.getHitbox().setWidth(SIZE);
		this.getHitbox().setHeight(SIZE);
		this.getHitbox().setLength(SIZE);
		this.setSolid(blocktype.solid);
		this.setTransparent(blocktype.transparent);
		this.getSide("left").texture = blocktype.texture_left;
		this.getSide("right").texture = blocktype.texture_right;
		this.getSide("top").texture = blocktype.texture_top;
		this.getSide("bottom").texture = blocktype.texture_bottom;
		this.getSide("back").texture = blocktype.texture_back;
		this.getSide("front").texture = blocktype.texture_front;

		return this;
	}

	/**
	 * This function's purpose is to prevent drawing of sides that are blocked by another block.
	 * This function will also do a lot of other stuff that we DONT'T want to happen every frame.
	 * This is an update that only happens under certain circumstances.
	 * 
	 * @return this block.
	 */
	public Block tickle(){

		for(int i = 0; i < this.sides.length; i++){
			this.sides[i].draw = true;
		}

		Chunk chunk = getChunk();

		if(chunk.isLoaded()){

			float mod = (Block.SIZE * Chunk.HEIGHT);
			int mod_div = Block.SIZE;

			if(Smart.mod(this.x, Chunk.WIDTH) > 0 && Smart.mod(this.x, Chunk.WIDTH) < Chunk.WIDTH &&
					Smart.mod(this.z, Chunk.WIDTH) > 0 && Smart.mod(this.z, Chunk.WIDTH) < Chunk.WIDTH){
				
				Block block_top = chunk.getBlock((int)(x/SIZE)%16, Math.min(Chunk.HEIGHT, (int)((y+Block.SIZE)%(mod)) / mod_div), (int)(z/SIZE)%16);
				if(block_top.isSolid()){
					if(!(block_top.isTransparent()))
						this.getSide("top").draw = false;
				}
				Block block_bottom = chunk.getBlock((int)(x/SIZE)%16, Math.max(0, (int)((y-Block.SIZE)%(mod)) / mod_div), (int)(z/SIZE)%16);
				if(block_bottom.isSolid()){
					if(!(block_bottom.isTransparent()))
						this.getSide("bottom").draw = false;
				}
				Block block_left = chunk.getBlock(Math.max(0, (int)((x-Block.SIZE)/SIZE)%16), (int)((y)%(mod)) / mod_div, (int)(z/SIZE)%16);
				if(block_left.isSolid()){
					if(!(block_left.isTransparent()))
						this.getSide("left").draw = false;
				}
				Block block_right = chunk.getBlock(Math.min(16, (int)((x+Block.SIZE)/SIZE)%16), (int)((y)%(mod)) / mod_div, (int)(z/SIZE)%16);
				if(block_right.isSolid()){
					if(!(block_right.isTransparent()))
						this.getSide("right").draw = false;
				}
				Block block_front = chunk.getBlock((int)(x/SIZE)%16, (int)((y)%(mod)) / mod_div, Math.min(16, (int)((z+Block.SIZE)/SIZE)%16));
				if(block_front.isSolid()){
					if(!(block_front.isTransparent()))
						this.getSide("front").draw = false;
				}
				Block block_back = chunk.getBlock((int)(x/SIZE)%16, (int)((y)%(mod)) / mod_div, Math.max(0, (int)((z-Block.SIZE)/SIZE)%16));
				if(block_back.isSolid()){
					if(!(block_back.isTransparent()))
						this.getSide("back").draw = false;
				}
			}
		}

		return this;

	}
	
	/**
	 * This function is used to set the size in units for the block.
	 * 
	 * @deprecated this function is deprecated since block-sizes will be hard-coded in the future.
	 * @param size the size of the block in units.
	 * @return this block.
	 */
	public Block setSize(int size){
		SIZE = size;
		this.setType(this.blocktype);

		return this;
	}
	
	/**
	 * This function is used to get the size in units of the block.
	 * 
	 * @deprecated this function is deprecated since block-sizes will be hard-coded in the future.
	 * @return the size in units of the block.
	 */
	public float getSize(){
		return SIZE;
	}
	
	/**
	 * This function is used to check if a block intersects with an array.
	 * 
	 * @param ray a ray object.
	 * @return a boolean value if ray exists, else it will return null.
	 */
	public Block intersectsWithRay(Ray ray){
		if(ray.x >= this.x-(SIZE/2) && ray.x <= this.x+(SIZE*2) && ray.z >= this.z-(SIZE/2) && ray.z <= this.z+(SIZE*2) && ray.y >= this.y-(SIZE/2) && ray.y <= this.y+(SIZE*2)){
			this.ray = ray;
			return this;
		}
		else if(ray.x >= this.x && ray.x <= this.x && ray.z >= this.z && ray.z <= this.z && ray.y >= this.y && ray.y <= this.y){
			this.ray = ray;
			return this;
		}

		return null;
	}

}
