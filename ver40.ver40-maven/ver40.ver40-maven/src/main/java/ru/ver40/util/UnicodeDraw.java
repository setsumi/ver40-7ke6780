package ru.ver40.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


/**
 * Вывод на экран Unicode-строк.
 * 
 * Мыло мыльное мыльцо, перемыли мыльнецо.
 * 
 */
public class UnicodeDraw {
	/**
	 * Единственный экземпляр класса.
	 */
	private static UnicodeDraw instance = null;

	/**
	 * Объект юникодного шрифта слика.
	 */
	private UnicodeFont font = null;

	/**
	 * Вернуть единственный экземпляр класса.
	 * 
	 * @return
	 */
	public static UnicodeDraw getInstance() {
		if (instance == null) {
			try {
				instance = new UnicodeDraw();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * Конструктор.
	 */
	@SuppressWarnings("unchecked")
	private UnicodeDraw() throws SlickException {
		font = ResourceManager.getUnicodeFont(Constants.UNICODE_FONT_KEY);
		font.addAsciiGlyphs();
		font.addGlyphs(1025, 1025); // Ё
		font.addGlyphs(1040, 1105); // А..Яа..яё
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		font.loadGlyphs();
	}

	public void draw(String str, int x, int y, Color col) {
		font.drawString(x, y, str, col);
	}
}
