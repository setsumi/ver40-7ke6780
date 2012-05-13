package ru.ver40;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.ver40.map.FloorMap;
import ru.ver40.map.FloorMap.CellLocation;
import ru.ver40.service.MapService;
import ru.ver40.system.UserGameState;
import ru.ver40.util.Constants;
import ru.ver40.util.Helper;

/**
 * Просмотр миникарты.
 * 
 */
public class StateMinimap extends UserGameState {

	private FloorMap m_map;
	private Point m_scrPos; // положение на экране
	private int m_width, m_height; // размер в пикселах

	private Point m_mapPos; // положение вьюпорта на карте
	private Point m_top; // верхний левый угол вьюпорта на карте
	private HashMap<Integer, Image> m_imgMaps; // кеш картинок миникарт чанков

	private Image m_img = null;

	/**
	 * Конструктор.
	 */
	public StateMinimap(int sx, int sy, int mx, int my, int w, int h) {
		super();
		attachToSystemState(Constants.STATE_MINIMAP);
		//
		m_map = MapService.getInstance().getMap();
		m_scrPos = new Point(sx, sy);
		m_mapPos = new Point(mx, my);
		m_width = w;
		m_height = h;
		m_top = new Point();
		m_imgMaps = new HashMap<Integer, Image>();
	}

	private void getImgMap(int x, int y) {
		FloorMap.CellLocation loc = m_map.getChunk(x, y);
		Image img = null;
		Graphics g = null;
		try {
			img = new Image(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);
			g = img.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		g.setColor(Color.green);
		g.setBackground(Color.darkGray);
		g.clear();
		g.drawString(Integer.toString(loc.m_chunk.getIndex()), 1, 1);
		int ci = 0;
		for (int i = 0; i < Constants.MAP_CHUNK_SIZE; i++) {
			for (int j = 0; j < Constants.MAP_CHUNK_SIZE; j++) {
				if (loc.m_chunk.getCell(ci).isPassable()) {
					g.fillRect(j, i, 1, 1);
				}
				ci++;
			}
		}
		g.flush();
		m_img = img;
	}

	/**
	 * Переместить вьюпорт в указанную точку на карте.
	 */
	public void moveTo(int x, int y) {
		m_mapPos.x = FloorMap.normalizePos(x);
		m_mapPos.y = FloorMap.normalizePos(y);
		m_top.x = getViewX(m_mapPos.x);
		m_top.y = getViewY(m_mapPos.y);
	}

	/**
	 * Получить валидные координаты левого верхнего угла вьюпорта по желаемому
	 * положению на карте его центра.
	 */
	public int getViewX(int x) {
		int viewX = x - (m_width / 2);
		if (viewX < 0)
			viewX = 0;
		else if (viewX + m_width > Constants.MAP_MAX_SIZE)
			viewX = Constants.MAP_MAX_SIZE - m_width;
		return viewX;
	}

	/**
	 * Получить валидные координаты левого верхнего угла вьюпорта по желаемому
	 * положению на карте его центра.
	 */
	public int getViewY(int y) {
		int viewY = y - (m_height / 2);
		if (viewY < 0)
			viewY = 0;
		else if (viewY + m_height > Constants.MAP_MAX_SIZE)
			viewY = Constants.MAP_MAX_SIZE - m_height;
		return viewY;
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
		moveTo(m_mapPos.x, m_mapPos.y);
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// CellLocation loc = m_map.getMiniMap(m_mapPos.x, m_mapPos.y);
		// Image im = loc.m_chunk.getMiniMap();
		if (m_img != null)
			g.drawImage(m_img, 30, 30);

		// DEBUG
		g.setColor(Color.yellow);
		g.drawString("View: " + m_mapPos.x + ", " + m_mapPos.y, 100, 15);
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			exitModal();
		}
		if (Helper.moveMapPointKeyboard(m_mapPos, key, c,
				Constants.MAP_CHUNK_SIZE)) {
			getImgMap(m_mapPos.x, m_mapPos.y);
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
