package ru.ver40.util;

import java.awt.Point;

import org.newdawn.slick.Input;

import ru.ver40.map.FloorMap;

/**
 * Свалка полезных функций.
 * 
 */
public class Helper {

	/**
	 * Перемещение точки в пределах карты с клавиатуры.
	 */
	public static Point moveMapPointKeyboard(Point pos, int key, char c) {
		if (key == Input.KEY_NUMPAD6) {
			pos.translate(1, 0);
		} else if (key == Input.KEY_NUMPAD4) {
			pos.translate(-1, 0);
		} else if (key == Input.KEY_NUMPAD2) {
			pos.translate(0, 1);
		} else if (key == Input.KEY_NUMPAD8) {
			pos.translate(0, -1);
		} else if (key == Input.KEY_NUMPAD7) {
			pos.translate(-1, -1);
		} else if (key == Input.KEY_NUMPAD9) {
			pos.translate(1, -1);
		} else if (key == Input.KEY_NUMPAD1) {
			pos.translate(-1, 1);
		} else if (key == Input.KEY_NUMPAD3) {
			pos.translate(1, 1);
		}
		pos.x = FloorMap.normalizePos(pos.x);
		pos.y = FloorMap.normalizePos(pos.y);
		return pos;
	}

}
