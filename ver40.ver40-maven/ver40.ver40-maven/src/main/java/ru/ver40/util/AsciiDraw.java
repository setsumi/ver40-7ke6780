package ru.ver40.util;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import ru.ver40.engine.ResourceManager;


/**
 * Отрисовка ASCII-символов
 */
public class AsciiDraw {

	private SpriteSheet font = null;
	private Image letter = null;
	private int width = 0; // ширина одного символа
	private int height = 0; // высота одного символа

	/**
	 * Конструктор
	 */
	public AsciiDraw() {
		font = ResourceManager.getSpriteSheet(Constants.ASCII_FONT_KEY);
		width = font.getWidth() / font.getHorizontalCount();
		height = font.getHeight() / font.getVerticalCount();
	}

	/**
	 * Ширина символа в пикселах
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Высота символа в пикселах
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Печать символа (перегрузка)
	 */
	public void draw(String str, int x, int y, Color fg) {
		draw(str, x, y, fg, null, null);
	}

	/**
	 * Печать символа
	 */
	public void draw(String str, int x, int y, Color fg, Color bg, Graphics g) {
		int len = str.length();
		if (len == 0)
			return;
		// рисуем фон
		if (bg != null) {
			g.setColor(bg);
			g.fillRect(x * width, y * height, width * len, height);
		}
		// рисуем символы
		Point p;
		for (int i = 0; i < len; i++) {
			p = codeConvert((int) str.charAt(i));
			letter = font.getSubImage(p.x, p.y);
			letter.draw((x + i) * width, y * height, fg);
		}
	}

	/**
	 * Вывод символа в произвольной точке (перегрузка)
	 */
	public void drawFree(String str, int x, int y, Color fg) {
		drawFree(str, x, y, fg, null, null);
	}

	/**
	 * Вывод символа в произвольной точке
	 */
	public void drawFree(String str, int x, int y, Color fg, Color bg,
			Graphics g) {
		int len = str.length();
		if (len == 0)
			return;
		// рисуем фон
		if (bg != null) {
			g.setColor(bg);
			g.fillRect(x, y, width * len, height);
		}
		// рисуем символы
		Point p;
		for (int i = 0; i < len; i++) {
			p = codeConvert((int) str.charAt(i));
			letter = font.getSubImage(p.x, p.y);
			letter.draw(x + (i * width), y, fg);
		}
	}

	/**
	 * Перевод кода символа в линейный индекс ASCII-битмапы
	 * 
	 * @param code
	 * @return
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
