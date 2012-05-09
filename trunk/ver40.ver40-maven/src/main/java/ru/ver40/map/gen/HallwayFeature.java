package ru.ver40.map.gen;

import ru.ver40.map.gen.FeatureGenerator.IFeature;
import ru.ver40.model.MapCell;
import ru.ver40.util.Rng;

public class HallwayFeature implements IFeature {

	@Override
	public int getDefaultProbability() {
		return 10;
	}

	@Override
	public MapCell[][] create() {
		int width = 0;
		int rng = Rng.d(10);
		if (rng <= 2) {
			width = 2;
		} else if (rng >= 9) {
			width = 4;
		} else {
			width = 3;
		}
		int len = Rng.d(3, 8, 3);	
		MapCell[][] data = new MapCell[len][width];
		// Создаем данные:
		//
		for (int r = 0; r < data.length; ++r) {
			for (int c = 0; c < data[r].length; ++c) {
				data[r][c] = new MapCell();
			}
		}
		// Если шириной 2, есть шанс что он будет ребристым
		//
		if (len == 2 && Rng.percent(100)) {
			for (int l = 0; l < len; ++l) {
				if (l % 4 == 0) {
					data[l][0] = MapCell.createWall();
				} else if (l % 2 == 0) {
					data[l][1] = MapCell.createWall();
				}
			}
		}
		return data;
	}		
}