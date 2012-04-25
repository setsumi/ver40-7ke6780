package ru.ver40.map;

/**
 * Клетка на карте
 * 
 */
public class Cell {
	/**
	 * Типы клеток
	 * 
	 */
	public enum Type {
		NONE(0), WALL(10), FLOOR(20), GAP(30);

		private int code;

		private Type(int c) {
			code = c;
		}

		public int toInt() {
			return code;
		}
	}

	public int type;

	public Cell() {
		type = Type.NONE.toInt();
	}
}
