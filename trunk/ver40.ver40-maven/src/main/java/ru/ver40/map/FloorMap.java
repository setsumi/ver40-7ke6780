package ru.ver40.map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import rlforj.los.ILosAlgorithm;
import rlforj.los.ILosBoard;
import rlforj.los.PrecisePermissive;
import rlforj.math.Point2I;
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

	public class CellLocation {
		public Chunk m_chunk; // Указатель на чанк.
		public int m_index; // Линейный индекс клетки в чанке.

		public CellLocation(Chunk chunk, int index) {
			m_chunk = chunk;
			m_index = index;
		}
	}

	/**
	 * Путь к каталогу с данными карты.
	 */
	private String m_path;
	/**
	 * Буфер чанков.
	 */
	private ArrayList<Chunk> m_chunks;
	/**
	 * Список линейных индексов чанков, которые были созданы в процессе игры.
	 */
	private HashSet<Integer> m_seenChunks;

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
		m_chunks = new ArrayList<Chunk>(Constants.MAP_CHUNK_CACHE_SIZE);
		m_seenChunks = new HashSet<Integer>();
		oldVisible = new ArrayList<MapCell>();
	}

	/**
	 * Вернуть путь к каталогу с данным карты
	 */
	public String getPath() {
		return m_path;
	}

	public CellLocation getChunk(int x, int y) {
		// TODO разобраться с этим хламом от миникарты
		CellLocation loc = locateCell(x, y);
		// Image ret = loc.m_chunk.getMiniMap();
		return loc;
	}

	/**
	 * Вернуть чанк и индекс в чанке по координатам клетки на карте.
	 * 
	 * Создает новые чанки по мере необходимости.
	 */
	public CellLocation locateCell(int x, int y) {
		if (!contains(x, y))
			new IllegalArgumentException("Invalid map coordinates: " + x + ", "
					+ y + " (Must be 0.." + (Constants.MAP_MAX_SIZE - 1));

		// координаты чанка
		int cx = x / Constants.MAP_CHUNK_SIZE;
		int cy = y / Constants.MAP_CHUNK_SIZE;
		// линейный индекс чанка
		int cli = cy * Constants.MAP_MAX_SIZE_CHUNKS + cx;
		m_seenChunks.add(cli);
		// ищем чанк в кэше
		Chunk ch = null, c = null;
		int i = 0;
		while (i < m_chunks.size()) {
			c = m_chunks.get(i);
			if (c.getIndex() == cli) { // нашли в кэше
				ch = c;
				// переносим в конец, чтобы дольше жил
				if (i != m_chunks.size() - 1) {
					m_chunks.remove(i);
					m_chunks.add(c);
				}
				break;
			}
			i++;
		}
		// не нашли в кэше
		if (ch == null) {
			// удаляем лишнее если кэш уже полон
			if (m_chunks.size() == Constants.MAP_CHUNK_CACHE_SIZE) {
				// m_chunks.get(0).save();
				m_chunks.remove(0);
			}
			// создаем чанк и заносим в кэш (в конец, а старые должны всплыть в
			// начало)
			ch = new Chunk(this, cli);
			m_chunks.add(ch);
		}
		// индекс клетки в квадрате
		int ci = (y % Constants.MAP_CHUNK_SIZE) * Constants.MAP_CHUNK_SIZE
				+ (x % Constants.MAP_CHUNK_SIZE);
		return new CellLocation(ch, ci);
	}

	/**
	 * Вернуть клетку по её координатам на карте.
	 * 
	 * Создает новые чанки по мере необходимости.
	 */
	public MapCell getCell(int x, int y) {
		CellLocation loc = locateCell(x, y);
		return loc.m_chunk.getCell(loc.m_index);
	}

	/**
	 * Присвоить клетку по её координатам на карте.
	 * 
	 * Создает новые чанки по мере необходимости.
	 */
	public void setCell(MapCell cell, int x, int y) {
		CellLocation loc = locateCell(x, y);
		loc.m_chunk.setCell(cell, loc.m_index);
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
	 * Перемещает персонажа на карте. Если перемещение возможно, возвращает
	 * null. Если перемещенеи невозможно, возвращает MapCell, на который
	 * персонаж наткнулся
	 * 
	 * @param p
	 *            - персонаж
	 * @param newX
	 *            - новый X
	 * @param newY
	 *            - новый Y
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
				if (newCell.getBuilding() != null && newCell.getBuilding().isActive()) {
					newCell.getBuilding().use();
					return null;
				}
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
		return !getCell(x, y).isPassable();
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

	/**
	 * Получить линию зрения от одной точки до другой.
	 */
	public List<Point2I> getLosLine(int fromX, int fromY, int toX, int toY) {
		ILosAlgorithm los = new PrecisePermissive(); // BresLos(false);
		los.existsLineOfSight(this, fromX, fromY, toX, toY, true);
		List<Point2I> ret = los.getProjectPath();
		if (ret == null) {
			ret = new Vector<Point2I>();
		}
		return ret;
	}

	/**
	 * Приводит произвольную координату X или Y в пределы диапазона координат
	 * карты (задвигает внутрь, если вылезла за края).
	 */
	public static int normalizePos(int xy) {
		if (xy < 0)
			xy = 0;
		if (xy > Constants.MAP_MAX_SIZE - 1)
			xy = Constants.MAP_MAX_SIZE - 1;
		return xy;
	}

	/**
	 * Получить валидные координаты левого верхнего угла окна вьюпорта по
	 * желаемому положению на карте его центра.
	 * 
	 * @param y
	 *            - координата окна (в центре, если окно не у края карты)
	 * @param w
	 *            - ширина окна
	 */
	public int getViewRectX(int x, int w) {
		int viewX = x - (w / 2);
		if (viewX < 0)
			viewX = 0;
		else if (viewX + w > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - w;
		return viewX;
	}

	/**
	 * Получить валидные координаты левого верхнего угла окна вьюпорта по
	 * желаемому положению на карте его центра.
	 * 
	 * @param y
	 *            - координата (в центре, если окно не у края карты)
	 * @param h
	 *            - высота окна
	 */
	public int getViewRectY(int y, int h) {
		int viewY = y - (h / 2);
		if (viewY < 0)
			viewY = 0;
		else if (viewY + h > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - h;
		return viewY;
	}
}
