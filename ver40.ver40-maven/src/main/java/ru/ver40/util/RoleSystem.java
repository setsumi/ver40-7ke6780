package ru.ver40.util;

import ru.ver40.model.Actor;
import ru.ver40.system.util.GameLog;

/**
 * Экспериментальный класс для ролевой системы
 * 
 * @author anon
 * 
 */
public class RoleSystem {

	public static void testBlast(Actor a1, Actor a2) {
		if (a2 == null) {
			GameLog.getInstance().log(a1 + " blasts empty space in vain.");
			return;
		}
		int b1 = Rng.d(2, 10, a1.getBlast());
		int b2 = Rng.d(2, 10, a2.getBlast());
		if (b1 > b2) {
			// Попал
			//
			int dmg = (b1 - b2) - a2.getResist();
			if (dmg > 0) {
				a2.damage(dmg);
				GameLog.getInstance().log(a1 + " blasts " + a2 + " for " + dmg + " damage.");
			} else {
				GameLog.getInstance().log(a1 + " blasts " + a2 + " but do no damage.");
			}
		} else {
			GameLog.getInstance().log(a1 + " misses " + a2);
		}
	}
	
	public static void testFight(Actor a1, Actor a2) {
		if (a2 == null) {
			GameLog.getInstance().log(a1 + " hits empty space in vain.");
			return;
		}
		int b1 = Rng.d(2, 10, a1.getFight());
		int b2 = Rng.d(2, 10, a2.getFight());
		if (b1 > b2) {
			// Попал
			//
			int dmg = (b1 - b2) - a2.getResist();
			if (dmg > 0) {
				a2.damage(dmg);
				GameLog.getInstance().log(a1 + " hits " + a2 + " for " + dmg + " damage.");
			} else {
				GameLog.getInstance().log(a1 + " hits " + a2 + " but do no damage.");
			}
		} else {
			GameLog.getInstance().log(a1 + " misses " + a2);
		}
	}	
}
