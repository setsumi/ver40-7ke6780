package ru.ver40.util;

import java.util.LinkedList;

/**
 * Псевдо-окошко для вывода логов в игре.
 * 
 */
public class LogWindow {
	private int m_posX, m_posY, m_width, m_height;

	private LinkedList<String> m_lines = null;

	/**
	 * Конструктор
	 * 
	 * Координаты и размеры указываются в символах.
	 */
	public LogWindow(int x, int y, int width, int height) {
		if (x < 0 || y < 0 || width < 0 || height < 0)
			throw new IllegalArgumentException("Invalid parameters: " + x + " "
					+ y + " " + width + " " + height + " (Must be > 0).");
		m_lines = new LinkedList<String>();
	}
}
