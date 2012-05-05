package ru.ver40;

/**
 * @author Setsumi
 *
 */
public class Gameplay {
	private GameplayState m_state = null;

	public Gameplay(int id) {
		m_state = new GameplayState(id);
	}

	public GameplayState getState() {
		return m_state;
	}

	public int getID() {
		return m_state.getID();
	}

	public void show() {
		TheGame.getInstance().enterState(getID());
	}

}
