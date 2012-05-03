package ru.ver40.model;

import org.newdawn.slick.Input;

/**
 * Данный интерфейс должны реализовывать объекты игровой модели,
 * которые получают инпут со стороны пользователя 
 * @author anon
 *
 */
public interface MovementDelegate {
	
	void handleInputEvent(Input input);

}
