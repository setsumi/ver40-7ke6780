package ru.ver40.map;

import java.util.ArrayList;

/**
 * Карта в виде большого 2D-поля
 * 
 */
public class Map {
	private ArrayList<Chunk> chunks;
	private String path;

	/**
	 * Конструктор
	 */
	public Map(String path) {
		this.path = path;
	}

	/**
	 * Вернуть путь к данным карты
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Получить клетку по координатам
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell GetCell(int x, int y) {
		Cell rv = null;
		return rv;
	}
}
