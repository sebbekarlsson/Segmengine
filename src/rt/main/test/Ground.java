package rt.main.test;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.TextureBank;
import rt.main.actors.BoxActor;

public class Ground extends BoxActor {

	public Ground(Scene scene,float x, float y, float z) {
		super(scene, x, y, z);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		setSize(32*20, 0, 32*20);
		for(int i = 0; i < sides.length; i++){
			sides[i].texture = TextureBank.TEST3;
			sides[i].textureRepeat = 32;
		}
		
	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey, float timez) {
		// TODO Auto-generated method stub
		
	}

}
