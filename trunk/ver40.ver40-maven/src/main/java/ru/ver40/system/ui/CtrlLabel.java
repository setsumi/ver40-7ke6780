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

	/**
	 * Конструктор.
	 */
	public CtrlLabel(BaseWindow parent, int x, int y, Color c) {
		super(parent, x, y, 0, 0, c);
		//
		m_text = new String();
	}

	public void setText(String text) {
		m_text = text;
		m_width = m_text.length();
		m_height = 1;
	}

	@Override
	public void draw(Graphics g) {
		AsciiDraw.getInstance().draw(m_text, m_parent.getPosX() + m_posX,
				m_parent.getPosY() + m_posY, m_color);
	}

}
