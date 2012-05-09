package ru.ver40.model;

public class Door extends Building {

	/**
	 * 
	 */
	private static final long serialVersionUID = 815650987938530887L;
	
	private boolean opened;
	
	public Door() {
		opened = false;
		getSymbol().setSymbol('+');
		setPassable(false);
		setActive(true);
	}
	
	@Override
	protected void use() {
		if (!opened) {
			getSymbol().setSymbol('/');
			setPassable(true);
			opened = !opened;
		} else {
			if (getParent().isPassable()) {
				getSymbol().setSymbol('+');
				setPassable(false);
				opened = !opened;
			}
		}
	}
}
