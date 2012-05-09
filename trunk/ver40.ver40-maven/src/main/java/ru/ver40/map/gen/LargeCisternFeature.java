package ru.ver40.map.gen;

import java.util.Set;

import rlforj.math.Point2I;
import ru.ver40.map.gen.FeatureGenerator.IFeature;
import ru.ver40.model.MapCell;
import ru.ver40.util.GridMath;
import ru.ver40.util.Rng;

public class LargeCisternFeature implements IFeature {

	@Override
	public int getDefaultProbability() {
		return 2;
	}

	@Override
	public MapCell[][] create() {
		int rad = Rng.d(4, 6, 3);
		MapCell[][] cells = new MapCell[rad][rad];
		Set<Point2I> ftr = GridMath.filledCircle(rad/2, rad/2, rad/2);
		for (int r = 0; r < cells.length; ++r) {
			for (int c = 0; c < cells[r].length; ++c) {
				if (ftr.contains(new Point2I(c, r))) {
					cells[r][c] = new MapCell();
				} else {
					cells[r][c] = MapCell.createWall();
				}
			}
		}
		for (int r = 1; r < cells.length - 1; ++r) {
			for (int c = 1; c < cells[r].length - 1; ++c) {
				if (!cells[r][c].isPassable()) {
					int t = 0;
					if (cells[r + 1][c + 1].isPassable()) {
						t++;
					}
					if (cells[r + 1][c].isPassable()) {
						t++;
					}
					if (cells[r][c + 1].isPassable()) {
						t++;
					}
					if (cells[r + 1][c - 1].isPassable()) {
						t++;
					}
					if ( cells[r - 1][c - 1].isPassable()) {
						t++;
					}
					if (cells[r - 1][c].isPassable()) {
						t++;
					}
					if (cells[r - 1][c + 1].isPassable()) {
						t++;
					}
					if (t >= 6) {									
						cells[r][c] = new MapCell();
						
					}
				}
			}
		}			
		return cells; 
	}
	
}