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

public class AIShootOnSee implements AIProvider {

	private Actor m_owner;
	private MapService m_mapService = MapService.getInstance();
	private ILosAlgorithm m_los;
	private AStar m_astar;
	private PositionConstant m_pos;
	private Random m_rng;

	public AIShootOnSee() {
		m_los = new PrecisePermissive();
		m_rng = new Random();
		m_pos = PositionConstant.values()[m_rng.nextInt(7)];
	}

	@Override
	public void behave() {
		FloorMap map = m_mapService.getMap();
		Player player = map.getPlayer();
		AIMoveAdapter moveAdapter = new AIMoveAdapter(map);
		m_astar = new AStar(moveAdapter, 400, 400, true);
		// Проверка игрок в поле зрения:
		// Если расстояние межлу ними МЕНЬШЕ чем 1.5 * FovRadius
		// То рассчитать лос
		//
		if (Point2I.distance(player.getX(), player.getY(), m_owner.getX(), m_owner.getY()) < 15
				&& m_los.existsLineOfSight(map, m_owner.getX(), m_owner.getY(), player.getX(),
						player.getY(), false)) {
			// Проверить существует ли LOS для выстрела:
			//
			if (m_los.existsLineOfSight(moveAdapter, m_owner.getX(), m_owner.getY(),
					player.getX(), player.getY(), true)) {
				// Стреляем
				//
				m_owner.action_shoot(player.getX(), player.getY(), new AnimationBulletFlight(
						StateGameplay.getViewport(), m_los.getProjectPath(), 20));
			} else {
				// Сделать шаг к плееру или ударить его
				//
				moveAdapter.setSrcPoint(new Point2I(m_owner.getX(), m_owner.getY()));
				moveAdapter.setTgtPoint(new Point2I(player.getX(), player.getY()));
				Point2I[] points = m_astar.findPath(m_owner.getX(), m_owner.getY(),
						player.getX(), player.getY());
				if (points != null && points.length > 1) {
					Point2I point = points[1];
					MapCell cell = map.translateActor(m_owner, point.x, point.y);

					if (cell != null) {
						// Уткнулись в стену или игрока
						//
						if (cell.getPersons().contains(player)) {
							// В игрока, атакуем!
							//
							m_owner.action_hit(player.getX(), player.getY());
						}
					}
				}
			}
		} else {
			if (m_rng.nextInt(100) < 35) {
				m_pos = PositionConstant.values()[m_rng.nextInt(7)];
			}
			moveRandom(m_pos, map);
		}
	}

	private void moveRandom(PositionConstant position, FloorMap map) {
		switch (position) {
		case NORTH:
			m_owner.action_moveTo(m_owner.getX(), m_owner.getY() - 1);
			break;
		case SOUTH:
			m_owner.action_moveTo(m_owner.getX(), m_owner.getY() + 1);
			break;
		case EAST:
			m_owner.action_moveTo(m_owner.getX() + 1, m_owner.getY());
			break;
		case WEST:
			m_owner.action_moveTo(m_owner.getX() - 1, m_owner.getY());
			break;
		case NORTH_EAST:
			m_owner.action_moveTo(m_owner.getX() + 1, m_owner.getY() - 1);
			break;
		case SOUTH_EAST:
			m_owner.action_moveTo(m_owner.getX() + 1, m_owner.getY() + 1);
			break;
		case SOUTH_WEST:
			m_owner.action_moveTo(m_owner.getX() - 1, m_owner.getY() + 1);
			break;
		default:
			m_owner.action_moveTo(m_owner.getX() - 1, m_owner.getY() - 1);
			break;
		}
	}

	@Override
	public void setOwner(Actor actor) {
		this.m_owner = actor;
	}

}
