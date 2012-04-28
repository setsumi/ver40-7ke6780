package ru.ver40.util;

public class Constants {
	/**
	 * Ключ ресурса SpriteSheet-а шрифта для AsciiDraw
	 */
	public static final String ASCII_FONT_KEY = "asciifont";
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
	public static final int MAP_MAX_SIZE = Integer.MAX_VALUE;
}
