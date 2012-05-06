package ru.ver40;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.system.SystemGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.MyLogSystem;
import ru.ver40.system.util.ResourceManager;
import ru.ver40.system.util.UnicodeDraw;
import ru.ver40.util.Constants;

/**
 * @author Setsumi
 *
 */
public class TheGame extends StateBasedGame {
	private static TheGame m_game = null;

	public static TheGame getInstance() {
		return m_game;
	}

	/**
	 * Конструктор
	 */
	public TheGame() {
		super(Constants.GAME_NAME);

		m_game = this;

		SystemGameState sys = new SystemGameState(Constants.STATE_MAINMENU);
		sys.setClient(new StateMainMenu(sys));
		this.addState(sys);

		sys = new SystemGameState(Constants.STATE_GAMEPLAY);
		sys.setClient(new StateGameplay(sys));
		this.addState(sys);
	}

	/*
	 * Инициализация приложения.
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// this.getState(Constants.MAINMENU_STATE).init(container, this);
		// this.getState(Constants.GAMEPLAY_STATE).init(container, this);

		// Загрузка ресурсов.
		try {
			ResourceManager.loadResources(Constants.RESOURCE_FILE);
		} catch (IOException e) {
			Log.error("failed to load resource file '"
					+ Constants.RESOURCE_FILE + "': " + e.getMessage());
			throw new SlickException("Resource loading failed!");
		}
		// Включение повтора нажатых клавиш.
		Input input = container.getInput();
		input.enableKeyRepeat();

		// Создаем глобальные объекты сразу.
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
			DebugLog.create(0, 0, Constants.ASCII_SCREEN_WIDTH,
					Constants.DEBUG_LOG_HEIGHT);
			Log.setLogSystem(MyLogSystem.getInstance());

			// Запуск игры.
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
