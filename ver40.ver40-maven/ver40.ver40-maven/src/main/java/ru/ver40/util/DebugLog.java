package ru.ver40.util;

import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Псевдо-окошко с отладочным логом
 * 
 */
public class DebugLog {
	private int m_posX, m_posY, m_width, m_height;

	private LinkedList<String> m_lines = null;
	private int newCount;

	private static DebugLog m_instance = null;

	public static DebugLog getInstance() {
		if (m_instance == null)
			throw new IllegalStateException(
					"Instance is null. Use create() first.");
		return m_instance;
	}

	public static DebugLog create(int x, int y, int width, int height) {
		if (m_instance == null) {
			m_instance = new DebugLog(x, y, width, height);
		} else {
			throw new IllegalStateException(
					"Object instance is already created. Use getInstance() instead.");
		}
		return m_instance;
	}

	/**
	 * Конструктор
	 * 
	 * Координаты и размеры указываются в символах.
	 */
	private DebugLog(int x, int y, int width, int height) {
		if (x < 0 || y < 0 || width < 0 || height < 0)
			throw new IllegalArgumentException("Invalid arguments: " + x + " "
					+ y + " " + width + " " + height + " (Must be > 0).");
		m_posX = x;
		m_posY = y;
		m_width = width;
		m_height = height;
		m_lines = new LinkedList<String>();
		newCount = 0;
	}

	public void draw(Graphics g) {
		int i = m_height;
		int nc = newCount;
		Color bg = new Color(.0f, .0f, 1.0f, .7f);
		for (String line : m_lines) {
			if (line.length() < m_width) {
				line += StringUtils.repeat(" ", m_width - line.length());
			} else if (line.length() > m_width) {
				line = line.substring(0, m_width - 1);
				line += ">";
			}
			AsciiDraw.getInstance().draw(line, m_posX, i + m_posY,
					nc > 0 ? Color.white : new Color(.75f, .75f, .75f), bg,
					g);
			i--;
			nc--;
			if (i < 0)
				break;
		}
	}

	public void newTurn() {
		if (Config.showDebugLog)
			newCount = 0;
	}

	public void log(String msg) {
		m_lines.addFirst(msg);
		// параноидатьная защита от переполнения
		if (++newCount < 0)
			newCount = 0;
		
		// msg = wordWrap(msg);
		// int i = 0;
		// int l=-1;
		// while (true) {
		// int n = msg.substring(i).indexOf(m_newline);
		// l++;
		// if (n != -1) {
		// m_lines.add(l, msg.substring(i, n - 1));
		// i = n + m_newline.length();
		// } else {
		// m_lines.add(l, msg.substring(i));
		// break;
		// }
		// }
	}

	// private static final String m_newline = System
	// .getProperty("line.separator");
	// private static final String m_separators = " ,.:;{}[]\\|/~()";
	//
	// private String wordWrap(String in) {
	// in = in.trim();
	//
	// if(in.length() < m_width)
	// return in;
	//
	// // If Next length Contains Newline, Split There
	// if (in.substring(0, m_width).contains(m_newline))
	// return in.substring(0, in.indexOf(m_newline) - m_newline.length())
	// + m_newline
	// + wordWrap(in.substring(in.indexOf(m_newline) + 1));
	//
	// // Otherwise, Split Along Nearest Previous Space/Tab/Dash
	// int spaceIndex = -1;
	// for (int i = 0; i < m_separators.length(); i++) {
	// spaceIndex = Math.max(spaceIndex,
	// in.lastIndexOf(m_separators.charAt(i), m_width));
	// }
	//
	// if (spaceIndex == -1)
	// spaceIndex = m_width;
	//
	// // Split
	// return in.substring(0, spaceIndex).trim() + m_newline
	// + wordWrap(in.substring(spaceIndex));
	// }
}
