package ru.ver40;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
import ru.ver40.system.UserGameState;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.GameLog;
import ru.ver40.util.Constants;

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
	boolean targetting = false;
	int targetRadius, tX, tY;

	/**
	 * Конструктор.
	 */
	public StateGameplay() {
		super();
		attachToSystemState(Constants.STATE_GAMEPLAY);
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
		super.onInit(gc, game);
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

		map.setPlayer(player);
		fov = new PrecisePermissive();

		// Приветственное сообщение.
		GameLog gl = GameLog.getInstance();
		gl.log("[K] - enter targeting mode");
		gl.log("[,] - pick up items");
		gl.log("[NUMPAD] - walk/look around");
		gl.log("[/] - toggle look mode");
		gl.log("[`] - system log");
		gl.log(GameLog.Type.IMPORTANT, "Welcome to base reality.");
		gl.log(GameLog.Type.IMPORTANT, "Substantiation sequence complete.");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);

		TimeService t = TimeService.getInstance();
		while (t.getCurrentActor() != player) {
			t.tick();
		}

		FloorMap map = MapService.getInstance().getMap();
		map.setFogOfWar();
		fov.visitFieldOfView(map, player.getX(), player.getY(), 15);

		if (!freeLook && !targetting) {
			viewPos.x = player.getX();
			viewPos.y = player.getY();
		}
		viewport.moveTo(viewPos.x, viewPos.y);
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

		if (targetting) {
			if (key == Input.KEY_NUMPAD6) {
				tX++;
			} else if (key == Input.KEY_NUMPAD4) {
				tX--;
			} else if (key == Input.KEY_NUMPAD2) {
				tY++;
			} else if (key == Input.KEY_NUMPAD8) {
				tY--;
			} else if (key == Input.KEY_NUMPAD7) {
				tX--;
				tY--;
			} else if (key == Input.KEY_NUMPAD9) {
				tX++;
				tY--;
			} else if (key == Input.KEY_NUMPAD1) {
				tX--;
				tY++;
			} else if (key == Input.KEY_NUMPAD3) {
				tX++;
				tY++;
			} else if (key == Input.KEY_NUMPAD5 || key == Input.KEY_ENTER) {
				targetting = false;
			}

		}
		if (!targetting && !freeLook) {
			// Кривой детект нового хода.
			if (key == Input.KEY_NUMPAD6 || key == Input.KEY_NUMPAD4
					|| key == Input.KEY_NUMPAD2 || key == Input.KEY_NUMPAD8
					|| key == Input.KEY_NUMPAD7 || key == Input.KEY_NUMPAD9
					|| key == Input.KEY_NUMPAD1 || key == Input.KEY_NUMPAD3) {
				player.setKeyCode(key);			
				newTurn();
			}
		}
		// Вызов окна с предметами.
		if (key == Input.KEY_COMMA) {
			StateDlgItems items = new StateDlgItems("Items on the floor:");
			items.showModal();
		}

		if (key == Input.KEY_K) {
			tX = player.getX();
			tY = player.getY();
			targetting = true;
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		viewport.draw(g, player);

		// Прицеливанивае
		//
		if (targetting) {
			g.setColor(Color.yellow);
			g.drawString("Target: " + tX + ", " + tY, 100, 15);

			viewport.drawString("X", tX, tY, Color.yellow, Color.black, g);
		}

		GameLog.getInstance().draw(g);
		// Отладочные сообщения.
		g.setColor(Color.red);
		g.drawString("View: " + viewPos.x + ", " + viewPos.y, 100, 0);
	}

	/**
	 * Надо вызывать из логики игры перед началом нового хода, чтобы обновлять
	 * состояние логов.
	 */
	public void newTurn() {
		DebugLog.getInstance().resetNew();
		GameLog.getInstance().resetNew();
		TimeService.getInstance().tick();
	}
}
