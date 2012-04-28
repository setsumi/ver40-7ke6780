package ru.ver40.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ver40.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Рисует область карты на экране
 * 
 */
public class Viewport {
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
				Cell c = m_map.getCell(viewX, vy);
				String str = " ";
				Color col = Color.lightGray;
				switch (c.type) {
				case Cell.TYPE_WALL:
					str = "#";
					break;
				case Cell.TYPE_FLOOR:
					str = ".";
					break;
				}
				ascii.draw(str, i + m_posX, j + m_posY, col, Color.darkGray, gr); //
			}
		}
	}
}
