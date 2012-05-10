package ru.ver40.map.gen;

import ru.ver40.map.gen.FeatureGenerator.IFeature;
import ru.ver40.model.MapCell;
import ru.ver40.util.Rng;

public class CorridorFeature implements IFeature {
	
	private MapCell[][] data;
	private int len;		

	@Override
	public MapCell[][] create() {
		int lenSize = Rng.d(10);			
		len = 0;
		if (lenSize < 5) {
			// Короткий коридор таких большинство
			//
			len = Rng.d(3) + 3;
		} else if (lenSize < 8) {
			// Средний
			//
			len = Rng.d(10) + 2;
		} else {
			// Длинный
			//
			len = Rng.d(30) + 1;
		}
		data = new MapCell[1][len];
		for (int l = 0; l < data[0].length; ++l) {
			data[0][l] = new MapCell();
		}
		return data;					
	}

	@Override
	public int getDefaultProbability() {
		return 25;
	}

	@Override
	public boolean isMapAware() {
		return false;
	}
}