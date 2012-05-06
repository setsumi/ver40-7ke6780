package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.system.SystemGameState;
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
	public StateGameplay(SystemGameState owner) {
		super(owner);
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.enter()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.onInit()");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_I)) {
			// TheGame.getInstance().getInventoryDialog().show(this);
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		super.onRender(gc, game, g);

		AsciiDraw.getInstance().draw("Gameplay state.", 5, 5, Color.blue);
	}

}
