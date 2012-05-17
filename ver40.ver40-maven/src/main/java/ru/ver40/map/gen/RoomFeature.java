package ru.ver40.map.gen;

import ru.ver40.map.gen.FeatureGenerator.IFeature;
import ru.ver40.model.MapCell;
import ru.ver40.model.Monster;
import ru.ver40.service.TimeService;
import ru.ver40.util.Rng;

public class RoomFeature implements IFeature {

	private MapCell[][] data;
	private int width, height;

	@Override
	public MapCell[][] create() {
		// Создаем комнату:
		//
		width  = Rng.d(5) + 2;
		height = Rng.d(5) + 2;
		data = new MapCell[height][width];
		// Создаем данные:
		//
		for (int r = 0; r < data.length; ++r) {
			for (int c = 0; c < data[r].length; ++c) {
				data[r][c] = new MapCell();
			}
		}
		if (Rng.percent(3)) {
			data[0][0] = MapCell.createWall();
			data[height - 1][0] = MapCell.createWall();
			data[0][width - 1] = MapCell.createWall();
			data[height - 1][width - 1] = MapCell.createWall();
		}
		
		// Генерация мобов
		//
		if (Rng.percent(40)) {
			// Рой био-мух
			//
			int num = Rng.d(1, 6, 1);
			for (int n = 0; n < num; ++n) {
				Monster m = Monster.createWatcherFly();
//				TimeService.getInstance().register(m);
				data[height/2][width/2].addPerson(m);
			}
			
		}
		
		return data;	
	}

	@Override
	public int getDefaultProbability() {
		return 75;
	}

	@Override
	public boolean isMapAware() {
		return false;
	}
}