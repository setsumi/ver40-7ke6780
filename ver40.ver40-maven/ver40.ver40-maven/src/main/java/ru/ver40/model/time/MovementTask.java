package ru.ver40.model.time;

import ru.ver40.map.FloorMap;
import ru.ver40.model.Actor;
import ru.ver40.model.PositionConstant;
import ru.ver40.service.MapService;

/**
 * Задача на перемещение
 * @author anon
 *
 */
public class MovementTask extends TimedTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3341040003962761478L;
	
	private PositionConstant position;

	public MovementTask(int duration, PositionConstant constant, Actor person) {
		super(person, duration);
		this.position = constant;	
	}

	@Override
	public void perform() {
		FloorMap map = MapService.getInstance().getcMap();
		Actor person = getActor();
		switch (position) {
		case NORTH:
			map.translatePerson(person, person.getX() , person.getY() - 1);			
			break;
		case SOUTH:
			map.translatePerson(person, person.getX() , person.getY() + 1);	
			break;
		case EAST:
			map.translatePerson(person, person.getX() + 1 , person.getY());
			break;
		case WEST:
			map.translatePerson(person, person.getX() - 1 , person.getY());
			break;
		case NORTH_EAST:
			map.translatePerson(person, person.getX() + 1 , person.getY() - 1);
			break;
		case SOUTH_EAST:
			map.translatePerson(person, person.getX() + 1 , person.getY() + 1);
			break;
		case SOUTH_WEST:
			map.translatePerson(person, person.getX() - 1 , person.getY() + 1);
			break;
		case NOTRT_WEST:
			map.translatePerson(person, person.getX() - 1 , person.getY() - 1);
			break;
			
		}
	}		
}