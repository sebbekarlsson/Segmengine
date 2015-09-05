package rt.main;

import java.awt.Color;
import java.util.ArrayList;

import rt.main.actors.Entity;

public abstract class Scene {
	
	/*
	 * The scene obviously needs a camera...
	 */
	public Camera camera = new Camera(this, 0, 0, 0);
	
	/*
	 * Creating a buffer that will be used to update & draw all of our objects (actors) .
	 */
	protected ArrayList<Actor> actors = new ArrayList<Actor>();
	
	/*
	 * This variable is used later to do initializing.
	 */
	private boolean initialized = false;
	
	/*
	 * Setting the default background color for a scene.
	 */
	public Color backgroundColor = Color.black;
	
	/**
	 * Used to update the current state of the scene.
	 * 
	 * @param delta an Integer (the current delta-time) .
	 */
	public void update(int delta){
		/*
		 * Updating the scene's logic & graphics.
		 */
		tick(delta);
		draw(delta);
		
		/*
		 * Updating and initializing all of the actors.
		 */
		for(int i = 0; i < actors.size(); i++){
			actors.get(i).update(delta);
			actors.get(i).getHitbox().update();
			
			if(!actors.get(i).isInitialized()){
				actors.get(i).initialize();
			}
		}
	}
	
	/**
	 * Used to initialize the scene.
	 */
	public abstract void init();
	
	/**
	 * Used to update the scene's logic.
	 * 
	 * @param delta an Integer (the current delta-time) .
	 */
	public abstract void tick(int delta);
	
	/**
	 * Used to draw the scene's graphics.
	 * 
	 * @param delta an Integer (the current delta-time) .
	 */
	public abstract void draw(int delta);

	/**
	 * 
	 * @return the camera for this scene.
	 */
	public Camera getCamera(){
		return this.camera;
	}
	
	/**
	 * Adds an actor to our actor-buffer.
	 * 
	 * @param actor the selected actor to add.
	 */
	public void stageActor(Actor actor){
		actors.add(actor);
	}
	
	/**
	 * Removes an actor from the scene's actor-buffer.
	 * 
	 * @param actor
	 */
	public void removeActor(Actor actor){
		actors.remove(actor);
	}
	
	/**
	 * Removes an actor from the scene's actor-buffer where the index = index.
	 * 
	 * @param index a value that represents the actor-index
	 */
	public void removeActor(int index){
		actors.remove(index);
	}
	
	/**
	 * 
	 * @return a boolean value that represents the current initialization-state.
	 */
	public boolean isInitialized(){
		return this.initialized;
	}
	
	/**
	 * Initializes and sets the current boolean state to initialized (true).
	 */
	public void initialize(){
		init();
		this.initialized = true;
	}
	
	/**
	 * 
	 * @return a list with the actors of the scene. (actor-buffer)
	 */
	public ArrayList<Actor> getActors(){
		return this.actors;
	}
	
	/**
	 * 
	 * @return a list with the entities of the scene. (actor-buffer, but only entities)
	 */
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
