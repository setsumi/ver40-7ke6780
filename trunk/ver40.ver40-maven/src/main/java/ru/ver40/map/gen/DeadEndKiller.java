package ru.ver40.map.gen;

import ru.ver40.map.FloorMap;
import ru.ver40.map.gen.FeatureGenerator.IPostProcesser;
import ru.ver40.model.MapCell;

public class DeadEndKiller implements IPostProcesser {

	@Override
	public void process(FloorMap map, int x, int y, int mapWidth, int mapHeight) {
		// Убить:
		// ##     ### 
		// #      # #
		// ##
		//
		if (x > 2 && y > 2 && x < mapWidth - 2 && y < mapHeight - 2) {
			if (map.isObstacle(x, y)) {
				// VERT
				//
				if (map.isObstacle(x, y + 1) && map.isObstacle(x, y - 1)) {
					if ((map.isObstacle(x + 1, y + 1) && map.isObstacle(x + 1, y - 1))
							&& (map.isObstacle(x - 1, y + 1) || map.isObstacle(x - 1, y - 1))
							&& (!map.isObstacle(x + 1, y) && !map.isObstacle(x - 1, y))) {
						map.setCell(MapCell.createDoor(), x, y);														
					}
				}
				// HOR
				//
				if (map.isObstacle(x + 1, y) && map.isObstacle(x - 1, y)) {
					if ((map.isObstacle(x + 1, y + 1) && map.isObstacle(x - 1, y + 1))
							&& (map.isObstacle(x + 1, y - 1) || map.isObstacle(x - 1, y - 1))
							&&  !map.isObstacle(x, y + 1) && !map.isObstacle(x, y - 1)) {
						map.setCell(MapCell.createDoor(), x, y);							
					}
				}
			}
		}
	}
	
}