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
import ru.ver40.system.ui.WndStatusPanel;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.GameLog;
import ru.ver40.util.Constants;
import ru.ver40.util.Helper;

/**
 * Главный стейт игры.
 * 
 */
public class StateGameplay extends UserGameState {

	Viewport viewport;
	private Player player;
	private IFovAlgorithm fov;
	WndStatusPanel statusPanel;
	int timeInGame = 0;

	boolean targetting = false;
	Point targetPos;
	int targetRadius;

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
		GameLog.create(1, 31, Constants.ASCII_SCREEN_WIDTH - 2, 8,
				Constants.GAME_LOG_BACKCOLOR);
		player = new Player("2ch anonymous");
		TimeService.getInstance().register(player);
		statusPanel = new WndStatusPanel(62, 1, Color.white, Color.black);
		targetPos = new Point(0, 0);

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
		gl.log("[k] - targeting mode");
		gl.log("[,] - pick up items");
		gl.log("[/] - look mode");
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

		if (!targetting) {
			viewport.moveTo(player.getX(), player.getY());
		}
		statusPanel.updateData(player, timeInGame);
	}

	@Override
	public void onKeyPressed(int key, char c) {
		// Обзор карты.
		if (c == '/') {
			StateFreeLook look = new StateFreeLook(viewport);
			look.showModal();
		}
		// Окно с предметами.
		if (c == ',') {
			StateDlgItems items = new StateDlgItems("Items on the floor:");
			items.showModal();
		}

		if (key == Input.KEY_K) {
			targetPos.x = player.getX();
			targetPos.y = player.getY();
			targetting = true;
		}
		if (targetting) {
			targetPos = Helper.moveMapPointKeyboard(targetPos, key, c);
			if (key == Input.KEY_NUMPAD5 || key == Input.KEY_ENTER) {
				targetting = false;
			}

		}
		if (!targetting) {
			// Кривой детект нового хода.
			if (key == Input.KEY_NUMPAD6 || key == Input.KEY_NUMPAD4
					|| key == Input.KEY_NUMPAD2 || key == Input.KEY_NUMPAD8
					|| key == Input.KEY_NUMPAD7 || key == Input.KEY_NUMPAD9
					|| key == Input.KEY_NUMPAD1 || key == Input.KEY_NUMPAD3) {
				player.setKeyCode(key);
				newTurn();
			}
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// Базовый интерфейс игры.
		//
		viewport.draw(g, player);
		statusPanel.draw(g);
		GameLog.getInstance().draw(g);

		// Прицеливанивае
		//
		if (targetting) {
			viewport.drawString("X", targetPos.x, targetPos.y, Color.yellow,
					Color.black, g);
			// DEBUG
			g.setColor(Color.yellow);
			g.drawString("Target: " + targetPos.x + ", " + targetPos.y, 100, 15);
		}

		// DEBUG
		g.setColor(Color.red);
		g.drawString(
				"View: " + viewport.getMapPosX() + ", " + viewport.getMapPosY(),
				100, 0);
	}

	/**
	 * Надо вызывать из логики игры перед началом нового хода, чтобы обновлять
	 * состояние логов.
	 */
	public void newTurn() {
		timeInGame++;
		DebugLog.getInstance().resetNew();
		GameLog.getInstance().resetNew();
		TimeService.getInstance().tick();
	}
}
