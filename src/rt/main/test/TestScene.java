package rt.main.test;

import java.awt.Color;

import rt.main.Scene;

public class TestScene extends Scene {

	@Override
	public void init() {
		camera.setMouseControlled(true);
		camera.setKeyboardControlled(true);
		backgroundColor = new Color(82,217,255);


		for(int x = 0; x < 20; x++){
			for(int z = 0; z < 20; z++){
				Building b = new Building(this,x*32,16,z*32);
				stageActor(b);
			}
		}
		
		stageActor(new Ground(this, 0,16, 0));
	}

	@Override
	public void tick(int delta) {
	}

	@Override
	public void draw(int delta) {

	}

}
