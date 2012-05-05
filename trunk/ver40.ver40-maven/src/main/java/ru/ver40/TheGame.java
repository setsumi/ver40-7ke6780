package ru.ver40;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.util.Constants;
import ru.ver40.util.DebugLog;
import ru.ver40.util.MyLogSystem;

/**
 * @author Setsumi
 *
 */
public class TheGame extends StateBasedGame {
	private static TheGame m_game = null;

	public static TheGame getInstance() {
		return m_game;
	}

	private MainMenu m_mainMenu = null;
	private Gameplay m_gameplay = null;
	private InventoryDialog m_inventoryDialog = null;

	public MainMenu getMainMenu() {
		return m_mainMenu;
	}

	public Gameplay getGameplay() {
		return m_gameplay;
	}

	public InventoryDialog getInventoryDialog() {
		return m_inventoryDialog;
	}

	/**
	 * Конструктор
	 */
	public TheGame() {
		super(Constants.GAME_NAME);

		m_game = this;

		m_mainMenu = new MainMenu(Constants.MAINMENU_STATE);
		this.addState(m_mainMenu.getState());
		m_gameplay = new Gameplay(Constants.GAMEPLAY_STATE);
		this.addState(m_gameplay.getState());
		m_inventoryDialog = new InventoryDialog(
				Constants.INVENTORY_DIALOG_STATE);
		this.addState(m_inventoryDialog.getState());

		// this.addState(new GameplayState(Constants.GAMEPLAY_STATE));
		m_mainMenu.show();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// this.getState(Constants.MAINMENU_STATE).init(container, this);
		// this.getState(Constants.GAMEPLAY_STATE).init(container, this);
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
