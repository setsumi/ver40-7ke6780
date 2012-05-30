package ru.ver40.model;

import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.system.AAnimation;
import ru.ver40.util.RoleSystem;

/**
 * Класс для описания той самой маленькой '@' 
 * бегающей по экрану.
 * @author anon
 *
 */
public class Player extends Actor {
	
	private static final long serialVersionUID = -272256474863920368L;
	

	/**
	 * Конструктор.
	 */
	public Player(String name) {
		m_name = name;
//		setPassable(true);
		getSymbol().setSymbol('@');
		getSymbol().setFgColor(0xB5044B);
		setBlast(10);
		setResist(10);
		setStructure(10);
	}

	@Override
	public void performTimedAction() {
		FloorMap map = MapService.getInstance().getMap();
		switch (getAction()) {
		case ACTION_WAIT:
			System.out.println(this + " wait");// debug
			break;
		case ACTION_MOVE:
			System.out.println(this + " do_moveTo");// debug
			do_moveTo(map, m_actTargetX, m_actTargetY);
			break;
		case ACTION_USEOBJECT:
			break;
		case ACTION_HIT:
			break;
		case ACTION_KICK:
			break;
		case ACTION_SHOOT:
			break;
		}
	}

	@Override
	protected void do_moveTo(FloorMap map, int x, int y) {
		MapCell cell = map.translateActor(this, x , y);
		if (cell != null && !cell.getPersons().isEmpty()) {
			RoleSystem.testFight(this, cell.getPersons().get(0));		
		}
	}

	@Override
	protected void do_useObject(FloorMap map, int x, int y) {
	}

	@Override
	protected void do_hit(FloorMap map, int x, int y) {
	}

	@Override
	protected void do_kick(FloorMap map, int x, int y) {
	}

	@Override
	protected void do_shoot(FloorMap map, int x, int y, AAnimation anim) {
	}
	
}
