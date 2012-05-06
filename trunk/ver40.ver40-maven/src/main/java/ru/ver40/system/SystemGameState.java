package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.system.util.DebugLog;
import ru.ver40.util.Constants;

/**
 * Системный игровой стейт слика, управляющий своим пользовательским стейтом.
 * 
 */
public class SystemGameState extends BasicGameState {

	int m_stateID = -1;
	
	UserGameState m_client = null;
	SystemGameState m_caller = null;

	/**
	 * Конструктор.
	 * 
	 * Сразу после создания объекта необходимо присвоить клиента.
	 */
	public SystemGameState(int stateID) {
		m_stateID = stateID;
	}

	public void setClient(UserGameState client) {
		m_client = client;
	}

	public UserGameState getClient() {
		return m_client;
	}

	public void setCaller(SystemGameState caller) {
		m_caller = caller;
	}

	public SystemGameState getCaller() {
		return m_caller;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.enter(gc, game);

		// Нотификация клиенту
		m_client.enter(gc, game);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		// Инициализация клиента.
		m_client.onInit(gc, game);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		// Сначала отрисовка клиента.
		m_client.onRender(gc, game, g);

		// Отрисовка системы в конце, чтобы рисовать поверх пользователя.
		DebugLog.getInstance().draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		// Сначала обработка системы.
		Input input = gc.getInput();
		if (input.isKeyPressed(Constants.DEGUG_LOG_SHOWKEY)) {
			// Отладочный лог.
			if (DebugLog.showLog)
				DebugLog.getInstance().resetNew();
			DebugLog.showLog = !DebugLog.showLog;

			// Сброс нажатых клавиш.
			input.clearKeyPressedRecord();
		} else {
			// Обработка клиента.
			m_client.onUpdate(gc, game, delta);
		}
	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
