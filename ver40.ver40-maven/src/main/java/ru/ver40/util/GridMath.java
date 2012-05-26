package ru.ver40.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rlforj.math.Point2I;
import ru.ver40.map.FloorMap;
import ru.ver40.model.GObject;

/**
 * Стандартные операции над гридой
 * 
 * @author anon
 *
 */
public class GridMath {
	
	/**
	 * Набор точке, образующих окружность
	 * @param x0 - x
	 * @param y0 - y
	 * @param r - радиус
	 * @return окружность
	 */
	public static Set<Point2I> circle(int x0, int y0, int r) {		
		Set<Point2I> ret = new HashSet<>();		
		int f = 1 - r;
		int ddF_x = 1;
		int ddF_y = -2 * r;
		int x = 0;
		int y = r;

		ret.add(new Point2I(x0, y0 + r));
		ret.add(new Point2I(x0, y0 - r));
		ret.add(new Point2I(x0 + r, y0));
		ret.add(new Point2I(x0 - r, y0));

		while (x < y) {
			if (f >= 0) {
				y--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			ret.add(new Point2I(x0 + x, y0 + y));
			ret.add(new Point2I(x0 - x, y0 + y));
			ret.add(new Point2I(x0 + x, y0 - y));
			ret.add(new Point2I(x0 - x, y0 - y));
			ret.add(new Point2I(x0 + y, y0 + x));
			ret.add(new Point2I(x0 - y, y0 + x));
			ret.add(new Point2I(x0 + y, y0 - x));
			ret.add(new Point2I(x0 - y, y0 - x));
		}		
		return ret;
	}
	
	/**
	 * Набор точек, образующих круг
	 * @param x0
	 * @param y0
	 * @param r
	 * @return
	 */
	public static Set<Point2I> filledCircle(int x0, int y0, int r) {
		Set<Point2I> ret = new HashSet<>();	
		for (int i = 0; i <= r; ++i) {
			ret.addAll(circle(x0, y0, i));
		}		
		return ret;
	}
	
	/**
	 * Равномерное заполняет карту, начиная с назраченной точки объектами, список который перечислен 
	 * @param map
	 * @param x0
	 * @param y0
	 * @param obj
	 */
	public static void mapFill(FloorMap map, int x0, int y0, List<GObject> obj) {
		
	}
	
	public interface IDepthProvider {
		
		public int getMaxDepth(int x, int y);
		
		public int getDepth(int x, int y);
		
	}
	
//	private class MapActorDepthAdapter implements IDepthProvider {
//		
//		private FloorMap map;
//
//		public MapActorDepthAdapter(FloorMap map) {
//			this.map = map;
//		}
//
//		@Override
//		public int getMaxDepth(int x, int y) {
//			return 1; // 1 Actor per cell
//		}
//
//		@Override
//		public int getDepth(int x, int y) {
//			return map.getCell(x, y).getPersons().isEmpty() ? 0 : 1;
//		}		
//	}	
}
