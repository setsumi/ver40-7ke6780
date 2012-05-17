package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import ru.ver40.system.util.DebugLog;
import ru.ver40.util.Constants;

/**
 * Системный игровой стейт слика. Управляет своим пользовательским стейтом.
 * 
 */
public class SystemGameState extends BasicGameState {

	int m_stateID = -1;
	
	UserGameState m_client; // Пользовательский стейт.
	StateManager m_manager; // Менеджер всех стейтов.
//	HashMap<Integer, Character> m_keysPressed, m_keysReleased; // Список нажатых
//																// и отпущенных
//																// клавиш для
//																// синхронной с
//																// игровым лупом
//																// обработки.

	// Клавиши зарезервированные за системой.
	private static final int[] m_systemKeys = { Constants.DEGUG_LOG_SHOWKEY };

	/**
	 * Конструктор.
	 */
	public SystemGameState(int stateID, StateManager manager) {
		m_stateID = stateID;
		m_manager = manager;
//		m_keysPressed = new HashMap<Integer, Character>();
//		m_keysReleased = new HashMap<Integer, Character>();
	}

	/**
	 * Присвоить клиента (стейт пользователя, привязанный к этому системному).
	 */
	public void setClient(UserGameState client) {
		m_client = client;
	}

	/**
	 * Получить клиента (стейт пользователя, привязанный к этому системному).
	 */
	public UserGameState getClient() {
		return m_client;
	}

	@Override
	public int getID() {
		return m_stateID;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.enter(gc, game);

		// Нотификация клиенту
		m_client.onEnter(gc, game);
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.leave(gc, game);

		// Нотификация клиенту
		m_client.onLeave(gc, game);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		// Инициализация клиента.
		if (m_client != null)
			m_client.onInit(gc, game);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		// Отрисовка клиентов идет через менеджер стейтов, а не напрямую. Это
		// нужно для прорисовки цепочки модальных стейтов.
		m_manager.render(gc, game, g);

		// Отрисовка системы в конце, чтобы рисовать поверх пользователя.
		DebugLog.getInstance().draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
//		// Посылаем нажатые клавиши клиенту.
//		for (Map.Entry<Integer, Character> entry : m_keysPressed.entrySet()) {
//			m_client.onKeyPressed(entry.getKey(), entry.getValue());
//		}
//		m_keysPressed.clear();
//		// Посылаем отпущенные клавиши клиенту.
//		for (Map.Entry<Integer, Character> entry : m_keysReleased.entrySet()) {
//			m_client.onKeyReleased(entry.getKey(), entry.getValue());
//		}
//		m_keysReleased.clear();
		// И наконец обновление клиенту.
		m_client.onUpdate(gc, game, delta);
	}

	@Override
	public void keyPressed(int key, char c) {
		boolean isSyskey = false;
		for (int x : m_systemKeys) {
			if (x == key) {
				isSyskey = true;
				break;
			}
		}
		if (key == Constants.DEGUG_LOG_SHOWKEY) {
			if (DebugLog.showLog)
				DebugLog.getInstance().resetNew();
			DebugLog.showLog = !DebugLog.showLog;
		}
		if (!isSyskey) {
			m_client.onKeyPressed(key, c);
//			m_keysPressed.put(key, c);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		boolean isSyskey = false;
		for (int x : m_systemKeys) {
			if (x == key) {
				isSyskey = true;
				break;
			}
		}
		if (!isSyskey) {
			m_client.onKeyReleased(key, c);
//			m_keysReleased.put(key, c);
		}
	}

}
