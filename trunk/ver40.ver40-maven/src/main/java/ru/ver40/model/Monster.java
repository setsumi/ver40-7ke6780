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
		FloorMap map = MapService.getInstance().getMap();
		switch (getAction()) {
		case ACTION_WAIT:
			break;
		case ACTION_MOVE:
			System.out.println(this.toString() + " [" + this.hashCode() + "] do_moveTo");// debug
			do_moveTo(map, m_actTargetX, m_actTargetY);
			break;
		case ACTION_USEOBJECT:
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
		ai.setOwner(this);
		m_ai = ai;
	}
	
	public AIProvider getAi() {
		return m_ai;
	}
	
	@Override
	protected void die() {
		super.die();
		getCell().remove(this);
		TimeService.getInstance().unregister(this);
	}
	
	/*
	 * Статические фабрики
	 */
	public static Monster createGelworm() {
		Monster ret = new Monster();
		ret.setName("Gelworm");
		ret.getSymbol().setSymbol('s');
		ret.getSymbol().setFgColor(0x03998F);
		ret.setFight(35);
		ret.setResist(25);
		ret.setMove(25);
		ret.setHide(10);
		ret.setFind(30);
		ret.setRecover(30);
		ret.setStructure(20 + Rng.d(2, 10, 3));
		ret.setEnergy(10 + Rng.d(3, 6, 2));
		ret.setAi(new FightOnSeeAI());
		return ret;
	}
	
	public static Monster createWatcherFly() {
		Monster ret = new Monster();
		ret.setName("Watcher Fly");
		ret.getSymbol().setSymbol('i');
		ret.getSymbol().setFgColor(0x039942);
		ret.setBlast(10);
		ret.setFight(15);
		ret.setResist(0);
		ret.setMove(20);
		ret.setHide(10);
		ret.setFind(20);
		ret.setRecover(5);
		ret.setStructure(5 + Rng.d(1, 6, 1));
		ret.setEnergy(5 + Rng.d(1, 6, 1));
		ret.setAi(new ShootOnSeeAI());
		return ret;
	}

}
