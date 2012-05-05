package ru.ver40;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.util.AsciiDraw;

/**
 * @author Setsumi
 *
 */
public class InventoryDialogState extends BasicGameState {

	private int m_stateID = -1;

	private GameState m_parentState = null;
	private int m_posX, m_posY, m_width, m_height;
	private Color m_fgColor, m_bgColor;

	/**
	 * Конструктор.
	 */
	public InventoryDialogState(int stateID) {
		m_stateID = stateID;
	}

	public void setParent(GameState parentState) {
		m_parentState = parentState;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		m_posX = 4;
		m_posY = 2;
		m_width = 50;
		m_height = 15;
		m_fgColor = new Color(1.0f, 1.0f, 1.0f);
		m_bgColor = new Color(0.0f, 0.0f, 1.0f, 0.5f);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// рисуем родительский стейт на фоне
		m_parentState.render(container, game, g);

		// рисуем окно инвенторя
		for (int i = 0; i < m_width; i++) {
			for (int j = 0; j < m_height; j++) {
				AsciiDraw.getInstance().draw(" ", m_posX + i, m_posY + j,
						m_fgColor, m_bgColor, g);
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// выходим по нажатию Esc
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			TheGame.getInstance().enterState(m_parentState.getID());
		}
		input.clearKeyPressedRecord();
	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
