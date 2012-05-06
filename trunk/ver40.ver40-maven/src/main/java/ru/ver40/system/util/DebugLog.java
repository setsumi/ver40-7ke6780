package ru.ver40.system.util;

import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.newdawn.slick.Graphics;

import ru.ver40.util.Constants;

/**
 * Псевдо-окошко с отладочным логом.
 * 
 * Синглтон.
 */
public class DebugLog {

	private static DebugLog m_instance = null;

	private int m_posX, m_posY, m_width, m_height;
	private LinkedList<String> m_lines = null;
	private int m_newCount; // счетчик свежих записей

	public static boolean showLog = false; // рисовать лог или нет (рендеру
											// приложения)

	/**
	 * Создать единственный объект класса.
	 * 
	 * Координаты и размеры указываются в символах.
	 * 
	 * @return
	 */
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
	 * Вернуть единственный объект класса.
	 * 
	 * @return
	 */
	public static DebugLog getInstance() {
		if (m_instance == null)
			throw new IllegalStateException(
					"Instance is null. Use create() first.");
		return m_instance;
	}

	/**
	 * Конструктор.
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
		m_newCount = 0;
	}

	/**
	 * Рендер лога
	 */
	public void draw(Graphics g) {
		if (!DebugLog.showLog)
			return;

		int i = m_height;
		int nc = m_newCount;
		for (String line : m_lines) {
			String msg = new String(line);
			if (msg.length() < m_width) {
				msg += StringUtils.repeat(" ", m_width - msg.length());
			} else if (msg.length() > m_width) {
				msg = msg.substring(0, m_width - 1);
				msg += ">";
			}
			AsciiDraw.getInstance().draw(
					msg,
					m_posX,
					i + m_posY,
					nc > 0 ? Constants.DEBUG_LOG_COLOR
							: Constants.DEBUG_LOG_COLOR
									.darker(Constants.LOG_FADE_FACTOR),
					Constants.DEBUG_LOG_BACKCOLOR, g);
			i--;
			nc--;
			if (i < 0)
				break;
		}
	}

	/**
	 * Сброс счетчика новых записей.
	 * 
	 * Обычно делается после скрытия лога и каждый ход.
	 */
	public void resetNew() {
		if (showLog)
			m_newCount = 0;
	}

	/**
	 * Добавить запись в лог.
	 */
	public void log(String msg) {
		m_lines.addFirst(msg);
		// ограничение размера лога
		if (m_lines.size() > Constants.LOG_MAX_SIZE)
			m_lines.removeLast();

		if (++m_newCount < 0)// параноидальная защита от переполнения
			m_newCount = m_height;
		
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
