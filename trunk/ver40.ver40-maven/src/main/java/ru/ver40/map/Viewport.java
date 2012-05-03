package ru.ver40.map;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import ru.ver40.model.MapCell;
import ru.ver40.model.Player;
import ru.ver40.model.VisibilityState;
import ru.ver40.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Рисует область карты на экране
 * 
 */
public class Viewport {	
	
	private Map<Integer, Color> m_colorCache; // кэш объектов Color для рендера
	
	private FloorMap m_map;
	private int m_width, m_height; // размер в символах
	private int m_offsetX, m_offsetY; // расстояние от центра до края
	private int m_posX, m_posY; // положение на экране

	/**
	 * Конструктор
	 */
	public Viewport(FloorMap map, int width, int height, int posX, int posY) {
		m_map = map;
		m_width = width;
		m_height = height;
		m_offsetX = m_width / 2;
		m_offsetY = m_height / 2;
		m_posX = posX;
		m_posY = posY;
		m_colorCache = new HashMap<Integer, Color>();
	}

	/**
	 * Рендер вьюпорта
	 */
	public void draw(int x, int y, Graphics gr, Player p) {
		int viewX = getViewX(x); // верхний угол вьюпорта
		int viewY = getViewY(y);
		// цикл отрисовки клеток
		for (int i = 0; i < m_width; i++, viewX++) {
			int vy = viewY;
			for (int j = 0; j < m_height; j++, vy++) {
				MapCell c = m_map.getCell(viewX, vy);
				String str = c.getResultString();
				if (c.getVisible() == VisibilityState.VISIBLE) {
					// затенение освещения
					// TODO радиус обзора должен быть в кричере (Player)?
					float grad = 0.8f / 15;
					Vector2f trg = new Vector2f(viewX, vy);
					Vector2f src = new Vector2f(p.getX(), p.getY());

					AsciiDraw.getInstance().draw(
							str,
							i + m_posX,
							j + m_posY,
							getColor(c.getResultFg()).darker(
									trg.distance(src) * grad),
							getColor(c.getResultBg()), gr);
				} else if (c.getVisible() == VisibilityState.FOG_OF_WAR) {
					AsciiDraw.getInstance().draw(str, i + m_posX, j + m_posY,
							getColor(c.getResultFg()).darker(0.8f),
							getColor(c.getResultBg()).darker(0.8f), gr);
				} else {
					// невидимые клетки не рисуем
				}
			}
		}
	}

	/**
	 * Получить объект Color из кэша
	 * 
	 * @return
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

	/**
	 * Получить валидные координаты левого верхнего угла вьюпорта по желаемому
	 * положению на карте его центра
	 * 
	 * @return
	 */
	public int getViewX(int x) {
		int viewX = x - m_offsetX;
		if (viewX < 0)
			viewX = 0;
		else if (viewX + m_width > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - m_width;
		return viewX;
	}

	/**
	 * Получить валидные координаты левого верхнего угла вьюпорта по желаемому
	 * положению на карте его центра
	 * 
	 * @return
	 */
	public int getViewY(int y) {
		int viewY = y - m_offsetY;
		if (viewY < 0)
			viewY = 0;
		else if (viewY + m_height > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - m_height;
		return viewY;
	}		 		
}
