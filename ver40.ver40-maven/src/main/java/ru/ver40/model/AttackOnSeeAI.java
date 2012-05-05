package ru.ver40.model;

import java.util.Random;

import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;
import rlforj.math.Point2I;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;

/**
 * Простое AI.
 * 
 * Если игрок в поле зренния: сделать к нему шаг и по возможности ударить.
 * Если игрока в поле зрения нет: 
 *  1 если направление не задано - задать случайное направление, затем 3
 *  2 если направленеи задано, 35% на смену направления, затем 3
 *  3 сделать шаг в выбранно направлениии
 * 
 * @author anon
 *
 */
public class AttackOnSeeAI implements AIProvider {
	
	private Actor owner;
	private MapService mService = MapService.getInstance();
	private ILosAlgorithm los;
	private PositionConstant pos;
	private Random rng;
	
	public AttackOnSeeAI() {
		los = new PrecisePermissive();
		rng = new Random();
		pos = PositionConstant.values()[rng.nextInt(7)];		
	}

	@Override
	public int behave() {
		FloorMap map = mService.getMap();
		Player player = map.getPlayer();
		// Проверка игрок в поле зрения:
		// Если расстояние межлу ними МЕНЬШЕ чем 1.5 * FovRadius
		// То рассчитать лос
		//
		if (Point2I.distance(player.getX(), player.getY(), owner.getX(), owner.getY()) < 1.5 * 15
				&& los.existsLineOfSight(map, owner.getX(), owner.getY(), 
						player.getX(), player.getY(), true)) {
			// Сделать шаг к плееру или ударить его
			//
			Point2I point = los.getProjectPath().get(1);
			map.translateActor(owner, point.x, point.y);
			return 10;
		} else {
			if (rng.nextInt(100) < 35) {
				pos = PositionConstant.values()[rng.nextInt(7)];
			}
			return moveRandom(pos, map);			
		}		
	}

	private int moveRandom(PositionConstant position, FloorMap map) {
		switch (position) {
		case NORTH:
			map.translateActor(owner, owner.getX() , owner.getY() - 1);			
			return 10;
		case SOUTH:
			map.translateActor(owner, owner.getX() , owner.getY() + 1);	
			return 10;
		case EAST:
			map.translateActor(owner, owner.getX() + 1 , owner.getY());
			return 10;
		case WEST:
			map.translateActor(owner, owner.getX() - 1 , owner.getY());
			return 10;
		case NORTH_EAST:
			map.translateActor(owner, owner.getX() + 1 , owner.getY() - 1);
			return 10;
		case SOUTH_EAST:
			map.translateActor(owner, owner.getX() + 1 , owner.getY() + 1);
			return 10;
		case SOUTH_WEST:
			map.translateActor(owner, owner.getX() - 1 , owner.getY() + 1);
			return 10;
		default:
			map.translateActor(owner, owner.getX() - 1 , owner.getY() - 1);
			return 10;		
		}
	}
	
	

	@Override
	public void setOwner(Actor actor) {
		this.owner = actor;		
	}
}
