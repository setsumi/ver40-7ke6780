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
import ru.ver40.map.ViewMinimap;
import ru.ver40.map.Viewport;
import ru.ver40.model.Player;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.system.UserGameState;
import ru.ver40.system.ui.WndStatusPanel;
import ru.ver40.system.util.DebugLog;
import ru.ver40.system.util.GameLog;
import ru.ver40.util.Constants;

/**
 * Главный стейт игры.
 * 
 */
public class StateGameplay extends UserGameState {

	private static StateGameplay instance = null;
	private static Viewport viewport = null;
	private static StateAnimation animations = null;

	public static StateGameplay getInstance() {
		return instance;
	}

	public static Viewport getViewport() {
		return viewport;
	}

	public static StateAnimation getAnimations() {
		return animations;
	}

	private Player player;
	private IFovAlgorithm fov;
	private WndStatusPanel statusPanel; // панелька со статусом персонажа
	private int timeInGame = 0; // счетчик ходов
	private ViewMinimap minimap; // миникарта

	// непрямой вызов нового хода из других стейтов
	private boolean m_doNewTurn = false;

	public void provokeNewTurn() {
		m_doNewTurn = true;
	}

	/**
	 * Конструктор.
	 */
	public StateGameplay() {
		super();
		attachToSystemState(Constants.STATE_GAMEPLAY);
		//
		instance = this;
	}

	@Override
	public void onEnter(GameContainer gc, StateBasedGame game) {
		super.onEnter(gc, game);
		//
		Log.debug("StateGameplay.onEnter()");
	}

	@Override
	public void onLeave(GameContainer gc, StateBasedGame game) {
		super.onLeave(gc, game);
		//
		Log.debug("StateGameplay.onLeave()");
	}

	@Override
	public void onInit(GameContainer gc, StateBasedGame game) {
		super.onInit(gc, game);
		//
		Log.debug("StateGameplay.onInit()");

		GameLog.create(1, 31, Constants.ASCII_SCREEN_WIDTH - 2, 8,
				Constants.GAME_LOG_BACKCOLOR);
		statusPanel = new WndStatusPanel(62, 1, Color.white, Color.black);
		animations = (StateAnimation) TheGame.getStateManager()
				.getSystemState(Constants.STATE_ANIMATION).getClient();
		viewport = new Viewport(60, 30, 1, 1);
		minimap = new ViewMinimap(59, 31, 20, 8, 0, 0, 2, Color.green.darker(0.4f));
		fov = new PrecisePermissive();
		player = new Player("Player");
		TimeService.getInstance().register(player);
		MapService.getInstance().gotoLevel(Constants.LEVELS_MAX_LEVEL / 2, 200, 200);

		// Приветственное сообщение.
		GameLog gl = GameLog.getInstance();
		gl.log("[u] use object");
		gl.log("[m] view map");
		gl.log("[k] shoot");
		gl.log("[,] pick up items");
		gl.log("[/] look around");
		gl.log(GameLog.Type.IMPORTANT, "Welcome to base reality.");
		gl.log(GameLog.Type.IMPORTANT, "Substantiation sequence complete.");
	}

	@Override
	public void onUpdate(GameContainer gc, StateBasedGame game, int delta) {
		super.onUpdate(gc, game, delta);
		//
		TimeService t = TimeService.getInstance();
		while (t.getCurrentActor() != player || player.getActionPoints() < 0) {
			t.tick();
		}

		FloorMap map = MapService.getInstance().getMap();
		map.setFogOfWar();
		fov.visitFieldOfView(map, player.getX(), player.getY(), 15);

		statusPanel.updateData(player, timeInGame);

		if (m_doNewTurn) {
			m_doNewTurn = false;
			newTurn();
		}
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
		// Миникарта.
		if (c == 'm') {
			StateMinimap mmap = new StateMinimap(viewport.getScreenPosX(),
					viewport.getScreenPosY() + 1, viewport.getWidth(),
					viewport.getHeight() - 1, player.getX(), player.getY(), 1,
					Color.green.darker(0.4f), viewport);
			mmap.showModal();
		}
		// Стрельба.
		if (c == 'k') {
			StateShoot shoot = new StateShoot(player, viewport);
			shoot.showModal();
		}
		// Использовать объект на карте.
		if (c == 'u') {
			StateUseMapObject use = new StateUseMapObject(player, viewport);
			use.showModal();
		}
		// Кривой детект нового хода.
		if (key == Input.KEY_NUMPAD6 || key == Input.KEY_NUMPAD4
				|| key == Input.KEY_NUMPAD2 || key == Input.KEY_NUMPAD8
				|| key == Input.KEY_NUMPAD7 || key == Input.KEY_NUMPAD9
				|| key == Input.KEY_NUMPAD1 || key == Input.KEY_NUMPAD3
				|| key == Input.KEY_NUMPAD5) {
			player.setKeyCode(key);
			newTurn();
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		super.onRender(gc, game, g);
		//
		// Базовый интерфейс игры.
		viewport.draw(g, player);
		minimap.draw(g);
		statusPanel.draw(g);
		GameLog.getInstance().draw(g);

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
	private void newTurn() {
		timeInGame++;
		DebugLog.getInstance().resetNew();
		GameLog.getInstance().resetNew();
		TimeService.getInstance().tick();

		viewport.moveTo(player.getX(), player.getY());
		minimap.moveTo(player.getX(), player.getY());
		// Запуск анимаций.
		if (animations.count() > 0) {
			animations.showModal();
		}
	}

	/**
	 * Инициализация всего хлама при смене карты.
	 */
	public void newMap(int x, int y) {
		FloorMap map = MapService.getInstance().getMap();
		Point p = map.getNearestWalkable(x, y, 200);
		player.setX(p.x);
		player.setY(p.y);
		map.getCell(p.x, p.y).addPerson(player);
		map.setPlayer(player);
		viewport.init(map, p.x, p.y);
		minimap.init(p.x, p.y);
	}

	/**
	 * Вернуть текущего игрока.
	 */
	public Player getPlayer() {
		return player;
	}
}
