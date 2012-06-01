package ru.ver40.model;

import ru.ver40.StateGameplay;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.system.AAnimation;
import ru.ver40.util.Rng;
import ru.ver40.util.RoleSystem;

/**
 * Тестовый монстр
 * @author anon
 *
 */
public class Monster extends Actor {

	private static final long serialVersionUID = 7299922403330474136L;	
	
	private AIProvider m_ai = null;
	
	/**
	 * Конструктор.
	 */
	public Monster() {
//		setPassable(true);
//		getSymbol().setBgColor(0xC41212);
		getSymbol().setSymbol('M');	
	}

	@Override
	public void performTimedAction() {
		// Выполняем текущее действие.
		//
		FloorMap map = MapService.getInstance().getMap();
		switch (getAction()) {
		case ACTION_WAIT:
			System.out.println(this.toString() + " [" + this.hashCode() + "] wait");// debug
			break;
		case ACTION_MOVE:
			System.out.println(this.toString() + " [" + this.hashCode() + "] do_moveTo");// debug
			do_moveTo(map, m_actTargetX, m_actTargetY);
			break;
		case ACTION_USEOBJECT:
			System.out.println(this.toString() + " [" + this.hashCode() + "] do_useObject");// debug
			do_useObject(map, m_actTargetX, m_actTargetY);
			break;
		case ACTION_HIT:
			do_hit(map, m_actTargetX, m_actTargetY);
			break;
		case ACTION_KICK:
			break;
		case ACTION_SHOOT:
			System.out.println(this.toString() + " [" + this.hashCode() + "] do_shoot");// debug
			do_shoot(map, m_actTargetX, m_actTargetY, getActAnimation());
			break;
		}
		// Планируем следующее действие.
		//
		if (m_ai != null) {
			System.out.println(this.toString() + " [" + this.hashCode() + "] ai.behave");// debug
			m_ai.behave();
		}
	}

	@Override
	protected void do_moveTo(FloorMap map, int x, int y) {
		map.translateActor(this, x, y);
	}
	
	@Override
	protected void do_shoot(FloorMap map, int x, int y, AAnimation anim) {
		Actor target = map.getCell(x, y).getPerson();
		RoleSystem.testBlast(this, target);
		if (anim != null) {
			StateGameplay.getAnimations().add(anim);
		}
	}

	@Override
	protected void do_useObject(FloorMap map, int x, int y) {
		map.getCell(x, y).getBuilding().use();
	}

	@Override
	protected void do_hit(FloorMap map, int x, int y) {
		Actor target = map.getCell(x, y).getPerson();
		RoleSystem.testFight(this, target);
	}

	@Override
	protected void do_kick(FloorMap map, int x, int y) {
	}

	public void setAi(AIProvider ai) {
		if (ai != null) {
			ai.setOwner(this);
		}
		m_ai = ai;
	}
	
	public AIProvider getAi() {
		return m_ai;
	}
	
	@Override
	protected void die() {
		super.die();
		getCell().remove(this);
		TimeService.getInstance().unregisterSafe(this);
	}
	
	/*
	 * Статические фабрики
	 */
	public static Monster createPlayer(int x, int y) {
		Monster ret = new Monster();
		ret.setX(x);
		ret.setY(y);
		ret.setName("Player");
		ret.getSymbol().setSymbol('@');
		ret.getSymbol().setFgColor(0xB5044B);
		ret.setBlast(30);
		ret.setFight(30);
		ret.setResist(30);
		ret.setMove(20);
		ret.setHide(10);
		ret.setFind(20);
		ret.setRecover(40);
		ret.setStructure(40);
		ret.setEnergy(30);
		ret.setAi(null);
		return ret;
	}

	public static Monster createGelworm(int x, int y) {
		Monster ret = new Monster();
		ret.setX(x);
		ret.setY(y);
		ret.setName("Gelworm");
		ret.getSymbol().setSymbol('s');
		ret.getSymbol().setFgColor(0x03998F);
		ret.setBlast(0);
		ret.setFight(35);
		ret.setResist(25);
		ret.setMove(15);
		ret.setHide(10);
		ret.setFind(30);
		ret.setRecover(15);
		ret.setStructure(20 + Rng.d(2, 10, 3));
		ret.setEnergy(10 + Rng.d(3, 6, 2));
		ret.setAi(new AIFightOnSee());
		return ret;
	}
	
	public static Monster createWatcherFly(int x, int y) {
		Monster ret = new Monster();
		ret.setX(x);
		ret.setY(y);
		ret.setName("Watcher Fly");
		ret.getSymbol().setSymbol('i');
		ret.getSymbol().setFgColor(0x039942);
		ret.setBlast(15);
		ret.setFight(10);
		ret.setResist(0);
		ret.setMove(20);
		ret.setHide(10);
		ret.setFind(20);
		ret.setRecover(5);
		ret.setStructure(5 + Rng.d(1, 6, 1));
		ret.setEnergy(5 + Rng.d(1, 6, 1));
		ret.setAi(new AIShootOnSee());
		return ret;
	}

}
