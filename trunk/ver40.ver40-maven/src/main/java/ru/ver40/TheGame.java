package ru.ver40;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.system.StateManager;
import ru.ver40.system.SystemGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.MyLogSystem;
import ru.ver40.system.util.ResourceManager;
import ru.ver40.system.util.UnicodeDraw;
import ru.ver40.util.Constants;

/**
 * Класс приложения игры.
 * 
 */
public class TheGame extends StateBasedGame {

	private static StateManager m_stateManager = null;

	/**
	 * Вернуть менеджер стейтов.
	 */
	public static StateManager getStateManager() {
		return m_stateManager;
	}

	/**
	 * Конструктор
	 */
	public TheGame() {		
		super(Constants.GAME_NAME);
		m_stateManager = new StateManager(this);

		SystemGameState state;
		state = new SystemGameState(Constants.STATE_MAINMENU, m_stateManager);
		m_stateManager.add(state);
		new StateMainMenu();

		state = new SystemGameState(Constants.STATE_GAMEPLAY, m_stateManager);
		m_stateManager.add(state);

		state = new SystemGameState(Constants.STATE_DLG_ITEMS, m_stateManager);
		m_stateManager.add(state);

		state = new SystemGameState(Constants.STATE_FREELOOK, m_stateManager);
		m_stateManager.add(state);
	}

	/*
	 * Инициализация приложения.
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// Загрузка ресурсов.
		//
		try {
			ResourceManager.loadResources(Constants.RESOURCE_FILE);
		} catch (IOException e) {
			Log.error("failed to load resource file '"
					+ Constants.RESOURCE_FILE + "': " + e.getMessage());
			throw new SlickException("Resource loading failed!");
		}
		// Включение повтора нажатых клавиш.
		//
		Input input = container.getInput();
		input.enableKeyRepeat();

		// Создаем глобальные объекты сразу.
		//
		AsciiDraw.getInstance();
		UnicodeDraw.getInstance();
	}

	/**
	 * Точка входа в приложение.
	 */
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			// Инициализация отладочного лога.
			//
			DebugLog.create(0, 0, Constants.ASCII_SCREEN_WIDTH,
					Constants.DEBUG_LOG_HEIGHT);
			Log.setLogSystem(MyLogSystem.getInstance());

			// Запуск игры.
			//
			app = new AppGameContainer(new TheGame());
			app.setDisplayMode(Constants.DISPLAY_RESOLUTION_X,
					Constants.DISPLAY_RESOLUTION_Y,
					Constants.DISPLAY_FULLSCREEN);
			app.start();			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
