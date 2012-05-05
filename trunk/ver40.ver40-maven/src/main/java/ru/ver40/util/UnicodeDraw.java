package ru.ver40.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


/**
 * Вывод на экран Unicode-строк.
 * 
 * Слишком мыльно, чтобы использовать. Синглтон.
 * 
 */
public class UnicodeDraw {
	/**
	 * Единственный экземпляр класса.
	 */
	private static UnicodeDraw m_instance = null;

	/**
	 * Объект юникодного шрифта слика.
	 */
	private UnicodeFont m_font = null;

	/**
	 * Вернуть единственный экземпляр класса.
	 * 
	 * @return
	 */
	public static UnicodeDraw getInstance() {
		if (m_instance == null) {
			try {
				m_instance = new UnicodeDraw();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return m_instance;
	}

	/**
	 * Конструктор.
	 */
	@SuppressWarnings("unchecked")
	private UnicodeDraw() throws SlickException {
		m_font = ResourceManager.getUnicodeFont(Constants.UNICODE_FONT_KEY);
		m_font.addAsciiGlyphs();
		m_font.addGlyphs(1025, 1025); // Ё
		m_font.addGlyphs(1040, 1105); // А..Яа..яё
		m_font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		m_font.loadGlyphs();
	}

	public void draw(String str, int x, int y, Color col) {
		m_font.drawString(x, y, str, col);
	}
}
