package ru.ver40.system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.TheGame;

/**
 * Обычный игровой стейт.
 */
public abstract class UserGameState extends UserGameStateBase {

	SystemGameState m_owner = null;

	/**
	 * Конструктор.
	 */
	public UserGameState(SystemGameState owner) {
		m_owner = owner;
	}

	/**
	 * Перейти в другой стейт.
	 */
	public final void enterState(int id) {
		((SystemGameState) TheGame.getInstance().getState(id)).setCaller(null);
		TheGame.getInstance().enterState(id);
	}

	/**
	 * Модально открыть другой стейт.
	 */
	public final void enterStateModal(int id) {
		((SystemGameState) TheGame.getInstance().getState(id))
				.setCaller(m_owner);
		TheGame.getInstance().enterState(id);
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// Если этот стейт модальный, то сначала рисуем вызвавший стейт.
		SystemGameState caller = m_owner.getCaller();
		if (caller != null) {
			// Рисуем только клиент вызвавшего стейта, т.к. системная прорисовка
			// уже есть наша.
			caller.getClient().onRender(gc, game, g);
		}
	}

}
