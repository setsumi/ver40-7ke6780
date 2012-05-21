package ru.ver40.model;

/**
 * Строение на карте. 
 * 
 * Предполагаемый класс для моделирования стен, дверей, мостов, и прочих архитектурных сооружений.
 * 
 * @author anon
 *
 */
public class Building extends GObject {
	
	/**
	 * Можно ли использовать этот предмет
	 */
	private boolean active;
	
	private IBuildingBehaviour beh;
	
	/**
	 * Серийная версия класса
	 */
	private static final long serialVersionUID = 3828996418331215629L;
	
	/**
	 * Вызывается когда используют данное сооружение
	 */
	public void use() {	
		
	}

	public IBuildingBehaviour getBeh() {
		return beh;
	}

	public void setBeh(IBuildingBehaviour beh) {
		this.beh = beh;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
