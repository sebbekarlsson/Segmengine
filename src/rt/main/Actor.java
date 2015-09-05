package rt.main;

import java.util.ArrayList;

import rt.main.physics.Hitbox;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.World;
import rt.main.utils.Smart;

public abstract class Actor {

	/*
	 * Setting our position and initializing other important variables.
	 */
	public float x,y,z,xrot,yrot,zrot = 0f;
	private boolean initialized = false;
	public Scene scene;
	private Hitbox hitbox;
	private boolean solid = true;
	private boolean transparent = false;
	public boolean draw = true;
	protected boolean updateChunks = false;

	public Actor(Scene scene, float x, float y, float z){
		this.scene = scene;
		this.x = x;
		this.y = y;
		this.z = z;
		this.hitbox = new Hitbox(this, 4,4,4);
	}
	
	/**
	 * Updates our actor and passes through with the current delta-time.
	 * 
	 * @param delta
	 */
	public void update(int delta){
		
		/*
		 * Updating the actor's logic and graphics.
		 */
		tick(delta);
		if(draw){
			draw(delta);
		}
	}
	
	/**
	 * Initializes the actor.
	 */
	public abstract void init();
	
	/**
	 * Updates the actor logic.
	 * 
	 * @param delta
	 */
	public abstract void tick(int delta);
	
	/**
	 * Updates the actor graphics.
	 * 
	 * @param delta
	 */
	public abstract void draw(int delta);
	
	/**
	 * This is not being used at all but was once used for collision checking.
	 * This is deprecated and will be removed in a future version.
	 * 
	 * @param actor
	 * @param timex
	 * @param timey
	 * @param timez
	 */
	public abstract void preparedCollision(Actor actor, float timex, float timey, float timez);
	
	/**
	 * Checks if the actor has been initialized
	 * 
	 * @return boolean
	 */
	public boolean isInitialized(){
		return this.initialized;
	}
	
	/**
	 * This function is used to call our init() function and then sets the actor.initialized to true.
	 */
	public void initialize(){
		init();
		this.initialized = true;
	}

	/**
	 * Used to get the actor's hitbox object.
	 * 
	 * @return (Object) Hitbox
	 */
	public Hitbox getHitbox(){
		return this.hitbox;
	}
	
	/**
	 * Sets the actor's hitbox.
	 * 
	 * @param (Hitbox) hitbox
	 */
	public void setHitbox(Hitbox hitbox){
		this.hitbox = hitbox;
	}
	
	/**
	 * Checks if the actor is a solid object.
	 * 
	 * @return boolean
	 */
	public boolean isSolid(){
		return this.solid;
	}

	/**
	 * Sets the actor's solid variable to a selected boolean.
	 * 
	 * @param boolean
	 */
	public void setSolid(boolean solid){
		this.solid = solid;
	}
	
	/**
	 * Checks if actor is transparent. (Mostly texture-wise, could still be solid and transparent at the same time)
	 * 
	 * @return boolean
	 */
	public boolean isTransparent(){
		return this.transparent;
	}

	/**
	 * Sets the actor's transparent variable to a selected boolean.
	 * 
	 * @param boolean
	 */
	public void setTransparent(boolean transparent){
		this.transparent = transparent;
	}
	
	/**
	 * Get the X position of the actor
	 * 
	 * @return float
	 */
	public float getX(){
		return this.x;
	}

	/**
	 * Get the Y position of the actor
	 * 
	 * @return float
	 */
	public float getY(){
		return this.y;
	}

	/**
	 * Get the Z position of the actor
	 * 
	 * @return float
	 */
	public float getZ(){
		return this.z;
	}

	/**
	 * Used to get the actor's current chunk depending on it's x, y, z variables.
	 * 
	 * @return (Object) Chunk
	 */
	public Chunk getChunk(){
		Chunk[][] chunks = ((World) scene).chunks;
		return chunks[((int)(this.x/(Chunk.WIDTH)))][((int)(this.z/(Chunk.WIDTH)))];
	}
	
	/**
	 * Used to get the visible chunks from the actor depending on a radius variable.
	 * 
	 * @param radius
	 * @return Arraylist<Chunk>
	 */
	public ArrayList<Chunk> getVisibleChunks(int radius){

		ArrayList<Chunk> chunklist = new ArrayList<Chunk>();
		World world = ((World)scene);


		for(int xx = Smart.mod(((this.x/Chunk.WIDTH)-radius/2), world.chunk_number); xx <= Smart.mod(((this.x/Chunk.WIDTH)+radius/2), world.chunk_number); xx++){
			for(int zz = Smart.mod(((this.z/Chunk.WIDTH)-radius/2), world.chunk_number); zz <= Smart.mod(((this.z/Chunk.WIDTH)+radius/2), world.chunk_number); zz++){
				chunklist.add(world.getChunk(xx, zz));
			}

		}

		return chunklist;

	}

	/**
	 * Checks if actor can update chunks.
	 * 
	 * @return boolean
	 */
	public boolean updatesChunks(){
		return this.updateChunks;
	}
}
