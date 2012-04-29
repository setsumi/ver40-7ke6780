package ru.ver40.model;

import java.io.Serializable;

/**
 * GObject - сокращение от GameObject.
 * Базовый абстрактный класс любого объекта игровой модели.
 * Хранит в себе базовую информацию для графического движка.
 * Является серриализуемым.
 * 
 * @author anon
 *
 */
public abstract class GObject extends NotificationSource implements Serializable {
	
	/**
	 * Серийная версия класса
	 */
	private static final long serialVersionUID = 2546350833419137727L;

	/**
	 * Можно ли пройти по данному объекту
	 */
	private boolean passable = true;
	
	/**
	 * Определяет видимость объекта. 
	 * Участвует в рассчете тайла данного объекта.
	 */
	private boolean visible = true;
	
	/**
	 * Информация о графическом представлении объекта.
	 */
	private Symbol symbol;
	
	public GObject() {
		symbol = new Symbol();
	}
	
	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}	
}
