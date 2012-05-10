package ru.ver40;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import rlforj.los.IFovAlgorithm;
import rlforj.los.PrecisePermissive;
import ru.ver40.map.FloorMap;
import ru.ver40.map.Viewport;
import ru.ver40.map.gen.FeatureGenerator;
import ru.ver40.map.gen.IMapGenarator;
import ru.ver40.model.Player;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.system.StateManager;
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.DebugLog;
import ru.ver40.util.Constants;
import ru.ver40.util.GameLog;

/**
 * Главный стейт игры.
 * 
 */
public class StateGameplay extends UserGameState {

	Viewport viewport;
	Point viewPos;
	private Player player;
	private IFovAlgorithm fov;

	boolean freeLook = false;

	/**
	 * Конструктор.
	 */
	public StateGameplay(StateManager manager) {
		super(manager);
	}

	@Override
	public void onEnter(GameContainer gc, StateBasedGame game) {
		super.onEnter(gc, game);
		Log.debug("StateGameplay.onEnter()");
	}

	@Override
	public void onLeave(GameContainer gc, StateBasedGame game) {
		super.onLeave(gc, game);
		Log.debug("StateGameplay.onLeave()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		Log.debug("StateGameplay.onInit()");
		
		FloorMap map = new FloorMap("map/test");
		MapService.getInstance().setcMap(map);
		viewport = new Viewport(map, 60, 30, 1, 1);
		viewPos = new Point(200, 200);
		GameLog.create(1, 31, Constants.ASCII_SCREEN_WIDTH - 2, 8,
				Constants.GAME_LOG_BACKCOLOR);
		player = new Player("2ch anonymous");
		TimeService.getInstance().register(player);

		IMapGenarator gen = new FeatureGenerator();
		gen.generate(map);

		o: for (int y = 190; y < 210; ++y) {
			for (int x = 190; x < 210; ++x) {
				if (!map.isObstacle(x, y)) {
					player.setX(x);
					player.setY(x);
					map.getCell(x, y).addPerson(player);
					break o;
				}
			}
		}

		// System.out.println(mm + " monsters on the map");
		Input input = gc.getInput();
		input.addKeyListener((KeyListener) player);
		map.setPlayer(player);
		fov = new PrecisePermissive();

		// Приветственное сообщение.
		GameLog gl = GameLog.getInstance();
		gl.log("[NUMPAD] - walk/look around");
		gl.log("[F] - toggle free look mode");
		gl.log("[`] - system log");
		gl.log(GameLog.Type.IMPORTANT, "Welcome to base reality.");
		gl.log(GameLog.Type.IMPORTANT, "Substantiation sequence complete.");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		TimeService t = TimeService.getInstance();
		while (t.getCurrentActor() != player) {
			t.tick();
		}

		FloorMap map = MapService.getInstance().getMap();
		map.setFogOfWar();
		fov.visitFieldOfView(map, player.getX(), player.getY(), 15);

		if (!freeLook) {
			viewPos.x = player.getX();
			viewPos.y = player.getY();
		}
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (key == Input.KEY_F) {
			freeLook = !freeLook;
			GameLog.getInstance().log(
					freeLook ? "Free look ON" : "Free look OFF");
		}
		if (freeLook) {
			if (key == Input.KEY_NUMPAD6) {
				viewPos.translate(1, 0);
			} else if (key == Input.KEY_NUMPAD4) {
				viewPos.translate(-1, 0);
			} else if (key == Input.KEY_NUMPAD2) {
				viewPos.translate(0, 1);
			} else if (key == Input.KEY_NUMPAD8) {
				viewPos.translate(0, -1);
			} else if (key == Input.KEY_NUMPAD7) {
				viewPos.translate(-1, -1);
			} else if (key == Input.KEY_NUMPAD9) {
				viewPos.translate(1, -1);
			} else if (key == Input.KEY_NUMPAD1) {
				viewPos.translate(-1, 1);
			} else if (key == Input.KEY_NUMPAD3) {
				viewPos.translate(1, 1);
			}
		}
		// Кривой детект нового хода.
		if (key == Input.KEY_NUMPAD6 || key == Input.KEY_NUMPAD4
				|| key == Input.KEY_NUMPAD2 || key == Input.KEY_NUMPAD8
				|| key == Input.KEY_NUMPAD7 || key == Input.KEY_NUMPAD9
				|| key == Input.KEY_NUMPAD1 || key == Input.KEY_NUMPAD3) {
			OnNewTurn();
		}
		// Вызов окна с предметами.
		if (key == Input.KEY_COMMA) {

		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		viewport.draw(viewPos.x, viewPos.y, g, player);
		GameLog.getInstance().draw(g);

		// Всякий хлам.
		g.setColor(Color.red);
		g.drawString("View pos: " + viewPos.x + ", " + viewPos.y, 100, 0);
	}

	/**
	 * Надо вызывать из логики игры перед началом нового хода, чтобы обновлять
	 * состояние логов.
	 */
	public void OnNewTurn() {
		DebugLog.getInstance().resetNew();
		GameLog.getInstance().resetNew();
	}
}
