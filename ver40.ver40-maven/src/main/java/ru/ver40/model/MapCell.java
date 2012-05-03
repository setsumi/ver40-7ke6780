package ru.ver40.model;

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
public class MapCell extends GObject  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3836097147165496799L;

	private Floor floor;
	
	private Building building;
	
//	private Trap trap;
	
//	private List<Item> items;
	
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
	
	/**
	 * Статический фактори-метод, создаюший обычную стену.
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
		if (building != null) {
			return Character.toString(building.getSymbol().getSymbol());
		} else if (!persons.isEmpty()) {
			return Character.toString(persons.get(persons.size() - 1).getSymbol().getSymbol());
		} else {
			return Character.toString(floor.getSymbol().getSymbol());
		}
	}
	
	public int getResultBg() {
		if (building != null) {
			return (building.getSymbol().getBgColor());
		} else if (!persons.isEmpty()) {
			return persons.get(persons.size() - 1).getSymbol().getBgColor();
		} else {
			return floor.getSymbol().getBgColor();
		}
	}
	
	public int getResultFg() {
		if (building != null) {
			return (building.getSymbol().getFgColor());
		} else if (!persons.isEmpty()) {
			return persons.get(persons.size() - 1).getSymbol().getFgColor();
		} else {
			return floor.getSymbol().getFgColor();
		}
	}
	
	@Override
	public boolean isPassable() {
		boolean passable = true;
		for (Actor p : persons) {
			passable = passable && p.isPassable();
		}
		return passable && floor.isPassable() 
				&& (building == null ? true : building.isPassable());
	}
}
