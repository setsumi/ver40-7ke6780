package ru.ver40.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class Constants {
	/**
	 * Разрешение игры по горизонтали в пикселах.
	 */
	public static final int DISPLAY_RESOLUTION_X = 640;
	/**
	 * Разрешение игры по вертикали в пикселах.
	 */
	public static final int DISPLAY_RESOLUTION_Y = 480;
	/**
	 * Флаг полноэкранного режима игры.
	 */
	public static final boolean DISPLAY_FULLSCREEN = false;
	/**
	 * Название игры для конструктора корневого класса.
	 */
	public static final String GAME_NAME = "ver.40-7KE.6780";
	/**
	 * Файл с описанием ресурсов для загрузки.
	 */
	public static final String RESOURCE_FILE = "data/resources.xml";
	/**
	 * Ключ ресурса SpriteSheet-а шрифта для AsciiDraw.
	 */
	public static final String ASCII_FONT_KEY = "asciifont";
	/**
	 * Ключ ресурса UnicodeFont для UnicodeDraw.
	 */
	public static final String UNICODE_FONT_KEY = "unicodefont";
	/**
	 * Антиалиасинг шрифта в UnicodeDraw.
	 */
	public static final boolean UNICODE_FONT_ANTIALIAS = true;
	/**
	 * Ширина экрана в ASCII-тайлах.
	 */
	public static final int ASCII_SCREEN_WIDTH = 80;
	/**
	 * Размер стороны чанка (длины стороны квадрата).
	 */
	public static final int MAP_CHUNK_SIZE = 256;
	/**
	 * Размер чанка (как линейный индекс).
	 */
	public static final int MAP_CHUNK_LENGTH = MAP_CHUNK_SIZE * MAP_CHUNK_SIZE;
	/**
	 * Размер кэша чанков (в классе карты).
	 */
	public static final int MAP_CHUNK_CACHE_SIZE = 9;
	/**
	 * Размер карты в клетках.
	 */
	public static final int MAP_MAX_SIZE = (int) Math.sqrt(Integer.MAX_VALUE)
			- (int) (Math.sqrt(Integer.MAX_VALUE) % MAP_CHUNK_SIZE);
	/**
	 * Центр карты (в клетках).
	 */
	public static final int MAP_CENTER = MAP_MAX_SIZE / 2;
	/**
	 * Размер карты в чанках.
	 */
	public static final int MAP_MAX_SIZE_CHUNKS = MAP_MAX_SIZE / MAP_CHUNK_SIZE;
	/**
	 * Высота отладочного лога в строках.
	 */
	public static final int DEBUG_LOG_HEIGHT = 20;
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
	 * Файл в который дампить отладочный лог.
	 */
	public static final String DEBUG_LOG_FILE = "debug-log.txt";
	/**
	 * Клавиша по которой показывать и скрывать отладочный лог в игре (тильда).
	 */
	public static final int DEGUG_LOG_SHOWKEY = Input.KEY_GRAVE;
	/**
	 * Файл в который дампить игровой лог.
	 */
	public static final String GAME_LOG_FILE = "game-log.txt";
	/**
	 * Коэффициент затемнение старых записей логов.
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
	 * Цвет фона игрового лога
	 */
	public static final Color GAME_LOG_BACKCOLOR = new Color(0.0f, 0.0f, 0.0f,
			0.5f);
	/**
	 * Максимальное количество записей внутриигровых вьюверов логов.
	 */
	public static final int LOG_MAX_SIZE = 1024;

	// Стейты.
	/**
	 * Стейт главного меню.
	 */
	public static final int STATE_MAINMENU = 10;
	/**
	 * Стейт игры.
	 */
	public static final int STATE_GAMEPLAY = 20;
	/**
	 * Стейт диалога предметов.
	 */
	public static final int STATE_DLG_ITEMS = 30;
	/**
	 * Стейт свободного просмотря карты (без привязки к игроку).
	 */
	public static final int STATE_FREELOOK = 40;
	/**
	 * Стейт Просмотра миникарты.
	 */
	public static final int STATE_MINIMAP = 50;

}

