package ru.ver40.model;

import java.util.Random;

import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;
import rlforj.math.Point2I;
import rlforj.pathfinding.AStar;
import ru.ver40.StateGameplay;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.system.AnimationBulletFlight;
import ru.ver40.util.RoleSystem;

public class ShootOnSeeAI implements AIProvider {
	
	private Actor owner;
	private MapService mService = MapService.getInstance();
	private ILosAlgorithm los;
	private AStar astar;
	private PositionConstant pos;
	private Random rng;
	
	public ShootOnSeeAI() {
		los = new PrecisePermissive();		
		rng = new Random();
		pos = PositionConstant.values()[rng.nextInt(7)];		
	}

	@Override
	public int behave() {
		FloorMap map = mService.getMap();
		Player player = map.getPlayer();
		AIMoveAdapter moveAdapter = new AIMoveAdapter(map);
		astar = new AStar(moveAdapter, 400, 400, true);
		// Проверка игрок в поле зрения:
		// Если расстояние межлу ними МЕНЬШЕ чем 1.5 * FovRadius
		// То рассчитать лос
		//
		if (Point2I.distance(player.getX(), player.getY(), owner.getX(), owner.getY()) < 15
				&& los.existsLineOfSight(map, owner.getX(), owner.getY(), 
						player.getX(), player.getY(), false)) {
			// Проверить существует ли LOS для выстрела:
			//
			if (los.existsLineOfSight(moveAdapter, owner.getX(), owner.getY(),
					player.getX(), player.getY(), true)) {
				// Стреляем
				//
				RoleSystem.testBlast(owner, player);

				// TODO пока пихнул анимацию сода
				AnimationBulletFlight animation = new AnimationBulletFlight(
				StateGameplay.viewport, los.getProjectPath(), 20);
				StateGameplay.animations.add(animation);
				// end of block
				return 10;
				
			}
			// Сделать шаг к плееру или ударить его
			//
			moveAdapter.setSrcPoint(new Point2I(owner.getX(), owner.getY()));
			moveAdapter.setTgtPoint(new Point2I(player.getX(), player.getY()));
			Point2I[] points = astar.findPath(owner.getX(), owner.getY(), player.getX(), player.getY());
			if (points != null && points.length > 1) {
				Point2I point = points[1];
				MapCell cell = map.translateActor(owner, point.x, point.y);
				
				if (cell != null) {
					// Уткнулись в стену или игрока
					//
					if (cell.getPersons().contains(player)) {
						// В игрока, атакуем!
						//
						
						RoleSystem.testFight(owner, player);
					}
				}
			}
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
