package rt.main.actors;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;
import rt.main.utils.Smart;



public abstract class Entity extends Actor {
	
	/*
	 * Setting the entity's delta-positions to zero.
	 */
	protected float dx, dy, dz = 0;
	
	/*
	 * Setting the entity's default friction.
	 */
	protected float friction = 0.0002f;
	
	/*
	 * Setting the entity's default weight.
	 */
	protected float weight = 0.0006f;
	
	/*
	 * This variable will later contain the chunk from the last frame.s
	 */
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
	
	/**
	 * This function is used to update the position of the object using some basic Physics.
	 * This function is also used to check for collisions and act upon those events.
	 * 
	 * @param delta the delta-time.
	 */
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

			/*
			 * Getting all the possible blocks that could be collided with on the positive x-axis
			 */
			Block next_block_1_x1_left = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x1_right = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y, Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x2_left = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_1_x2_right = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			
			/*
			 * Getting all the possible blocks that could be collided with on the negative x-axis
			 */
			Block next_block_2_x1_left = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH));
			Block next_block_2_x1_right = chunk.getBlock(Smart.mod(next_x-getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_2_x2_left = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH), Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z-getHitbox().getWidth()/2), Chunk.WIDTH) );
			Block next_block_2_x2_right = chunk.getBlock(Smart.mod(next_x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod((z+getHitbox().getWidth()/2), Chunk.WIDTH) );
			
			/*
			 * Getting all the possible blocks that could be collided with on the negative y-axis
			 */
			Block next_block_y1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH));
			Block next_block_y1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z, Chunk.WIDTH) );
			Block next_block_y1_up = chunk.getBlock(Smart.mod(x, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z+getHitbox().getWidth()/2, Chunk.WIDTH) );
			Block next_block_y1_down = chunk.getBlock(Smart.mod(x, Chunk.WIDTH) , Smart.mod(next_y, Chunk.HEIGHT), Smart.mod(z-getHitbox().getWidth()/2, Chunk.WIDTH) );
			
			/*
			 * Getting all the possible blocks that could be collided with on the positive z-axis
			 */
			Block next_block_1_z1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z2_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_1_z2_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y, Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );
			
			/*
			 * Getting all the possible blocks that could be collided with on the negative z-axis
			 */
			Block next_block_2_z1_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z1_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z-getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z2_left = chunk.getBlock(Smart.mod(x-getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );
			Block next_block_2_z2_right = chunk.getBlock(Smart.mod(x+getHitbox().getWidth()/2, Chunk.WIDTH) , Smart.mod(y+getHitbox().getHeight(), Chunk.HEIGHT), Smart.mod(next_z+getHitbox().getLength()/2, Chunk.WIDTH) );

			
			/*
			 * If the entity is inside of a block it should be pushed on top of it.
			 */
			if(next_block.isSolid()){
				y = next_block.y+next_block.getHitbox().getHeight();
			}

			/*
			 * If there is no block at the x-axis on the next frame, then move. Else don't.
			 */
			if(
					!next_block_1_x1_left.isSolid() && !next_block_1_x1_right.isSolid() && !next_block_1_x2_left.isSolid() && !next_block_1_x2_right.isSolid() &&
					!next_block_2_x1_left.isSolid() && !next_block_2_x1_right.isSolid() && !next_block_2_x2_left.isSolid() && !next_block_2_x2_right.isSolid() 
					){
				x += (dx * delta);
			}else{
				dx = 0;
			}
			/*
			 * If there is no block at the y-axis on the next frame, then move. Else don't.
			 */
			if(!next_block_y1_right.isSolid() && !next_block_y1_left.isSolid() && !next_block_y1_up.isSolid() && !next_block_y1_down.isSolid()){
				y += (dy * delta);

			}else{
				
				/*
				 * If there is a block at the y-axis on the next frame then we can jump.
				 * This piece should probably not be here later, we'll put it inside a function and call it whenever needed.
				 */
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					dy += 0.00060f * delta;
				}else{
					dy = 0 * delta;
				}
			}
			/*
			 * If there is no block at the z-axis on the next frame, then move. Else don't.
			 */
			if(
					!next_block_1_z1_left.isSolid() && !next_block_1_z1_right.isSolid() && !next_block_1_z2_left.isSolid() && !next_block_1_z2_right.isSolid() &&
					!next_block_2_z1_left.isSolid() && !next_block_2_z1_right.isSolid() && !next_block_2_z2_left.isSolid() && !next_block_2_z2_right.isSolid()
					){
				z += (dz * delta);
			}else{
				dz = 0;
			}

		}
		
		/*
		 * Decrease our delta-positions to zero with the entities friction multiplied with the delta-time.
		 */
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

		/*
		 * Here we check if the last chunk isn't the same as the current chunk. If it isn't, we call the onChunkLeave() function.
		 */
		if(old_chunk != null){
			if(old_chunk != getChunk()){
				onChunkLeave(old_chunk, delta);
			}
		}
		
		/*
		 * We want to remember what chunk we were in, in the last frame so that we know when we enter a new chunk.
		 */
		old_chunk = getChunk();

	}
	
	/**
	 * 
	 * @return the delta-x position of the entity.
	 */
	public float getDx(){
		return this.dx;
	}

	/**
	 * 
	 * @return the delta-y position of the entity.
	 */
	public float getDy(){
		return this.dy;
	}

	/**
	 * 
	 * @return the delta-z position of the entity.
	 */
	public float getDz(){
		return this.dz;
	}
	
	/**
	 * 
	 * @return the entity's friction amount.
	 */
	public float getFriction(){
		return this.friction;
	}

	/**
	 * 
	 * @return the entity's weight.
	 */
	public float getWeight(){
		return this.weight;
	}

	/**
	 * This function is used to set how much friction the entity will have/use.
	 * 
	 * @param friction the amount of friction that this entity will have.
	 */
	public void setFriction(float friction){
		this.friction = friction;
	}

	/**
	 * This function is used to set how much weight the entity will have.
	 * 
	 * @param weight the amount of weight that this entity will have.
	 */
	public void setWeight(float weight){
		this.weight = weight;
	}
	
	/**
	 * This function initializes a chunk if it hasn't already been initialized.
	 * This function loads a chunk if it hasn't already been loaded and if the chunk-file exists.
	 * This function generates the chunk if it hasn't already been loaded and if the chunk-file does not exists.
	 * 
	 * @param delta the delta-time.
	 * @param chunk what chunk to update.
	 */
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
				chunk.save();

			}else{
				chunk.load();
				chunk.tickle();
			}
		}

	}
	
	/**
	 * This function initializes, updates, loads and generates all of the visible chunks to the entity.
	 * 
	 * @param delta the delta-time.
	 */
	private void updateChunks(int delta){
		ArrayList<Chunk> chunks = getVisibleChunks(2);
		for(int i = 0; i < chunks.size(); i++){
			updateChunk(delta, chunks.get(i));
		}
	}
	
	/**
	 * This function is called when the chunk from last frame isn't the same as the current chunk in the current frame.
	 * This function is called when the entity enters a new chunk.
	 * 
	 * @param chunk the chunk.
	 * @param delta the delta time.
	 */
	private void onChunkLeave(Chunk chunk, int delta){
		chunk.unload();
	}
}
