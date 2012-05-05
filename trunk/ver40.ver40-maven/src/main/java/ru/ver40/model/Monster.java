package ru.ver40.model;


/**
 * Тестовый монстр
 * @author anon
 *
 */
public class Monster extends Actor {

	private static final long serialVersionUID = 7299922403330474136L;
	
	private AIProvider ai;
	
	public Monster() {
		setPassable(false);
		getSymbol().setBgColor(0xC41212);
		getSymbol().setSymbol('M');
	
	}

	@Override
	public int performTimedAction() {
		return ai.behave(); /*
		PositionConstant position = PositionConstant.values()[new Random().nextInt(7)];
		FloorMap map = MapService.getInstance().getcMap();
		switch (position) {
		case NORTH:
			map.translateActor(this, this.getX() , this.getY() - 1);			
			return 10;
		case SOUTH:
			map.translateActor(this, this.getX() , this.getY() + 1);	
			return 10;
		case EAST:
			map.translateActor(this, this.getX() + 1 , this.getY());
			return 10;
		case WEST:
			map.translateActor(this, this.getX() - 1 , this.getY());
			return 10;
		case NORTH_EAST:
			map.translateActor(this, this.getX() + 1 , this.getY() - 1);
			return 10;
		case SOUTH_EAST:
			map.translateActor(this, this.getX() + 1 , this.getY() + 1);
			return 10;
		case SOUTH_WEST:
			map.translateActor(this, this.getX() - 1 , this.getY() + 1);
			return 10;
		case NOTRT_WEST:
			map.translateActor(this, this.getX() - 1 , this.getY() - 1);
			return 10;		
		}
		return 0; */
	}
	
	public void setAi(AIProvider ai) {
		ai.setOwner(this);
		this.ai = ai;
	}
	
	public AIProvider getAi() {
		return ai;
	}
}
