package ru.ver40.system.ui;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ver40.system.util.AsciiDraw;

/**
 * Многострочный текст на окне.
 * 
 */
public class CtrlLabel extends BaseWindow {

	private String m_text;
	private String[] m_lines;
	private Color m_backColor;

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

		StringTokenizer st = new StringTokenizer(m_text, "\n");
		int maxWidth = 0;
		Vector<String> v = new Vector<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int width = token.length();
			maxWidth = (maxWidth < width) ? width : maxWidth;
			v.addElement(token);
		}
		int lines = v.size();
		if (lines < 1) {
			m_lines = null;
			lines = 1;
		} else {
			m_lines = new String[lines];
			int i = 0;
			for (Enumeration<String> e = v.elements(); e.hasMoreElements(); i++) {
				m_lines[i] = (String) e.nextElement();
			}
		}
		m_width = maxWidth;
		m_height = lines;
	}

	/**
	 * Вернуть текущий текст.
	 */
	public String getText() {
		return m_text;
	}

	@Override
	public void draw(Graphics g) {
		if (m_lines != null) {
			for (int i = 0; i < m_lines.length; i++) {
				if (m_backColor != null) {
					AsciiDraw.getInstance().draw(m_lines[i],
							m_parent.getPosX() + m_posX,
							m_parent.getPosY() + m_posY + i, m_color,
							m_backColor, g);
				} else {
					AsciiDraw.getInstance().draw(m_lines[i],
							m_parent.getPosX() + m_posX,
							m_parent.getPosY() + m_posY + i, m_color);
				}
			}
		}
	}

}
