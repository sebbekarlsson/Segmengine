package rt.main.actors;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import rt.main.Actor;
import rt.main.Scene;

public abstract class Entity extends Actor {

	protected float dx, dy, dz = 0;
	protected float friction = 0.0002f;
	protected float weight = 0.0005f;
	protected boolean onGround = false;

	public Entity(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
	}

	public void update(int delta){
		updatePhysics(delta);
		tick(delta);
		draw(delta);
	}

	private void updatePhysics(int delta){
		x += dx * delta;
		y += dy * delta;
		z += dz * delta;
		onGround = false;

		ArrayList<Actor> actors = scene.getActors();
		System.out.println(actors.size());
		for(int i = 0; i < actors.size(); i++){
			Actor actor = actors.get(i);


			if(actor != this){



				float speed_left = (dx) - delta;
				float distance_left = ((x-(getHitbox().getWidth()/2)) - actor.x - actor.getHitbox().getLength());
				float time_left = (distance_left / speed_left);

				float speed_right = (dx) - delta;
				float distance_right = (actor.x - (x+(getHitbox().getWidth()/2)));
				float time_right = (distance_right / speed_right);

				float speed_y = (dy + weight) - delta;
				float distance_y = (y - actor.y - actor.getHitbox().getHeight());
				float time_y = (distance_y / speed_y);

				float speed_front = (dz) - delta;
				float distance_front = ((z-(getHitbox().getLength()/2)) - actor.z - actor.getHitbox().getLength());
				float time_front = (distance_front / speed_front);

				float speed_back = (dz) - delta;
				float distance_back = ((actor.z - (z+getHitbox().getLength()/2)));
				float time_back = (distance_back / speed_back);

				if(x+getHitbox().getX()+getHitbox().getWidth() >= actor.x && x+getHitbox().getX() <= actor.x+actor.getHitbox().getWidth() && z+getHitbox().getZ()+getHitbox().getLength() >= actor.z && z+getHitbox().getZ() <=actor.z+actor.getHitbox().getLength()){
					if(time_y >= 0 && time_y <= 1) {
						if(this.isSolid() && actor.isSolid()){
							y = actor.y+actor.getHitbox().getHeight();
							onGround = true;
						}
					}
				}

				if(x+getHitbox().getX()+getHitbox().getWidth() >= actor.x && x+getHitbox().getX()+getHitbox().getWidth() <= actor.x+actor.getHitbox().getWidth()+getHitbox().getWidth() && y >= actor.y && y <= actor.y+actor.getHitbox().getHeight()-0.1f){
					if(this.isSolid() && actor.isSolid()){
						if(time_front > 0 && time_front < 1 && z > actor.z) {
							z = (actor.z+actor.getHitbox().getLength()) + getHitbox().getLength()/2;
							dz = 0f;
						}
						if(time_back > 0 && time_back < 1 && z < actor.z){
							z = (actor.z-(getHitbox().getLength()/2));
							dz = 0f;
						}
					}
				}
				if(z+getHitbox().getZ()+getHitbox().getLength() >= actor.z && z+getHitbox().getZ()+getHitbox().getLength() <= actor.z+actor.getHitbox().getLength()+getHitbox().getLength() && y >= actor.y && y <= actor.y+actor.getHitbox().getHeight()-0.1f){
					if(this.isSolid() && actor.isSolid()){
						if(time_left > 0 && time_left < 1 && x > actor.x) {
							x = (actor.x+actor.getHitbox().getWidth()) + getHitbox().getWidth()/2;
							dx = 0;
						}
						if(time_right > 0 && time_right < 1 && x < actor.x){
							x = (actor.x-(getHitbox().getWidth()/2));
							dx = 0;
						}
					}
				}

			}

		}


		if(!onGround){
			dy -= weight * delta;
		}else{
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				dy += 0.009f * delta;
			}else{
				dy = 0 * delta;
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
}
