package rt.main.actors;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;
import rt.main.utils.Smart;



public abstract class Entity extends Actor {

	protected float dx, dy, dz = 0;
	protected float friction = 0.0002f;
	protected float weight = 0.0006f;
	protected boolean onGround = false;
	protected Chunk old_chunk = null;


	public Entity(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	public void update(int delta){
		if(this.updatesChunks()){
			updateChunks(delta);
		}
		updatePhysics(delta);
		tick(delta);
		draw(delta);
	}

	private void updatePhysics(int delta){
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			dy -= (weight);
		}
		Chunk chunk = getChunk();
		if(chunk.isLoaded()){

			float next_x = x + (dx * delta);
			float next_y = y + (dy * delta);
			float next_z = z + (dz * delta);

			Block next_block = chunk.getBlock(Smart.mod(x, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH));

			Block next_block_1_x1_left = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x1_right = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x2_left = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x2_right = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );

			Block next_block_2_x1_left = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH));
			Block next_block_2_x1_right = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_2_x2_left = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_2_x2_right = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );

			Block next_block_y1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH));
			Block next_block_y1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH) );
			Block next_block_y1_up = chunk.getBlock(Smart.mod(x, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z+getHitbox().getWidth()/2, Chunk.WIDTH) );
			Block next_block_y1_down = chunk.getBlock(Smart.mod(x, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z-getHitbox().getWidth()/2, Chunk.WIDTH) );

			Block next_block_1_z1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z2_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z2_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );

			Block next_block_2_z1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z2_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z2_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );


			if(next_block.isSolid()){
				y = next_block.y+next_block.getHitbox().getHeight();
			}

			if(
					!next_block_1_x1_left.isSolid() && !next_block_1_x1_right.isSolid() && !next_block_1_x2_left.isSolid() && !next_block_1_x2_right.isSolid() &&
					!next_block_2_x1_left.isSolid() && !next_block_2_x1_right.isSolid() && !next_block_2_x2_left.isSolid() && !next_block_2_x2_right.isSolid() 
					){
				x += (dx * delta);
			}else{
				dx = 0;
			}
			if(!next_block_y1_right.isSolid() && !next_block_y1_left.isSolid() && !next_block_y1_up.isSolid() && !next_block_y1_down.isSolid()){
				y += (dy * delta);

			}else{
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					dy += 0.00060f * delta;
				}else{
					dy = 0 * delta;
				}
			}
			if(
					!next_block_1_z1_left.isSolid() && !next_block_1_z1_right.isSolid() && !next_block_1_z2_left.isSolid() && !next_block_1_z2_right.isSolid() &&
					!next_block_2_z1_left.isSolid() && !next_block_2_z1_right.isSolid() && !next_block_2_z2_left.isSolid() && !next_block_2_z2_right.isSolid()
					){
				z += (dz * delta);
			}else{
				dz = 0;
			}

		}

		if(dx > 0){
			if(dx - friction * delta < 0){
				dx = 0;
			}else{
				dx -= friction * delta;
			}
		}
		if(dx < 0){
			if(dx + friction * delta > 0){
				dx = 0;
			}else{
				dx += friction * delta;
			}
		}
		if(dz > 0){
			if(dz - friction * delta < 0){
				dz = 0;
			}else{
				dz -= friction * delta;
			}
		}
		if(dz < 0){
			if(dz + friction * delta > 0){
				dz = 0;
			}else{
				dz += friction * delta;
			}
		}

		if(old_chunk != null){
			if(old_chunk != getChunk()){
				onChunkLeave(old_chunk, delta);
			}
		}

		old_chunk = getChunk();

	}

	public float getDx(){
		return this.dx;
	}

	public float getDy(){
		return this.dy;
	}

	public float getDz(){
		return this.dz;
	}

	public float getFriction(){
		return this.friction;
	}

	public float getWeight(){
		return this.weight;
	}

	public void setFriction(float friction){
		this.friction = friction;
	}

	public void setWeight(float weight){
		this.weight = weight;
	}

	private void updateChunk(int delta, Chunk chunk){
		if(!chunk.isInitialized()){
			chunk.initialize();
		}else{
			chunk.update(delta);
		}
		if(!chunk.isLoaded()){
			if(!chunk.chunkFileExists()){
				chunk.setAir();
				chunk.generate();
				chunk.tickle();
				chunk.getBlock(3, 3, 3).tickle();
				chunk.save();

			}else{
				chunk.load();
				chunk.tickle();
				chunk.getBlock(3, 3, 3).tickle();
			}
		}

	}

	private void updateChunks(int delta){
		ArrayList<Chunk> chunks = getVisibleChunks(2);
		for(int i = 0; i < chunks.size(); i++){
			updateChunk(delta, chunks.get(i));
		}
	}

	private void onChunkLeave(Chunk chunk, int delta){
		chunk.unload();
	}
}
