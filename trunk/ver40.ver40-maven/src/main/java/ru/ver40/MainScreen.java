package ru.ver40;

import ru.ver40.map.Viewport;
import ru.ver40.util.Constants;
import ru.ver40.util.GameLog;

public class MainScreen {
	Viewport m_viewport = null;

	/**
	 * Конструктор.
	 */
	public MainScreen() {
		GameLog.create(1, 30, Constants.ASCII_SCREEN_WIDTH - 2, 6,
				Constants.GAME_LOG_BACKCOLOR);

	}

}
