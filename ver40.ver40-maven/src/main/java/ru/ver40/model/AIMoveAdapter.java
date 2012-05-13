package ru.ver40.model;

import rlforj.los.ILosBoard;
import rlforj.math.Point2I;
import ru.ver40.map.FloorMap;

/**
 * Такой адаптер для A*, позволяющий двигаться в направлении игрока, обходя других мобов
 * @author anon
 *
 */
class AIMoveAdapter implements ILosBoard {
	
	private FloorMap map;
	private int srcX, srcY, tgtX, tgtY;		
	
	public AIMoveAdapter(FloorMap map) {
		this.map = map;	
	}
	
	public void setSrcPoint(Point2I p) {
		this.srcX = p.x;
		this.srcY = p.y;
	}
	
	public void setTgtPoint(Point2I p) {
		this.tgtX = p.x;
		this.tgtY = p.y;
	}

	@Override
	public boolean contains(int x, int y) {
		return x > 0 && y > 0 && y < 400 && x < 400;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		// Начало и конец пути всегда проходимы
		//
		if ((x == srcX && y == srcY) || (x == tgtX && y ==tgtY)) {
			return false;
		}
		MapCell cell = map.getCell(x, y);
		return !(cell.getFloor().isPassable() 
				&& (cell.getBuilding() == null ? true : cell.getBuilding().isPassable()) 
				&& cell.getPersons().isEmpty());		
	}

	@Override
	public void visit(int x, int y) {
	}		
}