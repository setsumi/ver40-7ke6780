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
	
    private List<Person> persons;
	
	/**
	 * Констркутор по умолчанию: создает обычный пол карты.
	 */
	public MapCell() {
		this.floor = new Floor();
		persons = new LinkedList<Person>();
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
	
	public void addPerson(Person person) {
		person.setParent(this);
		persons.add(person);
	}
	
	public List<Person> getPersons() {
		return Collections.unmodifiableList(persons);
	}
	
	public void remove(GObject object) {
		if (object instanceof Building) {
			if (object == building) { 
				building = null;
			}
		} else if (object instanceof Person) {
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
	
	public String getResultBg() {
		if (building != null) {
			return String.valueOf(building.getSymbol().getBgColor());
		} else if (!persons.isEmpty()) {
			return String.valueOf((persons.get(persons.size() - 1).getSymbol().getBgColor()));
		} else {
			return String.valueOf(floor.getSymbol().getBgColor());
		}
	}
	
	public String getResultFg() {
		if (building != null) {
			return String.valueOf(building.getSymbol().getFgColor());
		} else if (!persons.isEmpty()) {
			return String.valueOf(persons.get(persons.size() - 1).getSymbol().getFgColor());
		} else {
			return String.valueOf(floor.getSymbol().getFgColor());
		}
	}
	
	@Override
	public boolean isPassable() {
		boolean passable = true;
		for (Person p : persons) {
			passable = passable && p.isPassable();
		}
		return passable && floor.isPassable() 
				&& (building == null ? true : building.isPassable());
	}
}