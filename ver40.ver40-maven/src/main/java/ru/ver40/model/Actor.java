package ru.ver40.model;

/**
 * Персонаж игрового мира
 * @author anon
 *
 */
public class Actor extends GObject {
	
	/*
	 * Роевые характериктики:
	 */
	private int hp;	
	
	/**
	 * Координаты на игровой карте
	 */
	private int x, y;
	
	/**
	 * Относительная скорость
	 * 10 - значение по умолчанию
	 */
	private int speed = 10;	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	private static final long serialVersionUID = 8472085921088558199L;
	
	public Actor() {
		this.getSymbol().setSymbol('@');
		this.hp = 10;
		setPassable(false);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp < 0) {
			die();
		}
		this.hp = hp;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private void die() {
		System.out.println(this + "dies");		
	}	
	
	public MapCell getCell() {
		return (MapCell) getParent();
	}
}
