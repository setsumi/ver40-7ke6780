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

/**
 * Главный игровой процесс игры.
 * 
 */
public class StateGameplay extends UserGameState {

	/**
	 * Конструктор.
	 */
	public StateGameplay(StateManager manager) {
		super(manager);
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.enter()");
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.leave()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.onInit()");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		this.m_manager.enterModal(2);
		if (input.isKeyPressed(Input.KEY_M)) {
			this.m_manager.enterModal(2);
		} else if (input.isKeyPressed(Input.KEY_Q)
				&& (input.isKeyDown(Input.KEY_LCONTROL) || input
						.isKeyDown(Input.KEY_RCONTROL))) {
		}
		return;
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		AsciiDraw.getInstance().draw("Gameplay state.", 5, 5, Color.blue);
	}

}
