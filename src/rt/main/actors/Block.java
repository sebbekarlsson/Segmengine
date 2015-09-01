package rt.main.actors;

import rt.main.Actor;
import rt.main.Camera;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.Ray;
import rt.main.types.BlockType;

public class Block extends BoxActor {

	private BlockType blocktype;
	public Ray ray;
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
		if(camera.frustum.cubeInFrustum(x+SIZE, y+SIZE, z+SIZE/2, x, y, z)){
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
		// TODO Auto-generated method stub

	}

	public BlockType getType(){
		return this.blocktype;
	}

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

		tickle();
		
		return this;
	}
	
	public Block tickle(){

		for(int i = 0; i < this.sides.length; i++){
			this.sides[i].draw = true;
		}

		Chunk chunk = getChunk();

		if(chunk.isLoaded()){
			
			float mod = (Block.SIZE * Chunk.HEIGHT);
			int mod_div = Block.SIZE;
			
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

		return this;

	}

	public Block setSize(int size){
		SIZE = size;
		this.setType(this.blocktype);

		return this;
	}

	public float getSize(){
		return SIZE;
	}
	
	public Block intersectsWithRay(Ray ray){
		 if(ray.x >= this.x-(SIZE/2) && ray.x <= this.x+(SIZE*2) && ray.z >= this.z-(SIZE/2) && ray.z <= this.z+(SIZE*2) && ray.y >= this.y-(SIZE/2) && ray.y <= this.y+(SIZE*2)){
			 this.ray = ray;
			 return this;
		 }
		 
		 return null;
	}
	
}
