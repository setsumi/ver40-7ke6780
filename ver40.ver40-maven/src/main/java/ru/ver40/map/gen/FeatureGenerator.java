package ru.ver40.map.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.IntRange;

import ru.ver40.map.FloorMap;
import ru.ver40.model.Actor;
import ru.ver40.model.MapCell;
import ru.ver40.util.Rng;

/**
 * Генератор карт, основывающийся на концепции 'фич'
 * Фича - некий объект, который может быть размещен на карте.
 * Фичи имеют собственную координатную сетку, и должны реализовывать
 * интерфейс IFeature, и представляют собой мини-карту.
 * 
 * @author anon
 *
 */
public class FeatureGenerator implements IMapGenarator {
	
	private int mapWidth; // Карта размером 400х400
	private int mapHeight;
	
	private FloorMap map;
	
	// Число фич, используется для выборки одной из зарегистированных
	//
	private int count;
	private Map<IntRange, IFeature> features;
	private List<Rotation> rotOrder;
	private List<Position> posOrder;
	private List<IPostProcesser> postProcessers;
	
	/**
	 * Конструктор.
	 * 
	 * @param w
	 *            ширина карты
	 * @param h
	 *            высота карты
	 */
	public FeatureGenerator(int w, int h) {
		mapWidth = w;
		mapHeight = h;

		features = new HashMap<>();
		rotOrder = new ArrayList<>();
		rotOrder.addAll(Arrays.asList(Rotation.values()));
		posOrder = new ArrayList<>();
		posOrder.addAll(Arrays.asList(Position.values()));
		postProcessers = new ArrayList<>();
	}
	
	@Override
	public void generate(FloorMap map) {
		this.map = map;		
		registerFeatures();
		registerPostProcesers();
		fillWall();
		doGenerate();
	}
	
	private void registerPostProcesers() {
		registerPostProcesser(new DeadEndKiller());
		
	}

	private void registerPostProcesser(IPostProcesser proc) {
		postProcessers.add(proc);				
	}

	private void doGenerate() {
		// Сгенерировать начальную фичу
		//
		long l = System.nanoTime();
		IFeature ftr = 	null;
		do {
			ftr = getRandomFeature();
		} while (ftr.isMapAware());
		place(ftr.create(), mapWidth/2, mapHeight/2);		
		// Выбираем рандомную точку
		//
		o:for (int i = 0; i < 50000; ++i) {
			Point rnd = getRandomPoint();
			// Для данной точки попыток вставки
			//
			for (int j = 0; j < 3; ++j) {
				ftr = getRandomFeature();
				if (place(ftr.create(), rnd.x, rnd.y)) {					
					carve(MapCell.createDoor(), rnd.x, rnd.y);
					continue o;
				}			
			}			
		}	
		// Пост процессеры
		//
		for (int y = 0; y < mapHeight; ++y) {
			for (int x = 0; x < mapWidth; ++x) {
				for (IPostProcesser p : postProcessers) {
					p.process(map, x, y, mapWidth, mapHeight);
				}
			}
		}
		long t2 = System.nanoTime() - l;
		System.out.println("Generated in " + t2 / 1000000 + " ms");
	}
	
	private void fillWall() {
		for (int y = 0; y < mapHeight; ++y) {
			for (int x = 0; x < mapWidth; ++x) {
				map.setCell(MapCell.createWall(), x, y);
			}
		}
	}
	
	/**
	 * Вернуть случайную точку, принадлежащую стене какой либо комнаты
	 * 
	 * @return new Point()
	 */
	private Point getRandomPoint() {
		
		while (true) {
			int x = Rng.d(mapWidth);
			int y = Rng.d(mapHeight);
			if (x > 0 && y > 0 && y < mapHeight - 2 && x < mapWidth - 2 && map.isObstacle(x, y)) {
				if (!map.isObstacle(x + 1, y)
						|| !map.isObstacle(x, y + 1)
						|| !map.isObstacle(x - 1, y)
						|| !map.isObstacle(x, y - 1)) {
					return new Point(x, y);			
				}						
			}
		}
	}

	/**
	 * Метод пробует разместить объект на карте, в заданной позиции
	 * @param cells - объект
	 * @param x - координата X карты
	 * @param y - координата Y карты
	 */
	private boolean place(MapCell[][] cells, int x, int y) {
		// Рандомно разворачиваем объект:
		//
		Collections.shuffle(rotOrder);
		for (Rotation r : rotOrder) {
			MapCell[][] obj = rotate(cells, r);
			// Рандомно выбираем месторасположение объекта 
			// относительно заданной точки
			//
			Collections.shuffle(posOrder);
			for (Position p : posOrder) {
				if (fits(obj, x, y, p)) {
					carve(obj, x, y, p);
					return true;							
				}
			}			
		}
		return false;
	}

	/**
	 * Данный метод прорезает данный объект на карте
	 * @param obj
	 * @param x
	 * @param y
	 * @param pos
	 */
	private void carve(MapCell[][] obj, int x, int y, Position pos) {
		int width = obj[0].length;
		int height = obj.length;
		
		Point start = getPoint(x, y, width, height, pos, true);
		Point end   = getPoint(x, y, width, height, pos, false);
		
		if (start.x > end.x || start.y > end.y)
			throw new RuntimeException(start + " " + end + " \n" + prettyPring(obj)
					+ " \n" + pos + " " + new Point(x, y));
//		System.out.println("Carving near [" + x + ", " + y +"]" 
//		+ "starting at [" + start.x + ", " + start.y +"]"
//		+ "ending at [" + end.x + ", " + end.y +"]"
//		+ "on position " + pos);
		
		for (y = start.y; y <= end.y; ++y) {
			for (x = start.x; x <= end.x; ++x) {
				if (obj[y - start.y][x - start.x] != null) {
					map.setCell(obj[y - start.y][x - start.x], x, y);
					for (Actor a : obj[y - start.y][x - start.x].getPersons()) {
						a.setX(x);
						a.setY(y);
					}
				}
			}
		}		
	}
	
	private String prettyPring(MapCell[][] cells) {
		StringBuilder b = new StringBuilder();
		for (int r = 0; r < cells.length; ++r) {
			for (int c = 0; c < cells[r].length; ++c) {
				b.append(cells[r][c].isPassable() ? "." : "#");
			}
			b.append("\n");
		}
		return b.toString();
	}
	
	private void carve(MapCell cell, int x, int y) {
		map.setCell(cell, x, y);	
	}

	/**
	 * Проверить, 'Влезает' ли данный объект 
	 * на данной точке на карту
	 * @param obj
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean fits(MapCell[][] obj, int x, int y, Position pos) {
		int width = obj[0].length;
		int height = obj.length;
		
		Point start = getPoint(x, y, width, height, pos, true);
		Point end   = getPoint(x, y, width, height, pos, false);
		// Проверка на края карты
		//
		if (start.x <= 1 || start.y <= 1 || end.x >= mapWidth - 1 || end.y >= mapHeight - 1) {
			return false;
		}
		
		for (y = start.y - 1 ; y <= end.y + 1; ++y) {
			for (x = start.x - 1; x <= end.x + 1; ++x) {
				if (!map.isObstacle(x, y)) {
					// Если для данной точки есть перехлестывющая точка карты
					//
					if (hasNearPoint(x - start.x, y - start.y, obj)) {
						return false;
					}
					
				}
			}
		}
		return true;		
	}
	
	private boolean hasNearPoint(int x, int y, MapCell[][] obj) {
		for (int ry = y - 1; ry <= y + 1; ++ry) {
			for (int rx = x - 1; rx <= x + 1; ++rx) {
				// Валидность
				//
				if (!(rx < 0 || ry < 0 || rx >= obj[0].length || ry >= obj.length)) {
					if (obj[ry][rx] != null) {
						return true; 
					}
				}
			}
		}
		return false;
	}

	public Point getPoint(int x, int y, int width, int height, Position pos, boolean start) {
		int startX = 0, endX = 0;
		int startY = 0, endY = 0;
		switch (pos) {
		case TOP:
			startX = x - width/2;
			endX   = startX + width - 1;
			endY = y - 1;
			startY   = y - height;
			break;
		case BOTTOM:
			endX = x + width/2;
			startX   = endX - width + 1;
			startY = y + 1;
			endY   = y + height;
			break;
		case LEFT:
			startX = x - width;
			endX   = startX + width - 1;
			startY = y - height/2;
			endY   = startY + height - 1;
			break;
		case RIGHT:
			endX = x + width;
			startX   =endX - width + 1;	
			endY = y + height/2;
			startY   = endY - height + 1;						
			break;
		}
		return start ? new Point(startX, startY) : new Point(endX, endY);
	}
	
	

	public void registerFeatures() {
		register(new RoomFeature());
		register(new CorridorFeature());
		register(new HallwayFeature());
		register(new LargeCisternFeature());
		register(new CrossFeature());
	}
	
	private void register(IFeature feature) {
		int def = feature.getDefaultProbability();
		IntRange range = new IntRange(count, count += def);
		features.put(range, feature);
	}
	
	private IFeature getRandomFeature() {
		int num = Rng.d(count);
		for (IntRange r : features.keySet()) {
			if (r.containsInteger(num)) {
				return features.get(r);
			}
		}
		throw new RuntimeException("Can not find structural feature for" 
				+ num + " in total of " + count);
	}
	
	private MapCell[][] rotate(MapCell[][] cells, Rotation dir) {
		switch (dir) {
		case CW90:
			return rotate(cells);
		case CW180:
			return rotate(rotate(cells));
		case CW270:
			return rotate(rotate(rotate(cells)));
		default:
			return cells;	
		}
	}
	
	private MapCell[][] rotate(MapCell[][] cells) {
		int n = cells.length;
		int m = cells[0].length;
		MapCell[][] rot = new MapCell[m][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				rot[j][n - i - 1] = cells[i][j];
			}
		}
		return rot;
	}
	
	private enum Rotation {
		NONE, CW90, CW180, CW270;
	}
	
	public enum Position {
		TOP, BOTTOM, LEFT, RIGHT;
	}
	
	public class Point {
		
		public int x,y;
		
		Point() {
			this(0, 0);			
		}
		
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "[" + x + ", " + y + "]";
		}
	}
	
	/**
	 * Интерфейс который должны реализовывать все фичи.
	 * Результат - двухмерная матрица создаваемого объекта (MapCell)
	 * И дефолтная частота встречаемости данного элемента.
	 * 
	 * @author anon
	 *
	 */
	public interface IFeature {
		
		boolean isMapAware();
		
		int getDefaultProbability();
		
		MapCell[][] create();
	}
	
	public interface IMapAwareFeature extends IFeature {
		MapCell[][] create (FloorMap map);
	}
	
	/**
	 * Интерфейс, который должны реализовывать пост процессоры для карт
	 * @author anon
	 *
	 */
	public interface IPostProcesser {
		
		public void process(FloorMap map, int x, int y, int mapWidth, int mapHeight);
		
	}
}