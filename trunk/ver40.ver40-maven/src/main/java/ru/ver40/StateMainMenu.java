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
	public void enter(GameContainer gc, StateBasedGame game) {
		Log.debug("StateMainMenu.enter()");
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame game) {
		Log.debug("StateMainMenu.leave()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		Log.debug("StateMainMenu.onInit()");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		// Запускаем игру по пробелу или вводу.
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_SPACE)
				|| input.isKeyPressed(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			m_manager.enter(Constants.STATE_GAMEPLAY);
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// Крутая заставка.
		AsciiDraw.getInstance().draw(
				Constants.GAME_NAME,
				Constants.ASCII_SCREEN_WIDTH / 2 - Constants.GAME_NAME.length()
						/ 2, 10, Color.green);
		AsciiDraw.getInstance().draw("press start",
				Constants.ASCII_SCREEN_WIDTH / 2 - 5, 20, Color.white);
	}

}
