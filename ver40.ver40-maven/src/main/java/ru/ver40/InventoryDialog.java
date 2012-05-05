package ru.ver40;

import org.newdawn.slick.state.GameState;


/**
 * @author Setsumi
 * 
 */
public class InventoryDialog {

	private InventoryDialogState m_state = null;

	public InventoryDialog(int id) {
		m_state = new InventoryDialogState(id);
	}

	public InventoryDialogState getState() {
		return m_state;
	}

	public int getID() {
		return m_state.getID();
	}

	public void show(GameState parent) {
		if (parent == null)
			throw new IllegalArgumentException(
					"Invalid parent. Must be not null.");
		m_state.setParent(parent);
		TheGame.getInstance().enterState(getID());
	}
}
