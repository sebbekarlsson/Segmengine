package rt.main;

import java.util.ArrayList;

import rt.main.physics.Hitbox;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.World;
import rt.main.utils.Smart;

public abstract class Actor {

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

	public void update(int delta){
		tick(delta);
		if(draw)
			draw(delta);
	}

	public abstract void init();
	public abstract void tick(int delta);
	public abstract void draw(int delta);
	public abstract void preparedCollision(Actor actor, float timex, float timey, float timez);

	public boolean isInitialized(){
		return this.initialized;
	}

	public void initialize(){
		init();
		this.initialized = true;
	}

	public Hitbox getHitbox(){
		return this.hitbox;
	}

	public void setHitbox(Hitbox hitbox){
		this.hitbox = hitbox;
	}

	public boolean isSolid(){
		return this.solid;
	}

	public void setSolid(boolean solid){
		this.solid = solid;
	}

	public boolean isTransparent(){
		return this.transparent;
	}

	public void setTransparent(boolean transparent){
		this.transparent = transparent;
	}

	public float getX(){
		return this.x;
	}

	public float getY(){
		return this.y;
	}

	public float getZ(){
		return this.z;
	}

	public Chunk getChunk(){
		Chunk[][] chunks = ((World) scene).chunks;
		return chunks[((int)(this.x/(Chunk.WIDTH)))][((int)(this.z/(Chunk.WIDTH)))];
	}

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

	public boolean updatesChunks(){
		return this.updateChunks;
	}
}
