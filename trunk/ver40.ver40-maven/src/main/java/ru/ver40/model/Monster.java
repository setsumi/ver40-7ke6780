package ru.ver40.model;

import ru.ver40.service.TimeService;
import ru.ver40.util.Rng;

/**
 * Тестовый монстр
 * @author anon
 *
 */
public class Monster extends Actor {

	private static final long serialVersionUID = 7299922403330474136L;	
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private AIProvider ai;
	
	public Monster() {
		setPassable(true);
	//	getSymbol().setBgColor(0xC41212);
		getSymbol().setSymbol('M');	
	}

	@Override
	public int performTimedAction() {
		return ai.behave();	
	}
	
	public void setAi(AIProvider ai) {
		ai.setOwner(this);
		this.ai = ai;
	}
	
	public AIProvider getAi() {
		return ai;
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
	
	@Override
	public String toString() {
		return name;
	}
}
