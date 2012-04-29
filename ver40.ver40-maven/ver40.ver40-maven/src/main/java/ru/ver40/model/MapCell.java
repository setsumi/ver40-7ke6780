package ru.ver40.model;

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
	
// private List<Person> persons;
	
	/**
	 * Констркутор по умолчанию: создает обычный пол карты.
	 */
	public MapCell() {
		this.floor = new Floor();
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
	
	// Работа с графичкой слика
	//
	public String getResultString() {
		if (building != null) {
			return Character.toString(building.getSymbol().getSymbol());
		} else {
			return Character.toString(floor.getSymbol().getSymbol());
		}
	}
	
	public String getResultBg() {
		if (building != null) {
			return String.valueOf(building.getSymbol().getBgColor());
		} else {
			return String.valueOf(floor.getSymbol().getBgColor());
		}
	}
	
	public String getResultFg() {
		if (building != null) {
			return String.valueOf(building.getSymbol().getFgColor());
		} else {
			return String.valueOf(floor.getSymbol().getFgColor());
		}
	}
}
