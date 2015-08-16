package rt.main.actors;

import rt.main.Actor;
import rt.main.Scene;
import rt.main.types.BlockType;

public class Block extends BoxActor {

	private BlockType blocktype;
	
	public Block(Scene scene, float x, float y, float z, BlockType blocktype) {
		super(scene, x, y, z);
		this.blocktype = blocktype;
		
		this.setSize(16, 16, 16);
		
		this.getHitbox().setWidth(16);
		this.getHitbox().setHeight(16);
		this.getHitbox().setLength(16);
		//this.getHitbox().setDraw(true);
		this.setSolid(true);
		
		this.getSide("left").texture = blocktype.texture_left;
		this.getSide("right").texture = blocktype.texture_right;
		this.getSide("top").texture = blocktype.texture_top;
		this.getSide("bottom").texture = blocktype.texture_bottom;
		this.getSide("back").texture = blocktype.texture_back;
		this.getSide("front").texture = blocktype.texture_front;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void tick(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preparedCollision(Actor actor, float timex, float timey,
			float timez) {
		// TODO Auto-generated method stub
		
	}
	
	public BlockType getType(){
		return this.blocktype;
	}
	
	public void setType(BlockType blocktype){
		this.blocktype = blocktype;
	}

}
