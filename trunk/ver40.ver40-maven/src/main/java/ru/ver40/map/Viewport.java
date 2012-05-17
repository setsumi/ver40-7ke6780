package ru.ver40.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.IntRange;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import ru.ver40.model.Actor;
import ru.ver40.model.MapCell;
import ru.ver40.model.Player;
import ru.ver40.model.VisibilityState;
import ru.ver40.service.TimeService;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.util.Constants;


/**
 * Рисует область карты на экране
 * 
 */
public class Viewport {
	
	private Map<Integer, Color> m_colorCache; // кэш объектов Color для рендера
	
	private FloorMap m_map;
	private int m_scrPosX, m_scrPosY; // положение на экране
	private int m_width, m_height; // размер в символах
	private int m_areaWidth, m_areaHeight; // активная зона вокруг

	private int m_mapPosX, m_mapPosY; // положение вьюпорта на карте
	private int m_topX, m_topY; // верхний левый угол вьюпорта на карте

	/**
	 * Конструктор
	 */
	public Viewport(FloorMap map, int width, int height, int scrPosX,
			int scrPosY) {
		m_map = map;
		m_width = width;
		m_height = height;
		m_areaWidth = (int) ((float) m_width * Constants.VIEWPORT_MAP_ACTIVE_AREA_FACTOR);
		m_areaHeight = (int) ((float) m_height * Constants.VIEWPORT_MAP_ACTIVE_AREA_FACTOR);
		m_scrPosX = scrPosX;
		m_scrPosY = scrPosY;
		m_colorCache = new HashMap<Integer, Color>();
		moveTo(0, 0);
	}

	/**
	 * Переместить вьюпорт в указанную точку на карте.
	 */
	public void moveTo(int x, int y) {
		m_mapPosX = FloorMap.normalizePos(x);
		m_mapPosY = FloorMap.normalizePos(y);
		m_topX = m_map.getViewRectX(m_mapPosX, m_width);
		m_topY = m_map.getViewRectY(m_mapPosY, m_height);

		// Определение активной зоны.
		// TODO для всяких обзоров, наверное не надо зону таскать за собой
		int areaX = m_map.getViewRectX(m_mapPosX, m_areaWidth);
		int areaY = m_map.getViewRectY(m_mapPosY, m_areaHeight);
		MapCell cell;
		List<Actor> persons;
		TimeService timeService = TimeService.getInstance();
		// Останавляваем всех выпавших из активной зоны.
		//
		timeService.unregisterNotInArea(new IntRange(areaX, areaX + m_areaWidth
				- 1), new IntRange(areaY, areaY + m_areaHeight - 1));
		// Активируем всех в активной зоне.
		//
		for (int i = areaX; i < areaX + m_areaWidth; i++) {
			for (int j = areaY; j < areaY + m_areaHeight; j++) {
				cell = m_map.getCell(i, j);
				persons = cell.getPersons();
				for (Actor a : persons) {
					if (!timeService.isRegistered(a)) {
						timeService.register(a);
					}
				}
			}
		}
	}

	/**
	 * Сместить вьюпорт относительно его текущего положения.
	 */
	public void moveBy(int x, int y) {
		moveTo(m_mapPosX + x, m_mapPosY + y);
	}

	/**
	 * Нарисовать строку во вьюпорте по указанным координатам на карте.
	 */
	public void drawString(String str, int x, int y, Color fc) {
		drawString(str, x, y, fc, null, null);
	}

	/**
	 * Нарисовать строку во вьюпорте по указанным координатам на карте.
	 */
	public void drawString(String str, int x, int y, Color fc, Color bc,
			Graphics g) {
		if (!m_map.contains(x, y))
			throw new IllegalArgumentException("Invalid map coordinates x=" + x
					+ ", y=" + y);
		// Не рисуем если не попадает во вьюпорт.
		if (x < m_topX || x >= m_topX + m_width || y < m_topY
				|| y >= m_topY + m_height)
			return;

		// Отрисовка.
		if (bc == null) {
			AsciiDraw.getInstance().draw(str, m_scrPosX + (x - m_topX),
					m_scrPosY + (y - m_topY), fc);
		} else {
			AsciiDraw.getInstance().draw(str, m_scrPosX + (x - m_topX),
					m_scrPosY + (y - m_topY), fc, bc, g);
		}
	}

	/**
	 * Рендер вьюпорта в его положении на карте. См. moveTo(), moveBy().
	 */
	public void draw(Graphics gr, Player p) {
		draw(m_mapPosX, m_mapPosY, gr, p);
	}

	/**
	 * Рендер вьюпорта в произвольной точке карты.
	 */
	private void draw(int x, int y, Graphics gr, Player p) {
		int viewX = m_map.getViewRectX(x, m_width); // верхний угол вьюпорта
		int viewY = m_map.getViewRectY(y, m_height);
		// цикл отрисовки клеток
		for (int i = 0; i < m_width; i++, viewX++) {
			int vy = viewY;
			for (int j = 0; j < m_height; j++, vy++) {
				MapCell c = m_map.getCell(viewX, vy);
				String str = c.getResultString();
				float fade = 0.3f; // TODO DEBUG!!!
				if (c.getVisible() == VisibilityState.VISIBLE) {
					// затенение освещения
					// TODO ! А ТО -> радиус обзора должен быть в кричере?
					//
					float grad = fade / 15;
					Vector2f trg = new Vector2f(viewX, vy);
					Vector2f src = new Vector2f(p.getX(), p.getY());

					AsciiDraw.getInstance().draw(
							str,
							i + m_scrPosX,
							j + m_scrPosY,
							getColor(c.getResultFg()).darker(
									trg.distance(src) * grad),
							getColor(c.getResultBg()), gr);
				} else if (c.getVisible() == VisibilityState.FOG_OF_WAR) {
					AsciiDraw.getInstance().draw(str, i + m_scrPosX, j + m_scrPosY,
							getColor(c.getResultFg()).darker(fade),
							getColor(c.getResultBg()).darker(fade), gr);
				} else {
					// Для дебага
					//
					AsciiDraw.getInstance().draw(str, i + m_scrPosX,
							j + m_scrPosY,
							getColor(c.getResultFg()).darker(fade),
							getColor(c.getResultBg()).darker(fade), gr);
				}
			}
		}
	}

	/**
	 * Получить объект Color из кэша
	 */
	private Color getColor(int colorHex) {
		Color c = m_colorCache.get(colorHex);
		if (c == null) {
			c = new Color(colorHex);
			m_colorCache.put(colorHex, c);
		}
		return c;
	}
	
	public MapCell getCell(int cIndex) {
		int y = cIndex % m_width;
		int x = cIndex - (cIndex * y);
		return m_map.getCell(x, y);
	}

	public FloorMap getMap() {
		return m_map;
	}
	
	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}

	public int getMapPosX() {
		return m_mapPosX;
	}

	public int getMapPosY() {
		return m_mapPosY;
	}

}
