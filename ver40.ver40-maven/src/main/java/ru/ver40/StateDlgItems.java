package ru.ver40;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.system.UserGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.util.Constants;

public class StateDlgItems extends UserGameState {

	public class Item {
		String name;

		public Item(String name) {
			this.name = name;
		}
	}

	private LinkedList<Item> m_items;

	/**
	 * Конструктор.
	 */
	public StateDlgItems() {
		super();
		attachToSystemState(Constants.STATE_DLG_ITEMS);
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);

		m_items = new LinkedList<Item>();
		m_items.add(new Item("Small gun"));
		m_items.add(new Item("Gun"));
		m_items.add(new Item("Big gun"));
		m_items.add(new Item("Huge gun"));
		m_items.add(new Item("Small rifle"));
		m_items.add(new Item("Rifle"));
		m_items.add(new Item("Big rifle"));
		m_items.add(new Item("Huge rifle"));
		m_items.add(new Item("Cannon rifle"));
		m_items.add(new Item("Cannon fodder"));
		m_items.add(new Item("Self destruct kit"));
		m_items.add(new Item("Repair kit"));
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			m_manager.exitModal();
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		AsciiDraw.getInstance().draw("Inventory Dlg", 1, 10, Color.white);
	}

}
