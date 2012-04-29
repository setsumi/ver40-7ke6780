package ru.ver40.model;

import org.newdawn.slick.Input;

import ru.ver40.model.time.MovementTask;
import ru.ver40.service.TimeService;

/**
 * Класс для описания той самой маленькой '@' 
 * бегающей по экрану.
 * @author anon
 *
 */
public class Player extends Person implements MovementDelegate {
	
	private transient TimeService tService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -272256474863920368L;
	
	private String name;
	
	public Player(String name) {
		this.name = name;			
		tService = TimeService.getInstance();
		getSymbol().setFgColor(0xB5044B);
	}

	@Override
	public void handleInputEvent(Input input) {		
		// Передвижение
		//
		if (input.isKeyPressed(Input.KEY_NUMPAD6)) {
			tService.schedule(new MovementTask(10, PositionConstant.EAST, this));
		} else if (input.isKeyPressed(Input.KEY_NUMPAD4)) {
			tService.schedule(new MovementTask(10, PositionConstant.WEST, this));			
		} else if (input.isKeyPressed(Input.KEY_NUMPAD8)) {
			tService.schedule(new MovementTask(10, PositionConstant.NORTH, this));			
		} else if (input.isKeyPressed(Input.KEY_NUMPAD2)) {
			tService.schedule(new MovementTask(10, PositionConstant.SOUTH, this));	
		} else if (input.isKeyPressed(Input.KEY_NUMPAD7)) {
			tService.schedule(new MovementTask(10, PositionConstant.NOTRT_WEST, this));	
		} else if (input.isKeyPressed(Input.KEY_NUMPAD3)) {
			tService.schedule(new MovementTask(10, PositionConstant.SOUTH_EAST, this));	
		} else if (input.isKeyPressed(Input.KEY_NUMPAD1)) {
			tService.schedule(new MovementTask(10, PositionConstant.SOUTH_WEST, this));			
		} else if (input.isKeyPressed(Input.KEY_NUMPAD9)) {
			tService.schedule(new MovementTask(10, PositionConstant.NORTH_EAST, this));	
		}						
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
}
