package ru.ver40.model;

import org.newdawn.slick.Input;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.util.RoleSystem;

/**
 * Класс для описания той самой маленькой '@' 
 * бегающей по экрану.
 * @author anon
 *
 */
public class Player extends Actor {
	
	private transient TimeService tService;
	
	private transient MapService mService;
	
	private int[] codes = { Input.KEY_NUMPAD1, Input.KEY_NUMPAD2,
			Input.KEY_NUMPAD3, Input.KEY_NUMPAD4, Input.KEY_NUMPAD6,
			Input.KEY_NUMPAD7, Input.KEY_NUMPAD8, Input.KEY_NUMPAD9 };
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -272256474863920368L;
	
	private String name;
	private int keyCode;
	
	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public Player(String name) {
		this.name = name;
		this.setPassable(true);
		tService = TimeService.getInstance();
		mService = MapService.getInstance();
		getSymbol().setFgColor(0xB5044B);
		setBlast(10);
		setResist(10);
		setStructure(10);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int performTimedAction() {
		FloorMap map = mService.getMap();
		switch (keyCode) {
		case Input.KEY_Q:
		case Input.KEY_NUMPAD8:			
			return move(map,  getX() , getY() - 1);			
		case Input.KEY_NUMPAD2:
			return move(map,  getX() , getY() + 1);
		case Input.KEY_NUMPAD6:
			return move(map,  getX() + 1 , getY());
		case Input.KEY_NUMPAD4:
			return move(map,  getX() - 1 , getY());			
		case Input.KEY_NUMPAD9:
			return move(map,  getX() + 1 , getY() - 1);			
		case Input.KEY_NUMPAD3:
			return move(map,  getX() + 1 , getY() + 1);
		case Input.KEY_NUMPAD1:
			return move(map,  getX() - 1 , getY() + 1);
		case Input.KEY_NUMPAD7:
			return move(map,  getX() - 1 , getY() - 1);
		case Input.KEY_NUMPAD5:			
			return 10;			
		}
		return 0;
	}
	
	private int move(FloorMap map, int x, int y) {
		MapCell cell = map.translateActor(this, x , y);
		if (cell != null && !cell.getPersons().isEmpty()) {
			RoleSystem.testBlast(this, cell.getPersons().get(0));		
		}
		return 10;				
	}

	
	private boolean provokeTimedAction(int key) {
		for (int x : codes) {
			if (x == key) {
				return true;
			}
		}
		return false;
	}	
	
	@Override
	public String toString() {
		return "Player";
	}
}
