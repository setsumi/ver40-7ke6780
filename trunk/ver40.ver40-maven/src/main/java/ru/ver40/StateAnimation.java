package ru.ver40;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.map.Viewport;
import ru.ver40.system.UserGameState;
import ru.ver40.util.Constants;

/**
 * Проигрывание анимаций (как скриптового ролика).
 * 
 */
public class StateAnimation extends UserGameState {

	private Viewport m_view;
	private LinkedList<Point> m_line;
	private int m_step, m_current;
	private boolean m_abort;

	public StateAnimation(Viewport view, LinkedList<Point> line, int speed) {
		super();
		attachToSystemState(Constants.STATE_ANIMATION);
		//
		m_view = view;
		m_line = line;
		m_step = speed;
		m_current = 0;
		m_abort = (line == null || line.size() < 1);
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		//
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
		//
		if (m_abort) {
			exitModal();
		} else {
			m_current += delta;
			if (m_current > m_step) {
				m_current = 0;
				m_line.removeFirst();
				if (m_line.size() == 0) {
					m_abort = true;
				}
			}
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		if (!m_abort) {
			Point p = m_line.getFirst();
			m_view.drawString("*", p.x, p.y, Color.white, Color.black, g);
		}
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			m_abort = true;
			exitModal();
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
