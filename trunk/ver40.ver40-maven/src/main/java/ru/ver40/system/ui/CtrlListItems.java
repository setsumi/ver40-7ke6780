package ru.ver40.system.ui;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import ru.ver40.StateDlgItems.TestItem;
import ru.ver40.system.util.AsciiDraw;

/**
 * Оконный контрол со списком предметов.
 */
public class CtrlListItems extends BaseWindow {
	private class ItemInfo {
		private TestItem m_item = null;
		private boolean m_selected = false;

		public ItemInfo(TestItem item) {
			m_item = item;
		}

		public TestItem getItem() {
			return m_item;
		}

		public boolean getSelected() {
			return m_selected;
		}
		public void setSelected(boolean sel) {
			m_selected = sel;
		}
	}

	public static final int MAX_HEIGHT = 20; // Не больше 26 (кол-во букв).
	public static final int INDENT_LEFT = 9; // Место слева для хоткеев и чеков.

	private LinkedList<ItemInfo> m_items;
	private int m_currPage, m_maxPages;
	private int m_currPageSize;

	/**
	 * Конструктор.
	 */
	public CtrlListItems(BaseWindow owner, int x, int y, int w, int h, Color c,
			LinkedList<TestItem> items) {
		super(owner, x, y, w, h > MAX_HEIGHT ? MAX_HEIGHT : h, c);
		//
		m_items = new LinkedList<ItemInfo>();
		for (TestItem i : items) {
			m_items.add(new ItemInfo(i));
		}
		m_currPage = 0;
		m_maxPages = m_items.size() / m_height
				+ (m_items.size() % m_height != 0 ? 1 : 0);
		updatePage();
	}

	private void updatePage() {
		if (m_currPage == m_maxPages - 1)
			m_currPageSize = m_items.size() % m_height;
		else
			m_currPageSize = m_height;
		if (m_currPageSize == 0)
			m_currPageSize = m_height;
	}

	public int getCurrPage() {
		return m_currPage;
	}

	public int getLastPage() {
		int last = m_maxPages - 1;
		if (last < 0)
			last = 0;
		return last;
	}

	public int getMaxPages() {
		return m_maxPages;
	}

	@Override
	public void onKeyPressed(int key, char c) {
		// Листание страниц.
		if (key == Input.KEY_NEXT) {
			m_currPage++;
		} else if (key == Input.KEY_PRIOR) {
			m_currPage--;
		}
		if (m_currPage < 0) {
			m_currPage = 0;
		} else if (m_currPage >= m_maxPages) {
			m_currPage = m_maxPages - 1;
		}
		updatePage();

		// Ставим отменки выбора предметов.
		char hkey = 'a';
		for (int i = 0; i < m_currPageSize; i++) {
			if (c == hkey + i) {
				ItemInfo ii = m_items.get(i + m_currPage * m_height);
				ii.setSelected(!ii.getSelected());
				break;
			}
		}
		// Выбрать все предметы.
		if (c == '\\') {
			for (ItemInfo i : m_items) {
				i.setSelected(true);
			}
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void draw(Graphics g) {
		int posY = m_posY;
		char hkey = 'a';
		for (int i = m_currPage * m_height; i < m_currPage * m_height
				+ m_currPageSize; i++) {
			AsciiDraw.getInstance().draw(
					Character.toString(hkey) + ")  ["
							+ (m_items.get(i).getSelected() ? "X]  " : " ]  ")
							+ m_items.get(i).getItem().getAttribute(),
					m_parent.getPosX() + m_posX, m_parent.getPosY() + posY,
					m_color);
			posY++;
			hkey++;
		}
	}

}
