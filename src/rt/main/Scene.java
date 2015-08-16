package rt.main;

import java.awt.Color;
import java.util.ArrayList;

import rt.main.actors.Entity;

public abstract class Scene {
	
	public Camera camera = new Camera(this, 0, 0, 0);
	protected ArrayList<Actor> actors = new ArrayList<Actor>();
	private boolean initialized = false;
	public Color backgroundColor = Color.black;
	
	public void update(int delta){
		tick(delta);
		draw(delta);
		
		for(int i = 0; i < actors.size(); i++){
			actors.get(i).update(delta);
			actors.get(i).getHitbox().update();
			
			
			
			if(!actors.get(i).isInitialized()){
				actors.get(i).initialize();
			}
		}
	}
	
	public abstract void init();
	public abstract void tick(int delta);
	public abstract void draw(int delta);

	public Camera getCamera(){
		return this.camera;
	}
	
	public void stageActor(Actor actor){
		actors.add(actor);
	}
	
	public void removeActor(Actor actor){
		actors.remove(actor);
	}
	
	public void removeActor(int index){
		actors.remove(index);
	}
	
	public boolean isInitialized(){
		return this.initialized;
	}
	
	public void initialize(){
		init();
		this.initialized = true;
	}
	
	public ArrayList<Actor> getActors(){
		return this.actors;
	}
	
	public ArrayList<Entity> getEntities(){
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(int i = 0; i < getActors().size(); i++){
			if(getActors().get(i) instanceof Entity){
				entities.add((Entity)getActors().get(i));
			}
		}
		return entities;
	}
}
