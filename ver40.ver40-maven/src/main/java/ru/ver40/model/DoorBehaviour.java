package ru.ver40.model;

public class DoorBehaviour implements IBuildingBehaviour {
	
	private boolean opened;

	@Override
	public void behave(Building b) {
		if (!opened) {
			b.getSymbol().setSymbol('/');
			b.setPassable(true);
			opened = !opened;
		} else {
			if (b.getParent().isPassable()) {
				b.getSymbol().setSymbol('+');
				b.setPassable(false);
				opened = !opened;
			}
		}			
	}
	
}