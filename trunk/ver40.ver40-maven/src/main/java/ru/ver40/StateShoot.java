package ru.ver40;

import java.awt.Point;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;
import rlforj.math.Point2I;
import ru.ver40.map.Viewport;
import ru.ver40.model.AIMoveAdapter;
import ru.ver40.model.MapCell;
import ru.ver40.model.Player;
import ru.ver40.service.MapService;
import ru.ver40.system.AnimationBulletFlight;
import ru.ver40.system.UserGameState;
import ru.ver40.util.Constants;
import ru.ver40.util.Helper;
import ru.ver40.util.RoleSystem;

/**
 * Стрельба по врагам.
 * 
 */
public class StateShoot extends UserGameState {

	private Player m_player;
	private Point m_playerPos;
	private Point m_targetPos;
	private List<Point2I> m_targetLine;
	private boolean m_targetValid;
	private ILosAlgorithm m_los;
	private AIMoveAdapter m_moveAdapter;
	private Viewport m_viewport;
	private boolean m_exit;

	/**
	 * Конструктор.
	 */
	public StateShoot(Player player, Viewport viewport) {
		super();
		attachToSystemState(Constants.STATE_SHOOT);
		//
		m_player = player;
		m_playerPos = new Point(player.getX(), player.getY());
		m_targetPos = new Point(m_playerPos);
		m_targetValid = true;
		m_los = new PrecisePermissive(); // BresLos(false);
		m_moveAdapter = new AIMoveAdapter(MapService.getInstance().getMap());
		m_viewport = viewport;
		m_exit = false;
	}

	/**
	 * Выход из стейта.
	 */
	private void exit() {
		m_exit = true;
		exitModal();
	}

	/* *********************************************
	 * Overrides
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
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		super.onRender(gc, game, g);
		//
		if (!m_exit) {
			if (m_targetLine != null) {
				for (Point p : m_targetLine) {
					m_viewport.drawString("x", p.x, p.y, m_targetValid ? Color.yellow
							: Color.red, Color.black, g);
				}
			}
			m_viewport.drawString("X", m_targetPos.x, m_targetPos.y,
					m_targetValid ? Color.yellow : Color.red, Color.black, g);
			// DEBUG
			g.setColor(Color.yellow);
			g.drawString("Target: " + m_targetPos.x + ", " + m_targetPos.y, 100, 15);
		}
	}

	@Override
	public void onKeyPressed(int key, char c) {
		if (!m_exit) {
			// Прицеливание.
			if (Helper.moveMapPointKeyboard(m_targetPos, key, c)) {
				// Получить линию стрельбы от одной точки до другой (как у
				// монстров в ShootOnSeeAI).
				m_targetValid = m_los.existsLineOfSight(m_moveAdapter, m_playerPos.x,
						m_playerPos.y, m_targetPos.x, m_targetPos.y, true);
				m_targetLine = m_los.getProjectPath();
			}
			// Огонь!
			if (m_targetValid && (key == Input.KEY_NUMPAD5 || key == Input.KEY_ENTER)) {
				MapCell cell = MapService.getInstance().getMap()
						.getCell(m_targetPos.x, m_targetPos.y);
				if (!cell.getPersons().isEmpty()) {
					RoleSystem.testBlast(m_player, cell.getPersons().get(0));
				}
				m_player.setKeyCode(Input.KEY_NUMPAD5); // держит на месте?
				// добавить анимацию
				AnimationBulletFlight animation = new AnimationBulletFlight(m_viewport,
						m_targetLine, 20);
				StateGameplay.getAnimations().add(animation);

				StateGameplay.getInstance().provokeNewTurn();
				exit();
			}
			// Передумали стрелять.
			if (key == Input.KEY_ESCAPE) {
				exit();
			}
		}
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
