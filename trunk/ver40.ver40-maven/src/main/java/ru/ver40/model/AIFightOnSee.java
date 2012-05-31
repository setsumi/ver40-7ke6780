package ru.ver40.model;

import java.util.Random;

import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;
import rlforj.math.Point2I;
import rlforj.pathfinding.AStar;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.util.RoleSystem;

/**
 * Простое AI.
 * 
 * Если игрок в поле зренния: сделать к нему шаг и по возможности ударить. Если игрока в поле
 * зрения нет: 1 если направление не задано - задать случайное направление, затем 3 2 если
 * направленеи задано, 35% на смену направления, затем 3 3 сделать шаг в выбранно направлениии
 * 
 * @author anon
 * 
 */
public class AIFightOnSee implements AIProvider {

	private Actor owner;
	private MapService mService = MapService.getInstance();
	private ILosAlgorithm los;
	private AStar astar;
	private PositionConstant pos;
	private Random rng;

	public AIFightOnSee() {
		los = new PrecisePermissive();
		rng = new Random();
		pos = PositionConstant.values()[rng.nextInt(7)];
	}

	@Override
	public void behave() {
		FloorMap map = mService.getMap();
		Player player = map.getPlayer();
		AIMoveAdapter moveAdapter = new AIMoveAdapter(map);
		astar = new AStar(moveAdapter, 400, 400, true);
		// Проверка игрок в поле зрения:
		// Если расстояние межлу ними МЕНЬШЕ чем 1.5 * FovRadius
		// То рассчитать лос
		//
		if (Point2I.distance(player.getX(), player.getY(), owner.getX(), owner.getY()) < 15
				&& los.existsLineOfSight(map, owner.getX(), owner.getY(), player.getX(),
						player.getY(), true)) {
			// Сделать шаг к плееру или ударить его
			//
			moveAdapter.setSrcPoint(new Point2I(owner.getX(), owner.getY()));
			moveAdapter.setTgtPoint(new Point2I(player.getX(), player.getY()));
			Point2I[] points = astar.findPath(owner.getX(), owner.getY(), player.getX(),
					player.getY());
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
		} else {
			if (rng.nextInt(100) < 35) {
				pos = PositionConstant.values()[rng.nextInt(7)];
			}
			moveRandom(pos, map);
		}
	}

	private void moveRandom(PositionConstant position, FloorMap map) {
		switch (position) {
		case NORTH:
			map.translateActor(owner, owner.getX(), owner.getY() - 1);
			break;
		case SOUTH:
			map.translateActor(owner, owner.getX(), owner.getY() + 1);
			break;
		case EAST:
			map.translateActor(owner, owner.getX() + 1, owner.getY());
			break;
		case WEST:
			map.translateActor(owner, owner.getX() - 1, owner.getY());
			break;
		case NORTH_EAST:
			map.translateActor(owner, owner.getX() + 1, owner.getY() - 1);
			break;
		case SOUTH_EAST:
			map.translateActor(owner, owner.getX() + 1, owner.getY() + 1);
			break;
		case SOUTH_WEST:
			map.translateActor(owner, owner.getX() - 1, owner.getY() + 1);
			break;
		default:
			map.translateActor(owner, owner.getX() - 1, owner.getY() - 1);
			break;
		}
	}

	@Override
	public void setOwner(Actor actor) {
		this.owner = actor;
	}
}