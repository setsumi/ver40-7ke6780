package ru.ver40.model;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import ru.ver40.TheGame;
import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;
import ru.ver40.system.StateManager;
import ru.ver40.util.GameLog;
import ru.ver40.util.Rng;

/**
 * Класс для описания той самой маленькой '@' 
 * бегающей по экрану.
 * @author anon
 *
 */
public class Player extends Actor implements KeyListener {
	
	private transient TimeService tService;
	
	private transient MapService mService;
	
	private int[] codes = { Input.KEY_NUMPAD1, Input.KEY_NUMPAD2, Input.KEY_NUMPAD3, Input.KEY_NUMPAD4,
			Input.KEY_NUMPAD6, Input.KEY_NUMPAD7, Input.KEY_NUMPAD8, Input.KEY_NUMPAD9 };
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -272256474863920368L;
	
	private String name;
	private int keyCode;
	
	public Player(String name) {
		this.name = name;
		this.setPassable(true);
		tService = TimeService.getInstance();
		mService = MapService.getInstance();
		getSymbol().setFgColor(0xB5044B);
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
		}
		return 0;
	}
	
	private int move(FloorMap map, int x, int y) {
		MapCell cell = map.translateActor(this, x , y);
		if (cell != null && !cell.getPersons().isEmpty()) {
			Actor a = cell.getPersons().get(0);
			int dmg = Rng.d(8);
			a.setStructure(a.getStructure() - dmg);
			GameLog.getInstance().log(
					"Player hit monster for " + dmg + " damage.");
			GameLog.getInstance().log("Monster has " + a.getStructure() + " hp");
		}
		return 10;				
	}

	@Override
	public void setInput(Input input) {
	}

	@Override
	public boolean isAcceptingInput() {
		return tService.getCurrentActor() == this;
	}

	@Override
	public void inputEnded() {	
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public void keyPressed(int key, char c) {
		if (provokeTimedAction(key)) {
			this.keyCode = key;
			tService.tick();			
		} else {
			if (key == Input.KEY_K) {
				
			}
		}
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
	public void keyReleased(int key, char c) {
	}	
}
