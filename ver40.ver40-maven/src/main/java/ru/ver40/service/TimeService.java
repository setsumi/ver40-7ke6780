package ru.ver40.service;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang.math.IntRange;



/**
 * Сервис управления игровым временем для зарегистрированных в нём объектов. Синглтон.
 * 
 * Сначала через action_блаблабла() объектов, устанавливается запланированное действие, его
 * продолжительность и другие параметры. Затем в turn() идет отсчет тиков всех объектов в
 * обратную сторону и когда достигает нуля в каком-либо объекте, значит пора действовать и
 * выполняется его performTimedAction(). После выполнения performTimedAction() главного объекта
 * и остальных, у кого действие в то же время, ход заканчивается и ожидается ввод пользователя
 * для нового действия главного объекта.
 * 
 * Главный объект, по времени хода которого идет отсчет, находится всегда в самом начале списка
 * (таким образом ходит первый, если на этот момент попадает действие нескольких объектов).
 */
public class TimeService {

	private static TimeService m_instance = null;

	public static TimeService getInstance() {
		if (m_instance == null) {
			m_instance = new TimeService();
		}
		return m_instance;
	}

	/**
	 * Объекты, действующие по времени.
	 */
	private LinkedList<ITimedEntity> m_entities;

	/**
	 * Главный объект, управляемый игроком и управляющий временем.
	 * Это дубликат для сохранения, а активный главный объект находится на первом месте списка
	 * объектов вместе со всеми остальными.
	 */
	private ITimedEntity m_player = null;

	/**
	 * Отсчет игрового времени (пока для понта и отладки).
	 */
	private long m_time = 0;

	/**
	 * Конструктор.
	 */
	public TimeService() {
		m_entities = new LinkedList<ITimedEntity>();
	}

	/**
	 * Вернуть количество времени (условных тиков), прошедшего с начала игры.
	 */
	public long getTime() {
		return m_time;
	}

	/**
	 * Обработка всех временных действий за один ход (ход == одно действие главного объекта -
	 * того кем управляет пользователь).
	 */
	public void turn() {
		if (!m_entities.isEmpty()) {
			// Подразумевается, что первый объект это главный объект.
			ITimedEntity first = m_entities.getFirst();
			if (first != m_player) {
				throw new RuntimeException("Invalid player. Expected: " + m_player
						+ ", but found: " + first);
			}
			while (first.getActDuration() > 0) {
				m_time++;
				System.out.println(Long.valueOf(m_time) + " - " + first.getActDuration());// debug
				for (ITimedEntity i : m_entities) {
					i.actionTick();
					if (i.getActDuration() == 0) {
						i.performTimedAction();
					}
				}
			}
		}
	}

	/**
	 * Вернуть главный объект, управляющий ходами в игре (первый в списке).
	 * 
	 * @return главный объект или null, если он не зарегистрирован
	 */
	public ITimedEntity getPlayer() {
		return m_player;
	}

	/**
	 * Зарегистрировать главный временной объект (которым управляет игрок).
	 */
	public void registerPlayer(ITimedEntity entity) {
		m_entities.remove(entity);
		m_entities.addFirst(entity);
		m_player = entity;
	}

	/**
	 * Зарегистрировать простой временной объект.
	 */
	public void registerMob(ITimedEntity entity) {
		if (!m_entities.contains(entity)) {
			m_entities.addLast(entity);
		}
	}

	/**
	 * Разрегистрировать временной объект (прекратить его обработку).
	 */
	public void unregister(ITimedEntity entity) {
		m_entities.remove(entity);
		if (entity == m_player) {
			m_player = null;
		}
	}

	/**
	 * Разрегистрирует все объекты не попадающие в указанный диапазон координат.
	 * Главный объект (игрока) не трогаем.
	 */
	public void unregisterNotInArea(IntRange rx, IntRange ry) {
		Iterator<ITimedEntity> it = m_entities.iterator();
		while (it.hasNext()) {
			ITimedEntity entity = it.next();
			if (entity != m_player
					&& (!rx.containsInteger(entity.getX()) || !ry.containsInteger(entity
							.getY()))) {
				it.remove();
			}
		}
	}

	/**
	 * Разрегистрирует все объекты (при переходе на другую карту например).
	 * Главный объект (игрока) не трогаем.
	 */
	public void unregisterAll() {
		// Чистим всех.
		m_entities.clear();
		// Восстанавливаем игрока.
		if (m_player != null) {
			registerPlayer(m_player);
		}
	}

}
