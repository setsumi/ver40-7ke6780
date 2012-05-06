package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Базовый класс изолированного от системы игрового стейта.
 */
public abstract class UserGameStateBase {
	/**
	 * Вход в стейт.
	 */
	public void enter(GameContainer gc, StateBasedGame game) {
	}

	/**
	 * Выход из стейта.
	 */
	public void leave(GameContainer container, StateBasedGame game) {
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
	 * 
	 * В наследниках необходимо вызывать сначала super, иначе фон модальных
	 * стейтов не прорисуется.
	 */
	public abstract void onRender(GameContainer gc, StateBasedGame game,
			Graphics g);

}
