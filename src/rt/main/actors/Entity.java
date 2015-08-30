package rt.main.actors;


import org.lwjgl.input.Keyboard;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.scenes.worlds.Chunk;



public abstract class Entity extends Actor {

	protected float dx, dy, dz = 0;
	protected float friction = 0.0002f;
	protected float weight = 0.0003f;
	protected boolean onGround = false;


	public Entity(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	public void update(int delta){
		updateChunk(delta);
		updatePhysics(delta);
		tick(delta);
		draw(delta);
	}

	private void updatePhysics(int delta){

		dy -= (weight * delta);
		Chunk chunk = getChunk();

		if(chunk.isLoaded()){

			float next_x = x + (dx * delta);
			float next_y = y + (dy * delta);
			float next_z = z + (dz * delta);
			
			float mod = (Block.SIZE * Chunk.HEIGHT);
			int mod_div = Block.SIZE;

			Block next_block = chunk.getBlock((int)(x%(16*16)/ 16), (int)Math.min((y%(mod)) / mod_div, Chunk.HEIGHT), (int)z%(16*16) / 16);

			Block next_block_1_x1_left = chunk.getBlock((int)((next_x-getHitbox().getWidth()/2)%(16*16))/ 16, (int)(y%(mod)) / mod_div, (int)((z-getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_1_x1_right = chunk.getBlock((int)((next_x-getHitbox().getWidth()/2)%(16*16))/ 16, (int)(y%(mod)) / mod_div, (int)((z+getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_1_x2_left = chunk.getBlock((int)((next_x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod)) / mod_div, (int)((z-getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_1_x2_right = chunk.getBlock((int)((next_x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod))/ mod_div, (int)((z+getHitbox().getWidth()/2)%(16*16)) / 16);

			Block next_block_2_x1_left = chunk.getBlock((int)((next_x-getHitbox().getWidth()/2)%(16*16))/ 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((z-getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_2_x1_right = chunk.getBlock((int)((next_x-getHitbox().getWidth()/2)%(16*16))/ 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((z+getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_2_x2_left = chunk.getBlock((int)((next_x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((z-getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_2_x2_right = chunk.getBlock((int)((next_x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((z+getHitbox().getWidth()/2)%(16*16)) / 16);

			Block next_block_y1_right = chunk.getBlock((int)((x+getHitbox().getWidth()/2)%(16*16)) / 16, Math.max(0, (int)(next_y%(mod)) / mod_div), (int)(z%(16*16)) / 16);
			Block next_block_y1_left = chunk.getBlock((int)((x-getHitbox().getWidth()/2)%(16*16)) / 16, Math.max(0, (int)(next_y%(mod)) / mod_div), (int)(z%(16*16)) / 16);
			Block next_block_y1_up = chunk.getBlock((int)(x%(16*16)) / 16, Math.max(0, (int)(next_y%(mod)) / mod_div), (int)((z+getHitbox().getWidth()/2)%(16*16)) / 16);
			Block next_block_y1_down = chunk.getBlock((int)(x%(16*16)) / 16, Math.max(0, (int)(next_y%(mod)) / mod_div), (int)((z-getHitbox().getWidth()/2)%(16*16)) / 16);

			Block next_block_1_z1_left = chunk.getBlock((int)((x-getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod)) / mod_div, (int)((next_z-getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_1_z1_right = chunk.getBlock((int)((x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod)) / mod_div, (int)((next_z-getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_1_z2_left = chunk.getBlock((int)((x-getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod)) / mod_div, (int)((next_z+getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_1_z2_right = chunk.getBlock((int)((x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)(y%(mod)) / mod_div, (int)((next_z+getHitbox().getLength()/2)%(16*16)) / 16);

			Block next_block_2_z1_left = chunk.getBlock((int)((x-getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((next_z-getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_2_z1_right = chunk.getBlock((int)((x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((next_z-getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_2_z2_left = chunk.getBlock((int)((x-getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((next_z+getHitbox().getLength()/2)%(16*16)) / 16);
			Block next_block_2_z2_right = chunk.getBlock((int)((x+getHitbox().getWidth()/2)%(16*16)) / 16, (int)((y+getHitbox().getHeight())%(mod)) / mod_div, (int)((next_z+getHitbox().getLength()/2)%(16*16)) / 16);

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
					dy += 0.007f * delta;
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

		}else{
			x += (dx * delta);
			y += (dy * delta);
			z += (dz * delta);
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

	private void updateChunk(int delta){
		if(updateChunks){
			if(!getChunk().isInitialized()){
				getChunk().initialize();
			}else{
				getChunk().update(delta);
			}
			if(!getChunk().isLoaded()){
				if(!getChunk().chunkFileExists()){

					getChunk().setAir();
					getChunk().generate();
					getChunk().save();

				}else{
					getChunk().load();
				}
			}



		}
	}
}
