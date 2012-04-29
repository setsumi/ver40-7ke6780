package ru.ver40.map;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ver40.model.MapCell;
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

	public void drawRaw(AsciiDraw ascii, int x, int y, Graphics gr) {
		int viewX = x - m_offsetX;
		int viewY = y - m_offsetY;
		if (viewX < 0)
			viewX = 0;
		else if (viewX + m_width > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - m_width;
		if (viewY < 0)
			viewY = 0;
		else if (viewY + m_height > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - m_height;

		for (int i = 0; i < m_width; i++, viewX++) {
			int vy = viewY;
			for (int j = 0; j < m_height; j++, vy++) {
				MapCell c = m_map.getCell(viewX, vy);				
				String str = c.getResultString();
				
				ascii.draw(str, i + m_posX, j + m_posY, getColor(c.getResultFg()), getColor(c.getResultBg()), gr); //
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
}
