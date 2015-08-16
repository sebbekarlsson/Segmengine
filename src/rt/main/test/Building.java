package rt.main.test;

import java.util.Random;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.TextureBank;
import rt.main.actors.BoxActor;

public class Building extends BoxActor {

	Random random = new Random();

	public Building(Scene scene, float x, float y, float z) {
		super(scene, x, y, z);
		getSide("right").texture = TextureBank.TEST;
		getSide("left").texture = TextureBank.TEST;
		getSide("front").texture = TextureBank.TEST;
		getSide("back").texture = TextureBank.TEST;
		getSide("top").texture = TextureBank.TEST2;
	}

	@Override
	public void draw(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		getHitbox().setDraw(true);
		int repeatBuffer = (1+random.nextInt(16));
		for(int i = 0; i < sides.length; i++){
			sides[i].textureRepeat = getHeight()/repeatBuffer;
		}
		if(random.nextInt(3) == 0){
			for(int i = 0; i < sides.length; i++){
				sides[i].textureRepeat = 4;
			}
		}

	}

	@Override
	public void tick(int delta) {
		//setSize(getWidth(), (float) Math.sqrt((-x-scene.camera.x)*(-x-scene.camera.x) + (-z-scene.camera.z)*(-z-scene.camera.z)));
	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {
		// TODO Auto-generated method stub
		
	}

}
