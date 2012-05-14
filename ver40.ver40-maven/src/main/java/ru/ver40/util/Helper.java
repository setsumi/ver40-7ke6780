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
	 * 
	 * Меняет поданную позицию по ссылке. Возвращает true если позиция
	 * сдвинулась.
	 */
	public static boolean moveMapPointKeyboard(Point pos, int key, char c) {
		return moveMapPointKeyboard(pos, key, c, 1);
	}

	/**
	 * Перемещение точки в пределах карты с клавиатуры.
	 * 
	 * Меняет поданную позицию по ссылке. Возвращает true если позиция
	 * сдвинулась.
	 */
	public static boolean moveMapPointKeyboard(Point pos, int key, char c,
			int step) {
		boolean ret = false;
		if (key == Input.KEY_NUMPAD6) {
			ret = true;
			pos.translate(step, 0);
		} else if (key == Input.KEY_NUMPAD4) {
			ret = true;
			pos.translate(-step, 0);
		} else if (key == Input.KEY_NUMPAD2) {
			ret = true;
			pos.translate(0, step);
		} else if (key == Input.KEY_NUMPAD8) {
			ret = true;
			pos.translate(0, -step);
		} else if (key == Input.KEY_NUMPAD7) {
			ret = true;
			pos.translate(-step, -step);
		} else if (key == Input.KEY_NUMPAD9) {
			ret = true;
			pos.translate(step, -step);
		} else if (key == Input.KEY_NUMPAD1) {
			ret = true;
			pos.translate(-step, step);
		} else if (key == Input.KEY_NUMPAD3) {
			ret = true;
			pos.translate(step, step);
		}
		pos.x = FloorMap.normalizePos(pos.x);
		pos.y = FloorMap.normalizePos(pos.y);
		return ret;
	}

	/**
	 * Получить текущее время (в миллисекундах).
	 */
	public static int msTime() {
		return (int) (System.nanoTime() / 1000000);
	}
}
