package ru.ver40.model;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;
import ru.ver40.service.TimeService;

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
	private Input input;
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
			map.translateActor(this, getX() , getY() - 1);			
			return 10;
		case Input.KEY_NUMPAD2:
			map.translateActor(this, this.getX() , this.getY() + 1);	
			return 10;
		case Input.KEY_NUMPAD6:
			map.translateActor(this, this.getX() + 1 , this.getY());
			return 10;
		case Input.KEY_NUMPAD4:
			map.translateActor(this, this.getX() - 1 , this.getY());
			return 10;
		case Input.KEY_NUMPAD9:
			map.translateActor(this, this.getX() + 1 , this.getY() - 1);
			return 10;
		case Input.KEY_NUMPAD3:
			map.translateActor(this, this.getX() + 1 , this.getY() + 1);
			return 10;
		case Input.KEY_NUMPAD1:
			map.translateActor(this, this.getX() - 1 , this.getY() + 1);
			return 10;
		case Input.KEY_NUMPAD7:
			map.translateActor(this, this.getX() - 1 , this.getY() - 1);
			return 10;
		}
		return 0;
	}

	@Override
	public void setInput(Input input) {
		this.input = input;		
	}

	@Override
	public boolean isAcceptingInput() {
//		return tService.getCurrentActor() == this;
		return true;
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
