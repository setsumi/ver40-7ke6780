package ru.ver40;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.map.FloorMap;
import ru.ver40.map.Viewport;
import ru.ver40.model.Player;
import ru.ver40.service.MapService;
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.GameLog;
import ru.ver40.util.Constants;
import ru.ver40.util.Helper;

/**
 * Сипользовать объекты на карте.
 * 
 */
public class StateUseMapObject extends UserGameState {

	private FloorMap m_map;
//	private Player m_player;
	private Viewport m_viewport;
	private Point m_playerPos;
	private Point m_selPos;
	private boolean m_exit;
	private Color m_color;

	/**
	 * Конструктор.
	 */
	public StateUseMapObject(Player player, Viewport viewport) {
		super();
		attachToSystemState(Constants.STATE_USEMAPOBJECT);
		//
		m_map = MapService.getInstance().getMap();
//		m_player = player;
		m_viewport = viewport;
		m_playerPos = new Point(player.getX(), player.getY());
		m_selPos = new Point(-1, -1);
		m_exit = true;
		m_color = new Color(1f, 1f, 1f, 0.5f);
	}

	/**
	 * Выход из стейта.
	 */
	private void exit() {
		m_exit = true;
		exitModal();
	}

	/**
	 * Не нашли, что юзать - выходим.
	 */
	private void nothingToUse() {
		GameLog.getInstance().log("Nothing to use here");
		exit();
	}

	/* *****************************************
	 * Overrides.
	 */
	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		//
		// Ищем активные объекты возле игрока.
		if (m_map.isActive(m_playerPos.x, m_playerPos.y)) {
			m_selPos.x = m_playerPos.x;
			m_selPos.y = m_playerPos.y;
		} else {
			int x, y;
			o: for (int i = m_playerPos.x - 1; i <= m_playerPos.x + 1; i++) {
				for (int j = m_playerPos.y - 1; j <= m_playerPos.y + 1; j++) {
					x = FloorMap.normalizePos(i);
					y = FloorMap.normalizePos(j);
					if (m_map.isActive(x, y)) {
						m_selPos.x = x;
						m_selPos.y = y;
						break o;
					}
				}
			}
		}
		m_exit = false;
		if (m_selPos.x == -1 || m_selPos.y == -1) {
			nothingToUse();
		}
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
		//
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		if (!m_exit) {
			m_viewport.drawString("\0", m_selPos.x, m_selPos.y, m_color);
		}
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (!m_exit) {
			if (key == Input.KEY_NUMPAD5 || key == Input.KEY_ENTER) {
				if (m_map.isActive(m_selPos.x, m_selPos.y)) {
					m_map.getCell(m_selPos.x, m_selPos.y).getBuilding().use();
					exit();
				} else {
					nothingToUse();
				}
			} else if (key == Input.KEY_ESCAPE) {
				exit();
			} else {
				if (Helper.moveMapPointKeyboard(m_selPos, key, c)) {
					if (m_selPos.x < m_playerPos.x - 1)
						m_selPos.x = m_playerPos.x - 1;
					if (m_selPos.x > m_playerPos.x + 1)
						m_selPos.x = m_playerPos.x + 1;
					if (m_selPos.y < m_playerPos.y - 1)
						m_selPos.y = m_playerPos.y - 1;
					if (m_selPos.y > m_playerPos.y + 1)
						m_selPos.y = m_playerPos.y + 1;
				}
			}
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
