package ru.ver40.map;

import java.util.ArrayList;

import ru.ver40.model.MapCell;
import ru.ver40.model.Person;
import ru.ver40.util.Constants;

/**
 * Карта в виде большого 2D-поля
 * 
 */
public class FloorMap {
	/**
	 * Буфер чанков
	 */
	private ArrayList<Chunk> m_chunks;
	/**
	 * Путь к каталогу с данными карты
	 */
	private String m_path;
	
	/**
	 * Конструктор
	 */
	public FloorMap(String path) {
		m_path = path;
		m_chunks = new ArrayList<Chunk>();
		m_chunks.ensureCapacity(Constants.MAP_CHUNK_CACHE_SIZE);
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
	public MapCell getCell(int x, int y) {
		Chunk ch = null;
		// координаты чанка
		int cx = x / Constants.MAP_CHUNK_SIZE;
		int cy = y / Constants.MAP_CHUNK_SIZE;
		// ищем чанк в кэше
		Chunk c;
		int i = 0;
		while (i < m_chunks.size()) {
			c = m_chunks.get(i);
			if (c.m_posX == cx && c.m_posY == cy) {
				ch = c;
				break;
			}
			i++;
		}
		// не нашли в кэше
		if (ch == null) {
			// удаляем лишнее если кэш уже полон
			if (m_chunks.size() == Constants.MAP_CHUNK_CACHE_SIZE) {
				m_chunks.get(0).save();
				m_chunks.remove(0);
			}
			// создаем чанк и заносим в кэш
			ch = new Chunk(this, cx, cy);
			m_chunks.add(ch);
		}
		// индекс клетки в квадрате
		int ci = (y % Constants.MAP_CHUNK_SIZE) * Constants.MAP_CHUNK_SIZE
				+ (x % Constants.MAP_CHUNK_SIZE);
		return ch.getCell(ci);
	}

	/**
	 * Сохранение чанков из буфера. Должно вызываться перед удалением карты.
	 */
	public void SaveChunks() {
		int i = 0;
		while (i < m_chunks.size()) {
			m_chunks.get(i).save();
			i++;
		}
	}
	
	public void translatePerson(Person p, int newX, int newY) {
		// Проверяем можно ли перенести:
		//
		MapCell newCell = getCell(newX, newY);
		if (newCell != null && newCell.isPassable()) {
			// Задаем координаты:
			//
			p.setX(newX);
			p.setY(newY);
			// Удаляем из старого списка:
			//
			MapCell oldCell = p.getCell();
			oldCell.remove(p);
			newCell.addPerson(p);
		}
	}
}
