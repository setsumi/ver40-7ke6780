package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.system.UserGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Главное меню игры.
 * 
 */
public class StateMainMenu extends UserGameState {
	/**
	 * Для прорисовки надписи "Loading..." перед выходом отсюда.
	 * 0 - висим тут и рисуем заставку; 1 - рисуем "Loading..." и выходим
	 */
	private int exitCountdown = 1;

	/**
	 * Конструктор.
	 */
	public StateMainMenu() {
		super();
		attachToSystemState(Constants.STATE_MAINMENU);
	}

	@Override
	public void onEnter(GameContainer gc, StateBasedGame game) {
		super.onEnter(gc, game);
		Log.debug("StateMainMenu.onEnter()");
	}

	@Override
	public void onLeave(GameContainer gc, StateBasedGame game) {
		super.onLeave(gc, game);
		Log.debug("StateMainMenu.onLeave()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		Log.debug("StateMainMenu.onInit()");
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_SPACE || key == Input.KEY_ENTER) {
			exitCountdown = 1; // Начать отсчет до выхода.
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		if (exitCountdown == 0) {
			// Крутая заставка.
			AsciiDraw.getInstance()
					.draw(Constants.GAME_NAME,
							Constants.ASCII_SCREEN_WIDTH / 2
									- Constants.GAME_NAME.length() / 2, 12,
							Color.green);
			AsciiDraw.getInstance().draw("press enter",
					Constants.ASCII_SCREEN_WIDTH / 2 - 5, 22, Color.white);
		} else {
			// Надпись "Loading..."
			if (exitCountdown > 1) {
				StateGameplay gameplay = new StateGameplay();
				gameplay.show();
			}
			AsciiDraw.getInstance().draw("LOADING...", 65, 37, Color.white);
			exitCountdown++;
		}
	}
}
