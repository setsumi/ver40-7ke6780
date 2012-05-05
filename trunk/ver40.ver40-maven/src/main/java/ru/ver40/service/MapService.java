package ru.ver40.service;

import ru.ver40.map.FloorMap;

/**
 * Сервис, возвращающий текущую игровую карту
 * @author anon
 *
 */
public class MapService {
	
	private FloorMap cMap; 
	
	private static MapService instance;
	
	private MapService() {
		
	}
	
	public static MapService getInstance() {
		if (instance == null) {
			instance = new MapService();
		}
		return instance;
	}

	public FloorMap getMap() {
		return cMap;
	}

	public void setcMap(FloorMap cMap) {
		this.cMap = cMap;
	}
}
