package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.TheGame;

/**
 * Базовый класс изолированного от системы игрового стейта.
 */
public abstract class UserGameState {
	/**
	 * ID системного стейта-владельца. Обязательно присвоить в конструкторах
	 * наследников вызовом attachToSystemState().
	 */
	private int m_id = -1;
	protected final StateManager m_manager; // Менеджер стейтов приложения.
	private boolean m_isInitialised = false;

	/**
	 * Конструктор.
	 */
	public UserGameState() {
		m_manager = TheGame.getStateManager();
	}

	/**
	 * Присоединиться к родному системному стейту. Обязательно выполнить в
	 * конструкторах наследников.
	 */
	protected void attachToSystemState(int id) {
		m_id = id;
		m_manager.getSystemState(m_id).setClient(this);
	}

	/**
	 * Активировать свой системный стейт (перейти на него).
	 */
	public void show() {

		m_manager.enter(m_id);
	}

	/**
	 * Активировать свой системный стейт модально (повесить поверх предыдущих
	 * стейтов).
	 */
	public void showModal() {
		m_manager.enterModal(m_id);
	}

	/**
	 * Выйти из своего системного модального стейта в предыдущий.
	 */
	public void exit() {
		m_manager.exitModal();
	}

	/**
	 * Событие входа в стейт.
	 */
	public void onEnter(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Событие выхода из стейта.
	 */
	public void onLeave(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Инициализация стейта. Обязательно вызывать этот супер в наследниках.
	 */
	public void onInit(GameContainer gc, StateBasedGame game) {
		m_isInitialised = true;
	}

	/**
	 * Обновление стейта. Крутится в цикле. Обязательно вызывать этот супер в
	 * наследниках.
	 */
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		if (!m_isInitialised) {
			m_isInitialised = true;
			onInit(gc, game);
		}
	}

	/**
	 * Рендер стейта. Крутится в цикле.
	 */
	public abstract void onRender(GameContainer gc, StateBasedGame game,
			Graphics g);

	/**
	 * Прием нажатия клавиш. (пустая)
	 */
	public void onKeyPressed(int key, char c) {
		// Пустая.
	}

	/**
	 * Прием отпускания клавиш. (пустая)
	 */
	public void onKeyReleased(int key, char c) {
		// Пустая.
	}
}
