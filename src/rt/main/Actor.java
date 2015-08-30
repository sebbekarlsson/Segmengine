package rt.main;

import rt.main.physics.Hitbox;
import rt.main.scenes.worlds.Chunk;
import rt.main.scenes.worlds.World;

public abstract class Actor {
	
	public float x,y,z,xrot,yrot,zrot = 0f;
	private boolean initialized = false;
	public Scene scene;
	private Hitbox hitbox;
	private boolean solid = true;
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
		return chunks[((int)(this.x/(16*16)))][((int)(this.z/(16*16)))];
	}
	
	public boolean updatesChunks(){
		return this.updateChunks;
	}
}
