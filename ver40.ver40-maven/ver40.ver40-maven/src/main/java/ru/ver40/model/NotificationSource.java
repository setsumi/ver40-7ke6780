package ru.ver40.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс, позволяющий рассылать нотификации своим слушателям.
 * 
 * @author anon
 *
 */
public abstract class NotificationSource {
	
	/**
	 * Будут ли доставляться нотификации.
	 */
	private boolean deliver;
	
	/**
	 * Список всех слушаталей данного нотификатора
	 */
	private List<NotificationListener> listeners;
	
	public NotificationSource() {
		listeners = new LinkedList<>();
	}
	
	/**
	 * Доставить определенную нотификацю всем слушателям.
	 * TODO: Подписка только на определенный тип нотификации?
	 * @param note
	 */
	public void deliverNotification(Notification note) {
		if (deliver) {
			for (NotificationListener listener : listeners) {
				note.setSource(this);
				listener.react(note);
			}
		}
	}
	
	/**
	 * Доставляет ли данный нотификатор сообщения
	 * @return
	 */
	public boolean isDeliver() {
		return deliver;
	}

	/**
	 * Установить доставку сообщений
	 * @param deliver
	 */
	public void setDeliver(boolean deliver) {
		this.deliver = deliver;
	}	
}
