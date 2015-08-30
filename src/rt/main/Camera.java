package rt.main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import rt.main.test.Building;

public class Camera extends Actor {
	
	private boolean mouseControlled = false;
	private boolean keyboardControlled = false;
	
	public Camera(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}
	

	@Override
	public void init() {
		
	}
	
	
	public void update(int delta){	
		tick(delta);
		draw(delta);
		
		if(isKeyboardControlled()){
			slaveForKeyboard();
		}
		if(isMouseControlled()){
			slaveForMouse(delta);
		}
	}
	

	@Override
	public void draw(int delta) {
		
	}

	@Override
	public void tick(int delta) {
		
	}
	
	public float getYaw(){
		return this.yrot;
	}
	
	public float getPitch(){
		return this.xrot;
	}
	
	public void setMouseControlled(boolean mouseControlled){
		this.mouseControlled = mouseControlled;
	}
	
	public void setKeyboardControlled(boolean keyboardControlled){
		this.keyboardControlled = keyboardControlled;
	}
	
	public boolean isMouseControlled(){
		return this.mouseControlled;
	}
	
	public boolean isKeyboardControlled(){
		return this.keyboardControlled;
	}
	
	private void slaveForMouse(int delta){
		this.xrot -= Mouse.getDY();
		this.yrot += Mouse.getDX();
	}
	
	private void slaveForKeyboard(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			x -= 1f * (float) Math.sin(Math.toRadians(getYaw()));
			z += 1f * (float) Math.cos(Math.toRadians(getYaw()));
			y += 1f * (float) Math.tan(Math.toRadians(getPitch()));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			z -= 0.2f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			x -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			x += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			scene.stageActor(new Building(scene,-x,-y,-z));
		}
	}


	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {
		// TODO Auto-generated method stub
		
	}

}
