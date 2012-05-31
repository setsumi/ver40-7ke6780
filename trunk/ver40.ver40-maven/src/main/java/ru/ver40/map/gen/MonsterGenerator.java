package ru.ver40.map.gen;

import java.awt.Point;

import ru.ver40.map.FloorMap;
import ru.ver40.model.Monster;
import ru.ver40.util.Rng;

/**
 * Расставляет монстров на карте.
 * 
 */
public class MonsterGenerator implements IMonsterGenerator {

	FloorMap m_map;
	int m_width, m_height;

	/**
	 * Конструктор.
	 * 
	 * @param w - ширина области
	 * @param h - высота области
	 */
	public MonsterGenerator(int w, int h) {
		m_width = w;
		m_height = h;
	}

	@Override
	public void generate(FloorMap map) {
		m_map = map;
		doGenerate();
	}

	/**
	 * Расставляет монстров на карте.
	 */
	private void doGenerate() {
		// Количество монстров.
		int max = m_width * m_height / 20;
		for (int i = 0; i < max; i++) {
			int x = Rng.randomInt(0, m_width);
			int y = Rng.randomInt(0, m_height);
			Point p = m_map.getNearestWalkable(x, y, Math.min(m_width, m_height));
			if (p != null) {
				m_map.getCell(p.x, p.y).addPerson(Monster.createWatcherFly(p.x, p.y));
			}
		}
	}

}
