package ru.ver40.util;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import ru.ver40.engine.ResourceManager;

/**
 * Отрисовка ASCII-строк
 */
public class AsciiDraw {

	private SpriteSheet font;
	private int width; // ширина одного символа
	private int height; // высота одного символа

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
		int dx, dy, sx, sy;
		font.startUse();
		for (int i = 0; i < len; i++) {
			p = codeConvert((int) str.charAt(i));
			dx = (x + i) * width;
			dy = y * height;
			sx = p.x * width;
			sy = p.y * height;
			font.drawEmbedded(dx, dy, dx + width - 1, dy + height - 1, sx, sy,
					sx + width - 1, sy + height - 1, fg);
		}
		font.endUse();
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
		int dx, dy, sx, sy;
		font.startUse();
		for (int i = 0; i < len; i++) {
			p = codeConvert((int) str.charAt(i));
			dx = x + (i * width);
			dy = y;
			sx = p.x * width;
			sy = p.y * height;
			font.drawEmbedded(dx, dy, dx + width - 1, dy + height - 1, sx, sy,
					sx + width - 1, sy + height - 1, fg);
		}
		font.endUse();
	}

	/**
	 * Перевод кода символа в координаты на матрице SpriteSheet-а шрифта
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
