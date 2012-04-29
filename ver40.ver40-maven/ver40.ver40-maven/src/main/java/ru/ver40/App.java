package ru.ver40;

import java.awt.Point;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import ru.ver40.engine.ResourceManager;
import ru.ver40.map.Cell;
import ru.ver40.map.FloorMap;
import ru.ver40.map.Viewport;
import ru.ver40.model.Building;
import ru.ver40.model.Floor;
import ru.ver40.model.MapCell;
import ru.ver40.model.Symbol;
import ru.ver40.util.AsciiDraw;

public class App extends BasicGame {

	// -----------------------------------------------
	AsciiDraw m_ascii;
	Point center;
	int radius;
	Point pos;
	float track;

	FloorMap m_map;
	Viewport m_view;
	Point m_viewPos;

	// -----------------------------------------------

	public App() {
		super("ver.40-7KE.6780");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// Загрузка всех ресурсов
		try {
			ResourceManager.loadResources("data/resources.xml");
		} catch (IOException e) {
			Log.error("failed to load resource file 'data/resources.xml': "
					+ e.getMessage());
			throw new SlickException("Resource loading failed!");
		}

		// Input input = gc.getInput();
		// input.enableKeyRepeat();

		// Отрисовка ASCII
		m_ascii = new AsciiDraw();

		m_map = new FloorMap("map/test");
		m_view = new Viewport(m_map, 60, 30, 1, 1);
		m_viewPos = new Point(200, 200);

		for (int i = 1; i < 400; i++) {
			for (int j = 1; j < 400; j++) {
				MapCell c = m_map.getCell(i, j);
				if (Math.random() < 0.2f) {
					c.setFloor(new Floor());
					c.setBuilding(MapCell.createWall().getBuilding());
				} else {
					c = new MapCell();
				}
			}
		}
		

		center = new Point(250, 200);
		pos = new Point();
		radius = 200;
		track = 0.0f;

		// TODO debug abort
		// gc.exit();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		pos.x = (int) (center.x + radius * Math.cos(track));
		pos.y = (int) (center.y + 0.8f * Math.sin(track) * radius);
		track += (Math.PI * 2) / (2000.0f * delta);
		if (track >= Math.PI * 2)
			track = 0.0f;

		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_NUMPAD6)) {
			m_viewPos.translate(1, 0);
		} else if (input.isKeyDown(Input.KEY_NUMPAD4)) {
			m_viewPos.translate(-1, 0);
		} else if (input.isKeyDown(Input.KEY_NUMPAD8)) {
			m_viewPos.translate(0, -1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD2)) {
			m_viewPos.translate(0, 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD7)) {
			m_viewPos.translate(-1, -1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD3)) {
			m_viewPos.translate(1, 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD1)) {
			m_viewPos.translate(-1, 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD9)) {
			m_viewPos.translate(1, -1);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		// Color c = new Color(Color.gray);
		// int r = 6;
		// float d = 1.0f / r;
		// Random rnd = new Random(1);
		// for (int y = 0; y < 20; y++)
		// for (int x = 0; x < r; x++) {
		// if (x == 0) {
		// m_ascii.draw("#", x + 5, y + 5, Color.lightGray,
		// Color.blue, g);
		// m_ascii.draw("#", 34 - x, y + 5, Color.lightGray,
		// Color.blue, g);
		// } else {
		// if (rnd.nextInt(5) != 1)
		// m_ascii.draw("#", x + 5, y + 5, c.darker(d * x));
		// if (rnd.nextInt(5) != 3)
		// m_ascii.draw("#", 34 - x, y + 5, c.darker(d * x));
		// }
		// }
		// m_ascii.draw("==============================", 5, 11,
		// Color.lightGray,
		// Color.black, g);
		// m_ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 12,
		// Color.lightGray,
		// Color.black, g);
		// m_ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 13,
		// Color.lightGray,
		// Color.black, g);
		// m_ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 14,
		// Color.lightGray,
		// Color.black, g);
		// m_ascii.draw("==============================", 5, 15,
		// Color.lightGray,
		// Color.black, g);
		// m_ascii.draw("@", 15, 12, Color.white, Color.black, g);
		// m_ascii.draw("Где я?", 3, 27, Color.magenta, Color.black, g);

		m_view.drawRaw(m_ascii, m_viewPos.x, m_viewPos.y, g);
		g.setColor(Color.red);
		g.drawString("View pos: " + m_viewPos.x + ", " + m_viewPos.y, 100, 0);
		// g.setBackground(new Color(0.0f, 0.0f, 0.5f));
		// Color col = new Color(1.0f, 1.0f, 0.0f);
		// Color col1 = new Color(1.0f, 0.0f, 0.0f);
		// m_ascii.drawFree("WARNING FoREVer!", pos.x, pos.y, col, col1, g);
		//
		// g.drawString("Max Image size: " + BigImage.getMaxSingleImageSize(),
		// 100, 0);

	}

	/**
	 * main()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new App());
			// app.setDisplayMode(640, 480, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}