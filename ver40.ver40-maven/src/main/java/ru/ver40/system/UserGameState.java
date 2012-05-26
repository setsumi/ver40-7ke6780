package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.TheGame;

/**
 * Базовый класс изолированного от системы игрового стейта. Обязательно
 * выполнять attachToSystemState() и все суперы.
 */
public abstract class UserGameState {
	/**
	 * ID системного стейта-владельца. Обязательно присвоить в конструкторах
	 * наследников вызовом attachToSystemState().
	 */
	private int m_id = -1;
	private final StateManager m_manager; // Менеджер стейтов приложения.
	private boolean m_isInitialized = false; // Флаг для инициализации через
												// onUpdate() и onRender()

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
	 * Активировать себя.
	 */
	public void show() {
		m_manager.enter(m_id);
	}

	/**
	 * Активировать себя модально (повесить поверх предыдущих стейтов).
	 */
	public void showModal() {
		m_manager.enterModal(m_id);
	}

	/**
	 * Выйти из себя в предыдущий стейт.
	 */
	public void exitModal() {
		m_manager.exitModal();
	}

	/**
	 * Событие входа в стейт. Обязательно вызывать этот супер в наследниках.
	 */
	public void onEnter(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Событие выхода из стейта. Обязательно вызывать этот супер в наследниках.
	 */
	public void onLeave(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Инициализация стейта. Обязательно вызывать этот супер в наследниках.
	 */
	public void onInit(GameContainer gc, StateBasedGame game) {
		m_isInitialized = true;
	}

	/**
	 * Обновление стейта. Крутится в цикле. Обязательно вызывать этот супер в наследниках.
	 */
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		if (!m_isInitialized) {
			onInit(gc, game);
		}
	}

	/**
	 * Рендер стейта. Крутится в цикле. Обязательно вызывать этот супер в наследниках.
	 */
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		if (!m_isInitialized) {
			onInit(gc, game);
		}
	}

	/**
	 * Прием нажатия клавиш.
	 */
	public abstract void onKeyPressed(int key, char c);

	/**
	 * Прием отпускания клавиш.
	 */
	public abstract void onKeyReleased(int key, char c);
}
