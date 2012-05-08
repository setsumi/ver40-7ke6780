package ru.ver40;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import rlforj.los.IFovAlgorithm;
import rlforj.los.PrecisePermissive;
import ru.ver40.map.FloorMap;
import ru.ver40.map.Viewport;
import ru.ver40.map.gen.FeatureGenerator;
import ru.ver40.map.gen.IMapGenarator;
import ru.ver40.model.MapCell;
import ru.ver40.model.Player;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.MyLogSystem;
import ru.ver40.system.util.ResourceManager;
import ru.ver40.system.util.UnicodeDraw;
import ru.ver40.util.Constants;
import ru.ver40.util.GameLog;

	
public class App extends BasicGame {

	//
	Point center;
	int radius;
	Point pos;
	float track;

	//

	FloorMap m_map;
	Viewport m_view;
	Point m_viewPos;
	private Player p;
	private IFovAlgorithm fov;
	// -----------------------------------------------

	public App() {
		super("ver.40-7KE.6780");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//
		center = new Point(250, 430);
		pos = new Point();
		radius = 30;
		track = 0.0f;

		GameLog.create(1, 30, Constants.ASCII_SCREEN_WIDTH - 2, 6,
				Constants.GAME_LOG_BACKCOLOR);
		//

		// Загрузка ресурсов
		try {
			ResourceManager.loadResources("data/resources.xml");
		} catch (IOException e) {
			Log.error("failed to load resource file 'data/resources.xml': "
					+ e.getMessage());
			throw new SlickException("Resource loading failed!");
		}

		// повтор нажатых клавиш
		Input input = gc.getInput();
		input.enableKeyRepeat();
		
		
		// Отрисовка символов
		AsciiDraw.getInstance();
		UnicodeDraw.getInstance();

		m_map = new FloorMap("map/test");
		MapService.getInstance().setcMap(m_map);
		m_view = new Viewport(m_map, 60, 30, 0, 0);
		m_viewPos = new Point(200, 200);
		p = new Player("2ch anonymous");
		TimeService.getInstance().register(p);

		IMapGenarator gen = new FeatureGenerator();
		gen.generate(m_map);
		
		int x = 200;
		int y = 200;
		p.setX(x);
		p.setY(x);
		m_map.getCell(x, y).addPerson(p);
		
		
		
		
//		System.out.println(mm + " monsters on the map");
		input.addKeyListener((KeyListener) p);
		m_map.setPlayer(p);
		fov = new PrecisePermissive();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		TimeService t = TimeService.getInstance();
		while (t.getCurrentActor() != p) {
			t.tick();
		}

		m_viewPos.x = p.getX();
		m_viewPos.y = p.getY();

		Input input = gc.getInput();
		// debug log
		if (input.isKeyPressed(Input.KEY_GRAVE)) {
			if (DebugLog.showLog)
				DebugLog.getInstance().resetNew();
			DebugLog.showLog = !DebugLog.showLog;
		} else if (input.isKeyPressed(Input.KEY_Q)) {
			// m_map.SaveChunks();
			// gc.exit();
		}

		m_map.setFogOfWar();
		fov.visitFieldOfView(m_map, p.getX(), p.getY(), 15);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		m_view.draw(m_viewPos.x, m_viewPos.y, g, p);
		g.setColor(Color.red);
		g.drawString("View pos: " + m_viewPos.x + ", " + m_viewPos.y, 100, 0);

		//
		// AsciiDraw.getInstance().drawFree("WARNING FoREVer!", 500, pos.y,
		// Color.yellow, Color.red, g);
		// AsciiDraw.getInstance().draw("ASCII Текст.", 1, 35, Color.green);
		// UnicodeDraw.getInstance().draw(
		// "Нам не хватает мыла. Замылим помыльнее.", 8, 440, Color.green);

		DebugLog.getInstance().draw(g);
		GameLog.getInstance().draw(g);
		//
	}

	/**
	 * main()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Инициализация логов.
			DebugLog.create(0, 0, Constants.ASCII_SCREEN_WIDTH, 20);
			Log.setLogSystem(MyLogSystem.getInstance());

			// Запуск игры.
			AppGameContainer app = new AppGameContainer(new App());
			// app.setDisplayMode(640, 480, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}	
}