package ru.ver40.map.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.IntRange;

import ru.ver40.map.FloorMap;
import ru.ver40.model.Building;
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
	
	private int mapWidth = 400; // Карта размером 400х400
	private int mapHeight = 400;
	
	private FloorMap map;
	
	// Число фич, используется для выборки одной из зарегистированных
	//
	private int count;
	private Map<IntRange, IFeature> features;
	private List<Rotation> rotOrder;
	private List<Position> posOrder;
	private List<IPostProcesser> postProcessers;
	
	public FeatureGenerator() {
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
		IFeature ftr = 
//				getRandomFeature();
				new RoomFeature();
		System.out.println(place(ftr.create(), mapWidth/2, mapHeight/2));		
		// Выбираем рандомную точку
		//
		o:for (int i = 0; i < 10000; ++i) {
			Point rnd = getRandomPoint();
			if (i % 100 == 0)
				System.out.println(i/100);
			// Для данной точки попыток вставки
			//
			for (int j = 0; j < 3; ++j) {
				ftr = getRandomFeature();
				if (place(ftr.create(), rnd.x, rnd.y)) {
					MapCell door = new MapCell();
					Building dr = new Building();
					dr.setPassable(true);
					dr.getSymbol().setSymbol('+');
					door.setBuilding(dr);
					carve(door, rnd.x, rnd.y);
					continue o;
				}			
			}			
		}	
		// Пост процессеры
		//
		for (int y = 0; y < mapHeight; ++y) {
			for (int x = 0; x < mapWidth; ++x) {
				for (IPostProcesser p : postProcessers) {
					p.process(map, x, y);
				}
			}
		}
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
	 * @return
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
				map.setCell(obj[y - start.y][x - start.x], x, y);
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
					return false;
				}
			}
		}
		return true;		
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
	
	

	private void registerFeatures() {
		register(new RoomFeature());
		register(new CorridorFeature());
		register(new HallwayFeature());
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
	
	private MapCell createDoor() {
		MapCell door = new MapCell();
		Building dr = new Building();
		dr.setPassable(true);
		dr.getSymbol().setSymbol('+');
		door.setBuilding(dr);
		return door;
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
		
		int getDefaultProbability();
		
		MapCell[][] create();		

	}
	
	/**
	 * Интерфейс, который должны реализовывать пост процессоры для карт
	 * @author anon
	 *
	 */
	public interface IPostProcesser {
		
		public void process(FloorMap map, int x, int y);
		
	}
	
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
			return data;
		}

		@Override
		public int getDefaultProbability() {
			return 75;
		}		
	}
	
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
	}
	
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
			return data;
		}
		
	}
	
	public class DeadEndKiller implements IPostProcesser {

		@Override
		public void process(FloorMap map, int x, int y) {
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
								&&  !map.isObstacle(x + 1, y) && !map.isObstacle(x - 1, y)) {
							map.setCell(createDoor(), x, y);														
						}
					}
					// HOR
					//
					if (map.isObstacle(x + 1, y) && map.isObstacle(x - 1, y)) {
						if ((map.isObstacle(x + 1, y + 1) && map.isObstacle(x - 1, y + 1))
								&& (map.isObstacle(x + 1, y - 1) || map.isObstacle(x - 1, y - 1))
								&&  !map.isObstacle(x, y + 1) && !map.isObstacle(x, y - 1)) {
							map.setCell(createDoor(), x, y);							
						}
					}
				}
			}
		}
		
	}
}