package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.system.StateManager;
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.AsciiDraw;

public class StateModal1 extends UserGameState {

	public StateModal1(StateManager manager) {
		super(manager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		Input i = gc.getInput();
		if (i.isKeyPressed(Input.KEY_ESCAPE)) {
			this.m_manager.exitModal();
		} else if (i.isKeyPressed(Input.KEY_M)) {
			this.m_manager.enterModal(3);
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		AsciiDraw.getInstance().draw("Modal 1", 1, 10, Color.white);
	}

}
