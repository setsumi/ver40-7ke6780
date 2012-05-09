package ru.ver40.model;

import ru.ver40.service.TimeService;

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
}
