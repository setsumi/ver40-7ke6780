package ru.ver40.system.util;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import ru.ver40.util.Constants;


/**
 * Вывод на экран ASCII-строк.
 * 
 * Синглтон.
 */
public class AsciiDraw {

	/**
	 * Единственный экземпляр класса.
	 */
	private static AsciiDraw m_instance = null;

	/**
	 * SpriteSheet с изображением символов
	 */
	private SpriteSheet m_font;
	/**
	 * ширина одного символа
	 */
	private int m_width;
	/**
	 * высота одного символа
	 */
	private int m_height;

	/**
	 * Вернуть единственный экземпляр класса.
	 */
	public static AsciiDraw getInstance() {
		if (m_instance == null) {
			m_instance = new AsciiDraw();
		}
		return m_instance;
	}

	/**
	 * Конструктор
	 */
	private AsciiDraw() {
		m_font = ResourceManager.getSpriteSheet(Constants.ASCII_FONT_KEY);
		m_width = m_font.getWidth() / m_font.getHorizontalCount();
		m_height = m_font.getHeight() / m_font.getVerticalCount();
	}

	/**
	 * Ширина символа в пикселах
	 */
	public int getWidth() {
		return m_width;
	}

	/**
	 * Высота символа в пикселах
	 */
	public int getHeight() {
		return m_height;
	}

	/**
	 * Залитый цветом прямоугольник.
	 */
	public void drawRect(int x, int y, int w, int h, Color c) {
		int dx, dy;
		m_font.startUse();
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				dx = i * m_width;
				dy = j * m_height;
				// залитый квадратик фона идет самым первым в битмапе фонта
				m_font.drawEmbedded(dx, dy, dx + m_width, dy + m_height, 0, 0,
						m_width, m_height, c);
			}
		}
		m_font.endUse();
	}

	/**
	 * Вывод строки на экран в посимвольных координатах
	 */
	public void draw(String str, int x, int y, Color fg) {
		draw(str, x, y, fg, null, null);
	}

	/**
	 * Вывод строки на экран в посимвольных координатах
	 */
	public void draw(String str, int x, int y, Color fg, Color bg, Graphics g) {
		if (str == null || x < 0 || y < 0 || fg == null)
			throw new IllegalArgumentException("Invalid arguments.");

		int len = str.length();
		if (len == 0)
			return;

		Point p = null;
		int dx, dy, sx, sy;
		m_font.startUse();
		for (int i = 0; i < len; i++) {
			// куда ложить символ на экране
			dx = (x + i) * m_width;
			dy = y * m_height;
			// рисуем фон
			if (bg != null) {
				// залитый квадратик фона идет самым первым в битмапе фонта
				m_font.drawEmbedded(dx, dy, dx + m_width, dy + m_height, 0, 0,
						m_width, m_height, bg);
			}
			// рисуем символ
			p = codeConvert((int) str.charAt(i));
			sx = p.x * m_width;
			sy = p.y * m_height;
			m_font.drawEmbedded(dx, dy, dx + m_width, dy + m_height, sx, sy, sx
					+ m_width, sy + m_height, fg);
		}
		m_font.endUse();
	}

	/**
	 * Вывод строки на экран в попиксельных координатах
	 */
	public void drawFree(String str, int x, int y, Color fg) {
		drawFree(str, x, y, fg, null, null);
	}

	/**
	 * Вывод строки на экран в попиксельных координатах
	 */
	public void drawFree(String str, int x, int y, Color fg, Color bg,
			Graphics g) {
		if (str == null || x < 0 || y < 0 || fg == null)
			throw new IllegalArgumentException("Invalid arguments.");

		int len = str.length();
		if (len == 0)
			return;

		// рисуем символы
		Point p = null;
		int dx, dy, sx, sy;
		m_font.startUse();
		for (int i = 0; i < len; i++) {
			// куда ложить символ на экране
			dx = x + (i * m_width);
			dy = y;
			// рисуем фон
			if (bg != null) {
				// залитый квадратик фона идет самым первым в битмапе фонта
				m_font.drawEmbedded(dx, dy, dx + m_width, dy + m_height, 0, 0,
						m_width, m_height, bg);
			}
			// рисуем символ
			p = codeConvert((int) str.charAt(i));
			sx = p.x * m_width;
			sy = p.y * m_height;
			m_font.drawEmbedded(dx, dy, dx + m_width, dy + m_height, sx, sy, sx
					+ m_width, sy + m_height, fg);
		}
		m_font.endUse();
	}

	/**
	 * Перевод кода символа в координаты на матрице SpriteSheet-а шрифта
	 */
	private Point codeConvert(int code) {
		if ((code >= 1040) && (code <= 1087)) { // А-Яа-п
			code -= 912;
		} else if ((code >= 1088) && (code <= 1103)) { // р-я
			code -= 864;
		} else if (code == 1025) { // Ё
			code = 240;
		} else if (code == 1105) { // ё
			code = 241;
		} else if (code > 254) { // вопросик вместо
									// непечатных символов
			code = 63;
		}
		int sy = code / Constants.ASCII_SCREEN_WIDTH;
		int sx = code - (Constants.ASCII_SCREEN_WIDTH * sy);
		return new Point(sx, sy);
	}
}
