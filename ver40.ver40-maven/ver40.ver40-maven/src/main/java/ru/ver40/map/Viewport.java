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
	
	private Map<Integer, Color> colorCache;
	
	private FloorMap m_map;
	private int m_width, m_height; // размер
	private int m_offsetX, m_offsetY; // смещение левого верхнего угла
										// относительно центра
	private int m_posX, m_posY; // положение на экране

	public Viewport(FloorMap map, int width, int height, int posX, int posY) {
		m_map = map;
		m_width = width;
		m_height = height;
		m_offsetX = m_width / 2;
		m_offsetY = m_height / 2;
		m_posX = posX;
		m_posY = posY;
		colorCache = new HashMap<Integer, Color>();
	}

	public void draw(AsciiDraw ascii, int x, int y, Graphics gr, Player p) {
		int viewX = x - m_offsetX; // верхний угол вьюпорта
		int viewY = y - m_offsetY;
		if (viewX < 0) // коррекция по границам карты
			viewX = 0;
		else if (viewX + m_width > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - m_width;
		if (viewY < 0)
			viewY = 0;
		else if (viewY + m_height > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - m_height;
		// цикл отрисовки клеток
		for (int i = 0; i < m_width; i++, viewX++) {
			int vy = viewY;
			for (int j = 0; j < m_height; j++, vy++) {
				MapCell c = m_map.getCell(viewX, vy);
				String str = c.getResultString();
				if (c.getVisible() == VisibilityState.VISIBLE) {
					float grad = 0.7f / 15;
					Vector2f trg = new Vector2f(viewX, vy);
					Vector2f src = new Vector2f(p.getX(), p.getY());

					ascii.draw(str, i + m_posX, j + m_posY,
							getColor(c.getResultFg()).darker(
									trg.distance(src) * grad),
							getColor(c.getResultBg()), gr);
				} else if (c.getVisible() == VisibilityState.FOG_OF_WAR) {
					ascii.draw(str, i + m_posX, j + m_posY,
							getColor(c.getResultFg()).darker(0.7f),
							getColor(c.getResultBg()).darker(0.7f), gr);
				} else {
					// невидимые клетки не рисуем
				}
			}
		}
	}

	private Color getColor(int colorHex) {
		Color c = colorCache.get(colorHex);
		if (c == null) {
			c = new Color(colorHex);
			colorCache.put(colorHex, c);
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

	public int getViewX(int x) {
		int viewX = x - m_offsetX;
		if (viewX < 0)
			viewX = 0;
		else if (viewX + m_width > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - m_width;
		return viewX;
	}

	public int geViewY(int y) {
		int viewY = y - m_offsetY;
		if (viewY < 0)
			viewY = 0;
		else if (viewY + m_height > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - m_height;
		return viewY;
	}		 		
}
