package ru.ver40.model;

/**
 * Нотификация.
 * TODO: Вероятно стоит ввести типы
 * 
 * @author anon
 *
 */
public interface Notification {
	
	public void setSource(NotificationSource source);
	
	public NotificationSource getSource();
	
	
}
