package ru.ver40.system.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ver40.system.util.AsciiDraw;

/**
 * Стандартное окно с заголовком и нижней строкой.
 * 
 */
public class MockWindow extends BaseWindow {

	protected Color m_bgColor;
	protected String m_title, m_footer;
	protected boolean m_border;

	/**
	 * Конструктор.
	 */
	public MockWindow(BaseWindow owner, int x, int y, int w, int h, Color fc,
			Color bc, String title, String footer, boolean border) {
		super(owner, x, y, w, h, fc);
		//
		m_bgColor = bc;
		m_title = title;
		m_footer = footer;
		m_border = border;

	}

	/**
	 * Присвоить текст заголовка окна.
	 */
	public void setTitle(String title) {
		m_title = title;
	}

	/**
	 * Присвоить текст нижней строки.
	 */
	public void setFooter(String footer) {
		m_footer = footer;
	}

	/**
	 * Возвращает горизонтальную позицию отцентрованной относительно окна
	 * строки.
	 */
	public int centerString(String str) {
		int diff = m_width - str.length();
		int ret = m_posX + diff / 2;
		if (ret < 0)
			ret = 0;
		return ret;
	}

	@Override
	public void onKeyPressed(int key, char c) {
		super.onKeyPressed(key, c);
	}

	@Override
	public void onKeyReleased(int key, char c) {
		super.onKeyReleased(key, c);
	}

	@Override
	public void draw(Graphics g) {
		// Рамочка по периметру.
		if (m_border) {
		}
		// Заливка тела окна.
		AsciiDraw.getInstance().drawRect(m_posX, m_posY, m_width, m_height,
				m_bgColor);
		// Заголовок.
		AsciiDraw.getInstance().draw(m_title, centerString(m_title), m_posY,
				m_color);
		// Нижняя строка.
		AsciiDraw.getInstance().draw(m_footer, centerString(m_footer),
				m_posY + m_height - 1, m_color);

		// Прорисовка дочерних контролсов.
		super.draw(g);
	}

}
