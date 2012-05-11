package ru.ver40;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.system.UserGameState;
import ru.ver40.system.ui.CtrlListItems;
import ru.ver40.system.ui.MockWindow;
import ru.ver40.util.Constants;

/**
 * Стейт диалога выбора предметов.
 * 
 */
public class StateDlgItems extends UserGameState {

	public class TestItem {
		String name;

		public TestItem(String name) {
			this.name = name;
		}

		public String getAttribute() {
			return name;
		}
	}
	private LinkedList<TestItem> m_items;

	private static final int INDENT_X = 3;
	private static final int INDENT_Y = 2;

	private static final int CTRL_ITEMSLIST = 0;
	private MockWindow m_wnd;
	private String m_title;

	/**
	 * Конструктор.
	 */
	public StateDlgItems(String title) {
		super();
		attachToSystemState(Constants.STATE_DLG_ITEMS);
		//
		m_title = title;
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);

		m_items = new LinkedList<TestItem>();
		m_items.add(new TestItem("Small gun"));
		m_items.add(new TestItem("Gun"));
		m_items.add(new TestItem("Big gun"));
		m_items.add(new TestItem("Huge gun"));
		m_items.add(new TestItem("Small rifle"));
		m_items.add(new TestItem("Rifle"));
		m_items.add(new TestItem("Big rifle"));
		m_items.add(new TestItem("Huge rifle"));
		m_items.add(new TestItem("Cannon rifle"));
		m_items.add(new TestItem("Cannon fodder"));
		m_items.add(new TestItem("Self destruct kit"));
		m_items.add(new TestItem("Repair kit"));

		int boxW = 0;
		for (TestItem i : m_items) {
			boxW = Math.max(boxW, i.getAttribute().length());
		}
		boxW += CtrlListItems.INDENT_LEFT;
		int winW = boxW + INDENT_X * 2;

		int boxH = m_items.size() > CtrlListItems.MAX_HEIGHT ? CtrlListItems.MAX_HEIGHT
				: m_items.size();
		int winH = boxH + INDENT_Y * 2;

		m_wnd = new MockWindow(null, 3, 29 - winH, winW, winH, Color.white,
				new Color(0.3f, 0.3f, 0.3f, 0.9f), "", "[\\] - select all",
				false);
		m_wnd.addChild(CTRL_ITEMSLIST, new CtrlListItems(m_wnd, INDENT_X,
				INDENT_Y, boxW, boxH, Color.white, m_items));
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE || key == Input.KEY_ENTER) {
			m_manager.exitModal();
		}
		m_wnd.onKeyPressed(key, c);
	}

	@Override
	public void onKeyReleased(int key, char c) {
		m_wnd.onKeyReleased(key, c);
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);

		CtrlListItems listbox = (CtrlListItems) m_wnd.getChild(CTRL_ITEMSLIST);
		m_wnd.setTitle(m_title
				+ (listbox.getLastPage() == 0 ? "" : "   Page "
						+ (listbox.getCurrPage() + 1) + "/"
						+ (listbox.getLastPage() + 1)));
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		m_wnd.draw(g);
	}

}
