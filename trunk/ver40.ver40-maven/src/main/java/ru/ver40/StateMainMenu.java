package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.system.StateManager;
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Главное меню игры.
 * 
 */
public class StateMainMenu extends UserGameState {

	/**
	 * Конструктор.
	 */
	public StateMainMenu(StateManager manager) {
		super(manager);
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
		Log.debug("StateMainMenu.onInit()");
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_SPACE || key == Input.KEY_ENTER) {
			m_manager.enter(Constants.STATE_GAMEPLAY);
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// Крутая заставка.
		AsciiDraw.getInstance().draw(
				Constants.GAME_NAME,
				Constants.ASCII_SCREEN_WIDTH / 2 - Constants.GAME_NAME.length()
						/ 2, 12, Color.green);
		AsciiDraw.getInstance().draw("press enter",
				Constants.ASCII_SCREEN_WIDTH / 2 - 5, 22, Color.white);
	}
}
