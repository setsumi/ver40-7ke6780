package ru.ver40.system;

import java.awt.Point;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import rlforj.math.Point2I;
import ru.ver40.map.Viewport;

/**
 * Полет пули.
 * 
 */
public class AnimationBulletFlight extends AAnimation {

	Viewport m_view;
	List<Point2I> m_line;

	/**
	 * Конструктор.
	 */
	public AnimationBulletFlight(Viewport view, List<Point2I> line,
			int duration) {
		super(AAnimation.Type.FIXED_FRAME_TIME, line.size(), duration,
				false);
		//
		m_view = view;
		m_line = line;
	}

	@Override
	protected void onDraw(Graphics g) {
		Point p = m_line.get(m_currFrame);
		m_view.drawString("*", p.x, p.y, Color.white, Color.black, g);
	}

}
