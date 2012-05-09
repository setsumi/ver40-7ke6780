package ru.ver40.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Ячейка карты.
 * 
 * Содержит:
 * 1..1 Пол
 * 0..1 Ловушка
 * 0..1 Строений
 * 0..* Персонажей
 * 0..* Предметов
 * 
 * @author anon
 *
 */
public class MapCell implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3836097147165496799L;

	private Floor floor;
	
	private Building building;
	
//	private Trap trap;
	
 	private List<Item> items;
	
    public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}

	private List<Actor> persons;
    
    private VisibilityState visible;

	public VisibilityState getVisible() {
		return visible;
	}

	public void setVisible(VisibilityState visible) {
		this.visible = visible;
	}

	/**
	 * Констркутор по умолчанию: создает обычный пол карты.
	 */
	public MapCell() {
		this.floor = new Floor();
		persons = new LinkedList<Actor>();
	}
	
	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public void addPerson(Actor person) {
		person.setParent(this);
		persons.add(person);
	}
	
	public void addItem(Item item) {
		item.setParent(this);
		items.add(item);
	}
	
	
	public List<Actor> getPersons() {
		return Collections.unmodifiableList(persons);
	}
	
	public void remove(GObject object) {
		if (object instanceof Building) {
			if (object == building) { 
				building = null;
			}
		} else if (object instanceof Actor) {
			persons.remove(object);
		}
	}
	
	// Работа с графичкой слика
	//
	public String getResultString() {
		if (!persons.isEmpty()) {
			return Character.toString(persons.get(persons.size() - 1).getSymbol().getSymbol());
		} else if (building != null) {
			return Character.toString(building.getSymbol().getSymbol());			
		} else {
			return Character.toString(floor.getSymbol().getSymbol());
		}
	}
	
	public int getResultBg() {
		if (!persons.isEmpty()) {
			return persons.get(persons.size() - 1).getSymbol().getBgColor();
		} else if (building != null) {
			return (building.getSymbol().getBgColor());
		} else {
			return floor.getSymbol().getBgColor();
		}
	}
	
	public int getResultFg() {
		if (!persons.isEmpty()) {
			return persons.get(persons.size() - 1).getSymbol().getFgColor();
		} else if (building != null) {
			return (building.getSymbol().getFgColor());
		} else {
			return floor.getSymbol().getFgColor();
		}
	}
	
	public boolean isPassable() {
		boolean passable = true;
		for (Actor p : persons) {
				passable = passable && p.isPassable();
		}
		return passable && floor.isPassable() 
				&& (building == null ? true : building.isPassable());
	}
	
	/*
	 * Статические фабрики
	 */
	
	/**
	 * Создать клетку со стеной
	 * @return
	 */
	public static MapCell createWall() {
		MapCell ret = new MapCell();
		Building wall = new Building();
		wall.setSymbol(new Symbol('#'));
		wall.setPassable(false);
		ret.setBuilding(wall);
		return ret;
	}
	
	/**
	 * Создать клетку с дверью
	 * @return
	 */
	public static MapCell createDoor() {
		MapCell ret = new MapCell();
		Building wall = new Door();
		ret.setBuilding(wall);
		return ret;
	}	
}


