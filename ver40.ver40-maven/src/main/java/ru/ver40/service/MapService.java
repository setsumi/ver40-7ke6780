package ru.ver40.service;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.ver40.StateGameplay;
import ru.ver40.map.FloorMap;
import ru.ver40.map.gen.FeatureGenerator;
import ru.ver40.map.gen.IMapGenarator;
import ru.ver40.model.MapCell;
import ru.ver40.util.Constants;

/**
 * Сервис, управляющий уровнями Города в игре.
 * 
 */
public class MapService {
	private static MapService m_instance;

	public static MapService getInstance() {
		if (m_instance == null) {
			m_instance = new MapService();
		}
		return m_instance;
	}
	
	/**
	 * Класс представления уровня.
	 */
	public class Level {
		public int m_levelNum; // номер уровня
		public FloorMap m_map; // карта уровня

		public Level(int level, FloorMap map) {
			this.m_levelNum = level;
			this.m_map = map;
		}
	}

	private List<Level> m_levels; // все существующие уровни
	private Level m_currLevel; // текущий уровень
	private Level m_prevLevel; // предыдущий уровень

	/**
	 * Конструктор.
	 */
	private MapService() {
		m_levels = new ArrayList<Level>();
		m_currLevel = null;
		m_prevLevel = null;
	}

	/**
	 * Перейти на указанный уровень.
	 */
	public void gotoLevel(int level, int x, int y) {
		Level target = findLevel(level);
		// Создаем уровень если запрошен не существующий.
		if (target == null) {
			target = new Level(level, createMap(level, x, y));
			m_levels.add(target);
		}
		// Присваиваем новый текущий и предыдущий уровень.
		m_prevLevel = m_currLevel;
		m_currLevel = target;
		//
		StateGameplay.getInstance().newMap(x, y);
	}

	/**
	 * Вернуть текущую карту.
	 */
	public FloorMap getMap() {
		return m_currLevel.m_map;
	}

	/**
	 * Вернуть номер уровня текущей карты.
	 */
	public int getLevelNum() {
		return m_currLevel.m_levelNum;
	}

	/**
	 * Вернуть текущий уровень.
	 */
	public Level getLevel() {
		return m_currLevel;
	}

	/**
	 * Вернуть предыдущий уровень.
	 */
	public Level getPrevLevel() {
		return m_prevLevel;
	}

	/**
	 * Поиск уровня в существующих.
	 * 
	 * @return null, если не найден
	 */
	private Level findLevel(int level) {
		Level ret = null;
		for (Level i : m_levels) {
			if (i.m_levelNum == level) {
				ret = i;
				break;
			}
		}
		return ret;
	}

//	/**
//	 * Поиск уровня в существующих.
//	 * 
//	 * @return null, если не найден
//	 */
//	private Level findLevel(FloorMap map) {
//		Level ret = null;
//		for (Level i : levels) {
//			if (i.map == map) {
//				ret = i;
//				break;
//			}
//		}
//		return ret;
//	}

	/**
	 * Создать новую карту уровня.
	 * 
	 * @param x
	 *            начальное положение игрока на карте
	 * @param y
	 *            начальное положение игрока на карте
	 * @return созданная карта
	 */
	private FloorMap createMap(int level, int x, int y) {
		// Создаем папки для данных карты.
		//
		String dir = Constants.LEVELS_DIRECTORY;
		File f = new File(dir);
		if (!f.isDirectory() && !f.mkdir()) {
			throw new RuntimeException("Can't create map directory '" + dir
					+ "'");
		}
		dir += File.separator + level;
		f = new File(dir);
		if (!f.isDirectory() && !f.mkdir()) {
			throw new RuntimeException("Can't create map directory '" + dir
					+ "'");
		}
		// Создаем карту.
		//
		FloorMap map = new FloorMap(dir);
		IMapGenarator gen = new FeatureGenerator(400, 400);
		gen.generate(map);
		// Размещаем транспортер.
		//
		Point trans = map.getNearestBuildable(x, y, 200);
		int transLevel = m_currLevel == null ? level + 1 : m_currLevel.m_levelNum;
		map.setCell(MapCell.createLevelTransporter(transLevel, trans.x, trans.y),
				trans.x, trans.y);

		return map;
	}
}
