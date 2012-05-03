package ru.ver40.util;

import org.newdawn.slick.Color;

public class Constants {
	/**
	 * Ключ ресурса SpriteSheet-а шрифта для AsciiDraw
	 */
	public static final String ASCII_FONT_KEY = "asciifont";
	/**
	 * Ключ ресурса UnicodeFont для UnicodeDraw
	 */
	public static final String UNICODE_FONT_KEY = "unicodefont";
	/**
	 * Антиалиасинг шрифта в UnicodeDraw
	 */
	public static final boolean UNICODE_FONT_ANTIALIAS = true;
	/**
	 * Ширина экрана в ASCII-тайлах
	 */
	public static final int ASCII_SCREEN_WIDTH = 80;
	/**
	 * Размер стороны чанка (длины стороны квадрата)
	 */
	public static final int MAP_CHUNK_SIZE = 256;
	/**
	 * Размер чанка (как линейный индекс)
	 */
	public static final int MAP_CHUNK_LENGTH = MAP_CHUNK_SIZE * MAP_CHUNK_SIZE;
	/**
	 * Размер кэша чанков в карте
	 */
	public static final int MAP_CHUNK_CACHE_SIZE = 25;
	/**
	 * Центр доступного диапазона координат
	 */
	public static final int MAP_CENTER = Integer.MAX_VALUE / 2;
	/**
	 * Максимальный размер карты
	 */
	public static final int MAP_MAX_SIZE = Integer.MAX_VALUE
			- (Integer.MAX_VALUE % MAP_CHUNK_SIZE);
	/**
	 * Цвет шрифта отладочного лога.
	 */
	public static final Color DEBUG_LOG_COLOR = new Color(1.0f, 1.0f, 1.0f);
	/**
	 * Цвет фона отладочного лога.
	 */
	public static final Color DEBUG_LOG_BACKCOLOR = new Color(0.0f, 0.0f, 1.0f,
			0.8f);
	/**
	 * Коэффициент затемнение старых записей лога.
	 */
	public static final float LOG_FADE_FACTOR = 0.25f;
	/**
	 * Цвет шрифта игрового лога (важные записи)
	 */
	public static final Color GAME_LOG_IMPORTANTCOLOR = new Color(1.0f, 1.0f,
			0.0f);
	/**
	 * Цвет шрифта игрового лога (обычные записи)
	 */
	public static final Color GAME_LOG_REGULARCOLOR = new Color(1.0f, 1.0f,
			1.0f);
	/**
	 * Максимальное количество записей в логах.
	 */
	public static final int LOG_MAX_SIZE = 2048;
}
