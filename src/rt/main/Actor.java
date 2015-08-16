package rt.main;

import rt.main.physics.Hitbox;

public abstract class Actor {
	
	public float x,y,z,xrot,yrot,zrot = 0f;
	private boolean initialized = false;
	public Scene scene;
	private Hitbox hitbox;
	private boolean solid = true;
	
	public Actor(Scene scene, float x, float y, float z){
		this.scene = scene;
		this.x = x;
		this.y = y;
		this.z = z;
		this.hitbox = new Hitbox(this, 4,4,4);
	}
	
	public void update(int delta){
		tick(delta);
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
}
