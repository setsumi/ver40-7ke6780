package ru.ver40;


/**
 * @author Setsumi
 *
 */
public class MainMenu {

	private MainMenuState m_state = null;

	public MainMenu(int id) {
		m_state = new MainMenuState(id);
	}

	public MainMenuState getState() {
		return m_state;
	}

	public int getID() {
		return m_state.getID();
	}

	public void show() {
		TheGame.getInstance().enterState(getID());
	}
}
