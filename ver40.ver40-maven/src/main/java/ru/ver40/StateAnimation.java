package ru.ver40;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.system.AAnimation;
import ru.ver40.system.UserGameState;
import ru.ver40.util.Constants;

/**
 * Проигрывание анимаций (как скриптового ролика).
 * 
 * Создается глобально и вызывается через менеджер стейтов игры.
 */
public class StateAnimation extends UserGameState {

	private LinkedList<AAnimation> m_animations; // Анимации для проигрывания.

	/**
	 * Конструктор.
	 */
	public StateAnimation() {
		super();
		attachToSystemState(Constants.STATE_ANIMATION);
		//
		m_animations = new LinkedList<AAnimation>();
	}

	/**
	 * Добавить анимацию.
	 */
	public void add(AAnimation animation) {
		m_animations.add(animation);
	}

	/**
	 * Запуск всех анимаций.
	 */
	private void start() {
		for (AAnimation a : m_animations) {
			a.start();
		}
	}

	/**
	 * Удаление всех анимаций и выход из стейта.
	 */
	private void exit() {
		m_animations.clear();
		exitModal();
	}

	@Override
	public void onEnter(GameContainer gc, StateBasedGame game) {
		super.onEnter(gc, game);
		//
		start();
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		//
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
		//
		boolean allStopped = true;
		for (AAnimation a : m_animations) {
			a.update(delta);
			if (!a.isStopped()) {
				allStopped = false;
			}
		}
		if (allStopped) {
			exit();
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		for (AAnimation a : m_animations) {
			a.draw(g);
		}
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			exit();
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
