package ru.ver40.map;

import java.io.File;
import java.util.ArrayList;

import rlforj.los.ILosBoard;
import ru.ver40.model.Actor;
import ru.ver40.model.MapCell;
import ru.ver40.model.Player;
import ru.ver40.model.VisibilityState;
import ru.ver40.util.Constants;


/**
 * Карта в виде большого 2D-поля
 * 
 */
public class FloorMap implements ILosBoard {
	/**
	 * Буфер чанков
	 */
	private ArrayList<Chunk> m_chunks;
	/**
	 * Путь к каталогу с данными карты
	 */
	private String m_path;
	
	private ArrayList<MapCell> oldVisible;
	
	private Player player;
	
	/**
	 * Конструктор
	 * 
	 * Получает путь к каталогу для данных карты
	 */
	public FloorMap(String path) {
		File f = new File(path);
		if (!f.isDirectory())
			throw new IllegalArgumentException("Invalid path to map data: "
					+ path);

		m_path = path;
		m_chunks = new ArrayList<Chunk>();
		m_chunks.ensureCapacity(Constants.MAP_CHUNK_CACHE_SIZE);
		oldVisible = new ArrayList<MapCell>();
	}

	/**
	 * Вернуть путь к каталогу с данным карты
	 * 
	 * @return
	 */
	public String getPath() {
		return m_path;
	}

	/**
	 * Вернуть клетку по её координатам на карте.
	 * 
	 * Создает новые чанки по мере необходимости.
	 * 
	 * @return
	 */
	public MapCell getCell(int x, int y) {
		if (!contains(x, y))
			new IllegalArgumentException("Invalid map coordinates: " + x + ", "
					+ y + " (Must be 0.." + (Constants.MAP_MAX_SIZE - 1));

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
	 * Сохранение на диск чанков из буфера.
	 * 
	 * Должно вызываться перед удалением карты. Чанки, вытесненные из буфера
	 * естественным путем, сохраняются автоматически.
	 */
	public void SaveChunks() {
		int i = 0;
		while (i < m_chunks.size()) {
			m_chunks.get(i).save();
			i++;
		}
	}
	
	/**
	 * Перемещает персонажа на карте.
	 * Если перемещение возможно, возвращает null.
	 * Если перемещенеи невозможно, возвращает MapCell, 
	 * на который персонаж наткнулся
	 * @param p - персонаж
	 * @param newX - новый X
	 * @param newY - новый Y
	 * @return - непроходимую ячейку или null
	 */
	public MapCell translateActor(Actor p, int newX, int newY) {
		// Проверяем можно ли перенести:
		//
		if (newX >= 0 && newY >= 0 && newX < Constants.MAP_MAX_SIZE
				&& newX < Constants.MAP_MAX_SIZE) {
			MapCell newCell = getCell(newX, newY);
			if (newCell != null && newCell.isPassable()) {
				// Проверить нет ли на ней других персонажей
				//
				if (newCell.getPersons().isEmpty()) {
					// Задаем координаты:
					//
					p.setX(newX);
					p.setY(newY);
					// Удаляем из старого списка:
					//
					MapCell oldCell = p.getCell();
					oldCell.remove(p);
					newCell.addPerson(p);
					return null;
				} else {
					return newCell;
				}
			} else {
				return newCell;
			}
		}
		return new MapCell();
	}

	@Override
	public boolean contains(int x, int y) {
		return x >= 0 && y >= 0 && x < Constants.MAP_MAX_SIZE
				&& y < Constants.MAP_MAX_SIZE;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return !getCell(x, y).isPassable() ;
	}

	@Override
	public void visit(int x, int y) {		
		MapCell cell = getCell(x, y);
		cell.setVisible(VisibilityState.VISIBLE);
		oldVisible.add(cell);		
	}
	
	// Поменить все видимые клетки вокруг игрока как FOG_OF_WAR
	public void setFogOfWar() {
		for (MapCell cell : oldVisible) {
			cell.setVisible(VisibilityState.FOG_OF_WAR);
		}
		oldVisible.clear();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
