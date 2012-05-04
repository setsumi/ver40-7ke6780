package ru.ver40.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Псевдо-окошко с игровым логом
 * 
 */
public class GameLog {
	/**
	 * Типы записей лога.
	 * 
	 * Соответствующие цвета в Constants.
	 */
	public static enum Type {
		IMPORTANT("IMPORTANT"), REGULAR("REGULAR");
		private final String m_name;

		private Type(String name) {
			m_name = name;
		}

		public String getName() {
			return m_name;
		}
	}

	private class Entry {
		Type type;
		String msg;
		int stack;

		Entry(Type type, String msg) {
			this.type = type;
			this.msg = msg;
			stack = 1;
		}
	}

	private int m_posX, m_posY, m_width, m_height;
	private Color m_backColor = null;
	private LinkedList<Entry> m_lines = null;
	private int m_newCount; // счетчик свежих записей
	private PrintStream m_out; // файл куда дампить лог

	/**
	 * Конструктор
	 * 
	 * Координаты и размеры указываются в символах.
	 */
	public GameLog(int x, int y, int width, int height, Color bg) {
		if (x < 0 || y < 0 || width < 0 || height < 0)
			throw new IllegalArgumentException("Invalid arguments: " + x + " "
					+ y + " " + width + " " + height + " (Must be > 0).");
		m_posX = x;
		m_posY = y;
		m_width = width;
		m_height = height;
		m_backColor = new Color(bg);
		m_lines = new LinkedList<Entry>();
		m_newCount = 0;
		try {
			m_out = new PrintStream(new FileOutputStream(
					Constants.GAME_LOG_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Рендер лога
	 */
	public void draw(Graphics g) {
		int i = 0, posY = m_posY;
		int nc = m_newCount;
		int height = m_height;
		// поднимаем верхнюю границу лога если новые записи не помещаются
		if (nc > height) {
			posY -= nc - height;
			height += nc - height;
			if (posY < 0)
				posY = 0;
		}
		for (Entry line : m_lines) {
			// обрезаем длинные строки и добавляем счетчик наложений если
			// необходимо
			String msg = new String(line.msg);
			String tail = "";
			if (line.stack > 1)
				tail = " [" + line.stack + "]";
			msg += tail;
			msg += StringUtils.repeat(" ", m_width - msg.length());
			if (msg.length() > m_width) {
				if (tail.length() > 0)
					tail = tail.replaceFirst("\\A.", ">");
				else
					tail = ">";
				msg = msg.substring(0, m_width - tail.length());
				msg += tail;
			}
			// рисовка
			AsciiDraw.getInstance().draw(
					msg,
					m_posX,
					posY + i,
					nc > 0 ? getColor(line.type) : getColor(line.type).darker(
							Constants.LOG_FADE_FACTOR), m_backColor, g);
			i++;
			nc--;
			if (i >= height)
				break;
		}
	}

	private Color getColor(Type type) {
		switch (type) {
		case IMPORTANT:
			return Constants.GAME_LOG_IMPORTANTCOLOR;
		}
		return Constants.GAME_LOG_REGULARCOLOR;
	}

	/**
	 * Сброс счетчика новых записей.
	 * 
	 * Обычно делается перед началом каждого ход. Подразумевается, что игровой
	 * лог нельзя прятать как отладочный.
	 */
	public void resetNew() {
		m_newCount = 0;
	}

	/**
	 * Добавить запись в лог.
	 */
	public void log(String msg) {
		log(Type.REGULAR, msg);
	}

	/**
	 * Добавить запись в лог.
	 */
	public void log(Type type, String msg) {
		// пишем в файл
		m_out.println(Calendar.getInstance().getTime() + " " + type.getName()
				+ ":" + msg);

		// наложение одинаковых последних записей
		Entry prev = null;
		if (!m_lines.isEmpty())
			prev = m_lines.getFirst();
		if (prev != null && prev.msg.compareTo(msg) == 0) {
			prev.type = type;
			prev.stack++;
			if (m_newCount > 0)
				m_newCount--;
		} else {
			// добавляем запись
			Entry entry = new Entry(type, msg);
			m_lines.addFirst(entry);
			// ограничение размера лога
			if (m_lines.size() > Constants.LOG_MAX_SIZE)
				m_lines.removeLast();
		}
		if (++m_newCount < 0) // параноидальная защита от переполнения
			m_newCount = m_height;
	}
}
