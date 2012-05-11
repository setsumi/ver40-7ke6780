package ru.ver40.system.ui;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Базовый класс для всех оконных объектов.
 * 
 */
public abstract class BaseWindow {

	protected BaseWindow m_parent;
	protected Map<Integer, BaseWindow> m_children;

	protected int m_posX, m_posY;
	protected int m_width, m_height;
	protected Color m_color;

	public BaseWindow(BaseWindow parent, int x, int y, int w, int h, Color c) {
		m_parent = parent;
		m_children = new HashMap<Integer, BaseWindow>();

		m_posX = x;
		m_posY = y;
		m_width = w;
		m_height = h;
		m_color = c;
	}

	public void addChild(int id, BaseWindow control) {
		m_children.put(id, control);
	}

	public BaseWindow getChild(int id) {
		return m_children.get(id);
	}

	public int getPosX() {
		return m_posX;
	}

	public int getPosY() {
		return m_posY;
	}

	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}

	public Color getColor() {
		return m_color;
	}

	public void setPosX(int x) {
		m_posX = x;
	}

	public void setPosY(int y) {
		m_posY = y;
	}

	public void setWidth(int w) {
		m_width = w;
	}

	public void setHeight(int h) {
		m_height = h;
	}

	public void setColor(Color c) {
		m_color = c;
	}

	public void onKeyPressed(int key, char c) {
		for (Map.Entry<Integer, BaseWindow> entry : m_children.entrySet()) {
			entry.getValue().onKeyPressed(key, c);
		}
	}

	public void onKeyReleased(int key, char c) {
		for (Map.Entry<Integer, BaseWindow> entry : m_children.entrySet()) {
			entry.getValue().onKeyReleased(key, c);
		}
	}

	public void draw(Graphics g) {
		for (Map.Entry<Integer, BaseWindow> entry : m_children.entrySet()) {
			entry.getValue().draw(g);
		}
	}

}
