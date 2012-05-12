package ru.ver40;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import ru.ver40.map.Viewport;
import ru.ver40.model.MapCell;
import ru.ver40.model.VisibilityState;
import ru.ver40.service.MapService;
import ru.ver40.system.UserGameState;
import ru.ver40.system.ui.WndTooltip;
import ru.ver40.util.Constants;
import ru.ver40.util.Helper;

/**
 * Отвязанный от игрока просмотр карты.
 * 
 */
public class StateFreeLook extends UserGameState {

	Viewport m_viewport = null;
	WndTooltip m_tipInfo = null;

	/**
	 * Конструктор.
	 */
	public StateFreeLook(Viewport view) {
		super();
		attachToSystemState(Constants.STATE_FREELOOK);
		//
		m_viewport = view;
		m_tipInfo = new WndTooltip(Color.white, new Color(0.0f, 0.0f, 1.0f,
				0.5f));
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
		// Определение информации о клетке под курсором.
		String info;
		MapCell cell = MapService.getInstance().getMap()
				.getCell(m_viewport.getMapPosX(), m_viewport.getMapPosY());
		info = cell.getVisible() == VisibilityState.INVISIBLE ? "???" : cell
				.getResultString();
		m_tipInfo.setText(info);
	}

	@Override
	public void onRender(GameContainer gc, StateBasedGame game, Graphics g) {
		// Квадратик курсора.
		m_viewport.drawString("\0", m_viewport.getMapPosX(),
				m_viewport.getMapPosY(), Color.white);
		// Окошко инфы.
		m_tipInfo.draw(g);
	}

	@Override
	public void onKeyPressed(int key, char c) {
		// Выход.
		if (key == Input.KEY_ESCAPE) {
			exitModal();
		}
		// Двигаем вид.
		Point pos = new Point(m_viewport.getMapPosX(), m_viewport.getMapPosY());
		pos = Helper.moveMapPointKeyboard(pos, key, c);
		m_viewport.moveTo(pos.x, pos.y);
	}

	@Override
	public void onKeyReleased(int key, char c) {
	}

}
