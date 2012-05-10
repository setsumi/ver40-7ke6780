package ru.ver40.map.gen;

import ru.ver40.map.gen.FeatureGenerator.IFeature;
import ru.ver40.model.MapCell;
import ru.ver40.util.Rng;

public class CrossFeature implements IFeature {

	@Override
	public int getDefaultProbability() {
		return 10;
	}

	@Override
	public MapCell[][] create() {		
		int len = Rng.d(3, 7, 2);
		boolean wide = Rng.percent(5);
		MapCell[][] data = new MapCell[len][len];
		// Vertical
		//
		int m = (int) Math.ceil(len/2);
		for (int r = 0; r < len; ++r) {
			data[r][m  - (wide ? 2 : 1)] = MapCell.createWall();
			data[r][  m  ] = new MapCell();
			if (wide) {
				data[r][m + 1] = new MapCell();
				data[r][m - 1] = new MapCell();
			}
			data[r][m + (wide ? 2 : 1)] = MapCell.createWall();
		}
		// Horizontal
		//
		m = len/2;
		for (int c = 0; c < len; ++c) {
			if (data[m - (wide ? 2 : 1)][c] == null)
				data[m - (wide ? 2 : 1)][c] = MapCell.createWall();
			data[  m  ][c] = new MapCell();
			if (wide) {
				data[m + 1][c] = new MapCell();
				data[m - 1][c] = new MapCell();
			}
			if (data[m + (wide ? 2 : 1)][c] == null)
				data[m + (wide ? 2 : 1)][c] = MapCell.createWall();
		}
		return data;
	}

	@Override
	public boolean isMapAware() {
		return false;
	}
}
