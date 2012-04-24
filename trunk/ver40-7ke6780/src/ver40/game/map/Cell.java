package ver40.game.map;

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
		NONE(0), WALL(1), FLOOR(2), GAP(3);

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
