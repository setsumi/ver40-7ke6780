package ru.ver40;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import ru.ver40.engine.ResourceManager;
import ru.ver40.util.AsciiDraw;

public class App extends BasicGame {

	// -----------------------------------------------
	// SpriteSheet font = null;
	// Image letter = null;
	AsciiDraw ascii = null;
	Point center;
	int radius;
	Point pos;
	float track;

	// -----------------------------------------------

	public App() {
		super("ver.40-7KE.6780");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Загрузка всех ресурсов
		try {
			ResourceManager.loadResources("data/resources.xml");
		} catch (IOException e) {
			Log.error("failed to load ressource file 'data/resources.xml': "
					+ e.getMessage());
			throw new SlickException("Resource loading failed!");
		}

		// Отрисовка одиночных символов
		ascii = new AsciiDraw();

		center = new Point(250, 200);
		pos = new Point();
		radius = 200;
		track = 0.0f;
		for (int i = 0; i < 999999; i++) {
			float j = (float) Math.cos(i);
			j = track;
			track = j;
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		pos.x = (int) (center.x + radius * Math.cos(track));
		pos.y = (int) (center.y + 0.8f * Math.sin(track) * radius);
		track += (Math.PI * 2) / (2000.0f * delta);
		if (track >= Math.PI * 2)
			track = 0.0f; 
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		Color c = new Color(Color.gray);
		int r = 6;
		float d = 1.0f / r;
		Random rnd = new Random(1);
		for (int y = 0; y < 20; y++)
			for (int x = 0; x < r; x++) {
				if (x == 0) {
					ascii.draw("#", x + 5, y + 5, Color.lightGray, Color.black,
							g);
					ascii.draw("#", 34 - x, y + 5, Color.lightGray,
							Color.black, g);
				} else {
					if (rnd.nextInt(5) != 1)
						ascii.draw("#", x + 5, y + 5, c.darker(d * x));
					if (rnd.nextInt(5) != 3)
						ascii.draw("#", 34 - x, y + 5, c.darker(d * x));
				}
			}
		ascii.draw("==============================", 5, 11, Color.lightGray,
				Color.black, g);
		ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 12, Color.lightGray,
				Color.black, g);
		ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 13, Color.lightGray,
				Color.black, g);
		ascii.draw("úúúúúúúúúúúúúúúúúúúúúúúúúúúúúú", 5, 14, Color.lightGray,
				Color.black, g);
		ascii.draw("==============================", 5, 15, Color.lightGray,
				Color.black, g);
		ascii.draw("@", 15, 12, Color.white, Color.black, g);
		ascii.draw("Где я?", 3, 27, Color.magenta, Color.black, g);
		// ascii.draw("Варнинг Форевер №2! Ёлы-палы-ё?", 0, 1, col, col1, g);
		// String s = "";
		// for (int i = 0, c = 1; i < 80 * 25; i++) {
		// s += (char) i;
		// if (c == 80) {
		// ascii.draw(s, 0, i / 80, col, col1, g);
		// s = "";
		// c = 0;
		// }
		// c++;
		// }
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