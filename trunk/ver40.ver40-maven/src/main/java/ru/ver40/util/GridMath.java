package ru.ver40.util;

import java.util.HashSet;
import java.util.Set;

import rlforj.math.Point2I;

public class GridMath {
	
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
	
	public static Set<Point2I> filledCircle(int x0, int y0, int r) {
		Set<Point2I> ret = new HashSet<>();	
		for (int i = 0; i <= r; ++i) {
			ret.addAll(circle(x0, y0, i));
		}		
		return ret;
	}
}
