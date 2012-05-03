package ru.ver40.model;

/**
 * Интерфейс, который должны реализовывать все слушатели внутриигровых нотификаций.
 * 
 * @author anon
 *
 */
public interface NotificationListener {
	
	public void react(Notification note);

}
