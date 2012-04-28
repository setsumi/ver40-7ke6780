package ru.ver40.map;

/**
 * Клетка на карте
 * 
 */
public class Cell {
	public static final short TYPE_NONE = 0;
	public static final short TYPE_WALL = 10;
	public static final short TYPE_FLOOR = 20;
	public static final short TYPE_GAP = 30;

	public short type;

	public Cell() {
		type = TYPE_NONE;
	}
}
