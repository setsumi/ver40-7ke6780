package ru.ver40.system;

import java.util.HashMap;
import java.util.LinkedList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Управление игровыми стейтами.
 * 
 */
public class StateManager {

	private StateBasedGame m_game;
	private HashMap<Integer, SystemGameState> m_states; // все стейты
	private LinkedList<SystemGameState> m_modals; // стек модальных стейтов

	/**
	 * Конструктор.
	 */
	public StateManager(StateBasedGame game) {
		m_game = game;
		m_states = new HashMap<Integer, SystemGameState>();
		m_modals = new LinkedList<SystemGameState>();
	}

	/**
	 * Вернуть системный стейт по его ID.
	 */
	public SystemGameState getSystemState(int id) {
		SystemGameState state = m_states.get(id);
		if (state == null) {
			throw new RuntimeException("State with id=" + id + " not found.");
		}
		return state;
	}

	/**
	 * Добавить стейт.
	 */
	public void add(SystemGameState state) {
		m_states.put(state.getID(), state);
		m_game.addState(state);
		// При первом добавлении стейта, добавляем его руками в пустой m_modals.
		// Иначе не работает render().
		// Вызывать enter() нельзя, т.к. будет повторный перевход в стейт.
		if (m_modals.size() == 0)
			m_modals.add(state);
	}

	/**
	 * Перейти в другой стейт.
	 */
	public void enter(int id) {
		m_modals.clear();
		m_modals.add(m_states.get(id));
		m_game.enterState(id);
	}

	/**
	 * Открыть другой стейт модально.
	 */
	public void enterModal(int id) {
		m_modals.add(m_states.get(id));
		m_game.enterState(id);
	}

	/**
	 * Выйти из модального стейта в предыдущий.
	 */
	public void exitModal() {
		if (m_modals.size() < 2) {
			throw new RuntimeException("Nowhere to exit.");
		}
		m_modals.removeLast();
		m_game.enterState(m_modals.getLast().getID());
	}

	/**
	 * Отрисовка стейтов. Нужно для прорисовки цепочки модальных стейтов.
	 */
	public void render(GameContainer gc, StateBasedGame game, Graphics g) {
		for (SystemGameState state : m_modals) {
			state.getClient().onRender(gc, game, g);
		}
	}
}
