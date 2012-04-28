package ru.ver40.map;

import java.util.ArrayList;
import java.util.List;

import ru.ver40.util.Constants;

/**
 * Карта в виде большого 2D-поля
 * 
 */
public class FloorMap {
	private List<Chunk> m_chunks;
	private String m_path;

	/**
	 * Конструктор
	 */
	public FloorMap(String path) {
		m_path = path;
		m_chunks = new ArrayList<Chunk>(2);// Constants.MAP_CHUNK_CACHE_SIZE);
	}

	/**
	 * Вернуть путь к данным карты
	 * 
	 * @return
	 */
	public String getPath() {
		return m_path;
	}

	/**
	 * Вернуть клетку по её координатам на карте
	 * 
	 * @return
	 */
	public Cell getCell(int x, int y) {
		Chunk ch = null;
		// координаты чанка
		int cx = x / Constants.MAP_CHUNK_SIZE;
		int cy = y / Constants.MAP_CHUNK_SIZE;
		// ищем чанк в кэше
		for (Chunk c : m_chunks) {
			if (c.m_posX == cx && c.m_posY == cy) {
				ch = c;
				// TODO move used chunk down the list to ensure survival
				break;
			}
		}
		// не нашли в кэше, создаем и заносим в кэш
		if (ch == null) {
			if (m_chunks.size() == Constants.MAP_CHUNK_CACHE_SIZE) {
				// TODO debug force save
				// m_chunks.get(0).save();
				m_chunks.remove(0);
			}
			ch = new Chunk(this, cx, cy);
			m_chunks.add(ch);
			// TODO debug test save
			// rv.save();
		}
		// индекс клетки в квадрате
		int ci = (y % Constants.MAP_CHUNK_SIZE) * Constants.MAP_CHUNK_SIZE
				+ (x % Constants.MAP_CHUNK_SIZE);
		return ch.getCell(ci);
	}
}
