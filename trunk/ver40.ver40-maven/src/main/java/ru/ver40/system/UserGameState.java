package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Базовый класс изолированного от системы игрового стейта.
 */
public abstract class UserGameState {

	protected final StateManager m_manager; // Менеджер стейтов приложения.

	/**
	 * Конструктор.
	 */
	public UserGameState(StateManager manager) {
		m_manager = manager;
	}

	/**
	 * Вход в стейт.
	 */
	public void onEnter(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Выход из стейта.
	 */
	public void onLeave(GameContainer gc, StateBasedGame game) {
		gc.getInput().clearKeyPressedRecord();
	}

	/**
	 * Инициализация стейта.
	 */
	public abstract void onInit(GameContainer gc, StateBasedGame game);

	/**
	 * Обновление стейта. Крутится в цикле.
	 */
	public abstract void onUpdate(GameContainer gc, StateBasedGame game,
			int delta);

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
