package ru.ver40.model;

import org.newdawn.slick.Input;

import ru.ver40.map.FloorMap;
import ru.ver40.service.MapService;

/**
 * Класс для описания той самой маленькой '@' 
 * бегающей по экрану.
 * @author anon
 *
 */
public class Player extends Person implements MovementDelegate {
	
	private transient MapService mService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -272256474863920368L;
	
	private String name;
	
	public Player(String name) {
		this.name = name;	
		mService = MapService.getInstance();
		getSymbol().setFgColor(0xB5044B);
	}



	@Override
	public void handleInputEvent(Input input) {
		FloorMap map = mService.getcMap();
		// Передвижение
		//
		if (input.isKeyDown(Input.KEY_NUMPAD6)) {
//			m_viewPos.translate(1, 0);	
			map.translatePerson(this, getX() + 1 , getY());
		} else if (input.isKeyDown(Input.KEY_NUMPAD4)) {
//			m_viewPos.translate(-1, 0);
			map.translatePerson(this, getX() - 1 , getY());
		} else if (input.isKeyDown(Input.KEY_NUMPAD8)) {
//			m_viewPos.translate(0, -1);
			map.translatePerson(this, getX() , getY() - 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD2)) {
//			m_viewPos.translate(0, 1);
			map.translatePerson(this, getX() , getY() + 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD7)) {
//			m_viewPos.translate(-1, -1);
			map.translatePerson(this, getX() - 1 , getY() - 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD3)) {
//			m_viewPos.translate(1, 1);
			map.translatePerson(this, getX() + 1 , getY() + 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD1)) {
//			m_viewPos.translate(-1, 1);
			map.translatePerson(this, getX() - 1 , getY() + 1);
		} else if (input.isKeyDown(Input.KEY_NUMPAD9)) {
//			m_viewPos.translate(1, -1);
			map.translatePerson(this, getX() + 1 , getY() - 1);
		}						
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
}
