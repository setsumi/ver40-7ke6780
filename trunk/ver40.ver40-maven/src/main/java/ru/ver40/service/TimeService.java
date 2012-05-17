package ru.ver40.service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang.math.IntRange;

import ru.ver40.model.time.ITimedEntity;

/**
 * Сервис, реализующий очередь задач.
 * 
 * Алгоритм работы: 
 * Каждая сущность (который может являеться игрок, монстр, 
 * или зарегистированный в игре класс) реализует интерфейс ITimedEntity.
 * 
 * Контрактом данного интерфейса является метод schedule, возвращающий
 * число тиков на осуществленеие данного события (int). 
 * 
 * Данный интерфейс так-же позволяет задавать события которые будут
 * запланированы одноразово (setFinite(true)) или будут повторяться через
 * фиксированный промежуток времени (setFiniti(false)). Примером
 * одноразового события может служать действия игрока или NPC по перемещению
 * или атаке, примером же бесконечного события может являться 
 * смена циклов дня и ночи.
 * 
 * Является серриализуемым.
 * @author anon
 *
 */
public class TimeService implements Serializable {
	
	private static final long serialVersionUID = -5970618929616925733L;
	
	private LinkedList<ITimedEntity> entities;
	
	private static TimeService instance;
	
	public static TimeService getInstance( ) {
		if (instance == null) {
			instance = new TimeService();
		}
		return instance;
	}
	
	public void register(ITimedEntity entity) {
		if (!entities.contains(entity)) {
			entity.setActionPoints(0);
			entities.addLast(entity);
		}
	}
	
	public void unregister(ITimedEntity entity) {
		entities.remove(entity);
	}
	
	public void tick() {
		if (entities.size() > 0) {
			ITimedEntity current = entities.pollFirst();
			current.setActionPoints(
					current.getActionPoints() + current.getSpeed());
			while (current.getActionPoints() > 0) {
				current.setActionPoints(current.getActionPoints()
						- current.performTimedAction());
			}
			entities.addLast(current);			
		}
	}
	
	public ITimedEntity getCurrentActor() {
		return entities.peekFirst();
	}
	
	private TimeService() {		
		entities = new LinkedList<ITimedEntity>();
	}

	/**
	 * Разрегистрирует все объекты не попадающие в указанный диапазон координат
	 * на карте.
	 */
	public void unregisterNotInArea(IntRange rx, IntRange ry) {
		Iterator<ITimedEntity> it = entities.iterator();
		while (it.hasNext()) {
			ITimedEntity entity = it.next();
			if (!rx.containsInteger(entity.getX())
					|| !ry.containsInteger(entity.getY())) {
				it.remove();
			}
		}
	}

}
