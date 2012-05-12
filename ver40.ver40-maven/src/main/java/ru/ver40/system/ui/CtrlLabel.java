package ru.ver40.system.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ver40.system.util.AsciiDraw;

/**
 * Строчка текста на окне.
 * 
 */
public class CtrlLabel extends BaseWindow {

	String m_text = null;
	Color m_backColor = null;

	/**
	 * Конструктор (без цвета фона).
	 */
	public CtrlLabel(BaseWindow parent, int x, int y, Color fc) {
		super(parent, x, y, 0, 0, fc);
		//
		m_backColor = null;
		setText(" ");
	}

	/**
	 * Конструктор.
	 */
	public CtrlLabel(BaseWindow parent, int x, int y, Color fc, Color bc) {
		super(parent, x, y, 0, 0, fc);
		//
		m_backColor = bc;
		setText(" ");
	}

	/**
	 * Вернуть цвет фона. (null - нет цвета фона)
	 */
	public Color getBackColor() {
		return m_backColor;
	}

	/**
	 * Установка цвета фона. (можно присвоить null, чтобы убрать цвет фона)
	 */
	public void setBackColor(Color c) {
		m_backColor = c;
	}

	/**
	 * Установка текста для отображения.
	 */
	public void setText(String text) {
		m_text = text;
		m_width = m_text.length();
		m_height = 1;
	}

	/**
	 * Вернуть текущий текст.
	 */
	public String getText() {
		return m_text;
	}

	@Override
	public void draw(Graphics g) {
		if (m_backColor != null) {
			AsciiDraw.getInstance().draw(m_text, m_parent.getPosX() + m_posX,
					m_parent.getPosY() + m_posY, m_color, m_backColor, g);
		} else {
			AsciiDraw.getInstance().draw(m_text, m_parent.getPosX() + m_posX,
					m_parent.getPosY() + m_posY, m_color);
		}
	}

}
