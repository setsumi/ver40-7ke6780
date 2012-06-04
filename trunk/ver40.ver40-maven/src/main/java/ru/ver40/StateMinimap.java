package ru.ver40;

import java.awt.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.map.FloorMap;
import ru.ver40.map.ViewMinimap;
import ru.ver40.map.Viewport;
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Просмотр миникарты.
 * 
 */
public class StateMinimap extends UserGameState {

	private Viewport m_viewport; // вьюпорт игры (с персонажем)
	private ViewMinimap m_minimap; // миникарта
	private Point m_mapPos; // положение миникарты на карте
	private Point m_sector; // координаты чанка (текущий сектор миникарты)
	private int m_zoom; // увеличение миникарты

	private float m_keybAccum = 0; // накопляет дельту

	/**
	 * Конструктор.
	 * @param sx - положение на экране левого верхнего угла (в символах)
	 * @param sy - положение на экране левого верхнего угла (в символах)
	 * @param w - ширина (в символах)
	 * @param h - высота (в символах)
	 * @param mx - положение на карте
	 * @param my - положение на карте
	 * @param zoom - коэффициент увеличения
	 * @param col - цвет миникарты
	 * @param view - вьюпорт игры (для отрисовки его рамочки на миникарте)
	 */
	public StateMinimap(int sx, int sy, int w, int h, int mx, int my, int zoom,
			Color col, Viewport view) {
		super();
		attachToSystemState(Constants.STATE_MINIMAP);
		//
		m_minimap = new ViewMinimap(sx, sy, w, h, mx, my, zoom, col);
		m_minimap.init(mx, my);
		m_viewport = view;
		m_mapPos = new Point(mx, my);
		m_sector = new Point(FloorMap.getChunkXY(mx, my));
		m_zoom = zoom;
	}

	/* *******************************************************
	 * Overrides.
	 */
	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		//
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
		//
		Input input = gc.getInput();
		boolean updated = false;
		if (input.isKeyDown(Input.KEY_NUMPAD6)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) m_keybAccum, 0);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD4)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) -m_keybAccum, 0);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD8)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate(0, (int) -m_keybAccum);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD2)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate(0, (int) m_keybAccum);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD7)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) -m_keybAccum, (int) -m_keybAccum);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD3)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) m_keybAccum, (int) m_keybAccum);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD1)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) -m_keybAccum, (int) m_keybAccum);
				m_keybAccum = 0;
			}
		} else if (input.isKeyDown(Input.KEY_NUMPAD9)) {
			updated = true;
			m_keybAccum += 0.3f * delta / m_zoom;
			if (m_keybAccum >= 1) {
				m_mapPos.translate((int) m_keybAccum, (int) -m_keybAccum);
				m_keybAccum = 0;
			}
		}
		if (updated) {
			m_mapPos.x = FloorMap.normalizePos(m_mapPos.x);
			m_mapPos.y = FloorMap.normalizePos(m_mapPos.y);
			m_minimap.moveTo(m_mapPos.x, m_mapPos.y);
			m_sector = FloorMap.getChunkXY(m_mapPos.x, m_mapPos.y);
		}
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		super.onRender(gc, game, g);
		//
		// сама миникарта
		m_minimap.setClip(g);
		m_minimap.draw(g);

		// рамочка вьюпорта на миникарте
		g.setColor(Color.white);
		g.drawRect(m_minimap.transMapToScrX(m_viewport.getMapTopX()),
				m_minimap.transMapToScrY(m_viewport.getMapTopY()), m_viewport.getWidth()
						* m_minimap.getZoom(), m_viewport.getHeight() * m_minimap.getZoom());
		m_minimap.clearClip(g);

		// статус-текст
		AsciiDraw.getInstance().draw(
				"Position " + m_mapPos.x + ":" + m_mapPos.y + " Sector " + m_sector.x + ":"
						+ m_sector.y + " Zoom x" + m_zoom + " [+][-] adjust zoom", 1, 1,
				Color.white);

//		// DEBUG
//		g.setColor(Color.red);
//		g.drawString("View: " + m_mapPos.x + ", " + m_mapPos.y, 100, 15);
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			exitModal();
		} else if (c == '-'/*key == Input.KEY_SUBTRACT*/) {
			if (--m_zoom < 1)
				m_zoom = 1;
			m_minimap.setZoom(m_zoom);
		} else if (c == '+'/*key == Input.KEY_ADD*/) {
			if (++m_zoom > 3)
				m_zoom = 3;
			m_minimap.setZoom(m_zoom);
		}

//		if (Helper.moveMapPointKeyboard(m_mapPos, key, c, 10)) {
//			m_minimap.moveTo(m_mapPos.x, m_mapPos.y);
//			m_sector = m_map.getChunkXY(m_mapPos.x, m_mapPos.y);
//		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
