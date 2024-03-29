package ru.ver40.model;

import ru.ver40.service.MapService;

public class BehaviourLevelTransporter implements IBuildingBehaviour {

	private int level;
	private int x, y;

	public BehaviourLevelTransporter(int level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
	}

	@Override
	public void behave(Building b) {
		MapService.getInstance().gotoLevel(level, x, y);
	}

}
