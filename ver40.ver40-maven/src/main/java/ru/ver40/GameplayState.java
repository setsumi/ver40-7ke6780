package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.util.AsciiDraw;

/**
 * Стейт игры.
 * 
 */
public class GameplayState extends BasicGameState {

	int m_stateID = -1;

	/**
	 * Конструктор
	 */
	public GameplayState(int stateID) {
		m_stateID = stateID;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO GameplayState init()

	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO GameplayState render()
		AsciiDraw.getInstance().draw("Gameplay state.", 5, 5, Color.blue);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO GameplayState update()
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_I)) {
			TheGame.getInstance().getInventoryDialog().show(this);
		}

	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
