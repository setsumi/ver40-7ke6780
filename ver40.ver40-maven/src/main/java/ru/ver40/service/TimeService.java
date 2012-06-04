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
	 * Класс для представления временного объекта в списке.
	 */
	private class EntityRec {
		public ITimedEntity m_entity; // сам объект
		public boolean m_flag; // пометка на удаление, либо признак главного объекта

		public EntityRec(ITimedEntity entity, boolean flag) {
			m_entity = entity;
			m_flag = flag;
		}

		public EntityRec(ITimedEntity entity) {
			this(entity, false);
		}
	}

	/**
	 * Объекты, действующие по времени.
	 */
	private LinkedList<EntityRec> m_entities;

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
	 * Флаг, указавающий, что происходит обработка событий хода. Нужен для корректного
	 * вышибания попыток прямой модификации списка объектов.
	 */
	private boolean m_turnInProgress = false;

	/**
	 * Список объектов, запранированных на регистрацию в конце хода.
	 */
	private LinkedList<EntityRec> m_entitiesToAdd;

	/**
	 * Конструктор.
	 */
	public TimeService() {
		m_entities = new LinkedList<EntityRec>();
		m_entitiesToAdd = new LinkedList<EntityRec>();
	}

	/**
	 * Вернуть количество времени (условных тиков), прошедшего с начала игры.
	 */
	public long getTime() {
		return m_time;
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
	 * Возвращает состояние сервиса времени в виде строки (для отладки).
	 */
	public String getDegugInfo() {
		return "TimeService size: " + m_entities.size() + "; player: " + m_player + "@"
				+ m_player.hashCode();
	}

	/**
	 * Обработка всех временных действий за один ход (ход == одно действие главного объекта -
	 * того кем управляет пользователь).
	 * @return true если после выполнения хода, у главного тайм-объекта был запланирован
	 *         кулдаун (это нужно для выполнения сразу следующего хода, чтобы отработать этот
	 *         кулдаун), иначе возвращается false.
	 */
	public boolean turn() {
		boolean ret = false;
		if (!m_entities.isEmpty()) {
			m_turnInProgress = true;
			// Подразумевается, что первый объект это главный объект.
			EntityRec first = m_entities.getFirst();
			if (first.m_entity != m_player) {
				throw new RuntimeException("Invalid player. Expected: " + m_player
						+ ", but found: " + first.m_entity);
			}
			// Крутим время пока не наступит действие главного объекта.
			while (first.m_entity.getActDuration() > 0) {
				m_time++;
				System.out.println(Long.valueOf(m_time) + " - "
						+ first.m_entity.getActDuration());// debug
				for (EntityRec er : m_entities) {
					er.m_entity.actionTick();
					if (!er.m_flag && er.m_entity.getActDuration() == 0) {
						boolean cooldown = er.m_entity.performTimedAction();
						if (er.m_entity == m_player && cooldown) {
							ret = true;
						}
					}
				}
			}
			m_turnInProgress = false;
			// Разрегистрация запланированных во время хода на удаление объектов.
			Iterator<EntityRec> it = m_entities.iterator();
			while (it.hasNext()) {
				EntityRec er = it.next();
				if (er.m_flag) {
					if (er.m_entity == m_player) {
						m_player = null;
					}
					it.remove();
				}
			}
			// Регистрация запланированных во время хода на добавление объектов.
			for (EntityRec er : m_entitiesToAdd) {
				if (er.m_flag) {
					registerPlayer(er.m_entity);
				} else {
					registerMob(er.m_entity);
				}
			}
			m_entitiesToAdd.clear();
		}
		return ret;
	}

	/**
	 * Запланировать регистрацию временного объекта на конец хода (для избежания коллизий
	 * списка во время хода).
	 */
	public void registerSafe(ITimedEntity entity, boolean isPlayer) {
		m_entitiesToAdd.add(new EntityRec(entity, isPlayer));
	}

	/**
	 * Зарегистрировать главный временной объект (которым управляет игрок).
	 */
	public void registerPlayer(ITimedEntity entity) {
		checkTurnInProgress();
		removeEntity(entity);
		m_entities.addFirst(new EntityRec(entity));
		m_player = entity;
	}

	/**
	 * Зарегистрировать простой временной объект.
	 */
	public void registerMob(ITimedEntity entity) {
		checkTurnInProgress();
		if (!findEntity(entity)) {
			m_entities.addLast(new EntityRec(entity));
		}
	}

	/**
	 * Запланировать разрегистрацию временного объекта на конец хода (для избежания коллизий
	 * списка во время хода).
	 */
	public void unregisterSafe(ITimedEntity entity) {
		for (EntityRec er : m_entities) {
			if (er.m_entity == entity) {
				er.m_flag = true;
				break;
			}
		}
	}

	/**
	 * Разрегистрировать временной объект (прекратить его обработку).
	 */
	public void unregister(ITimedEntity entity) {
		checkTurnInProgress();
		removeEntity(entity);
		if (entity == m_player) {
			m_player = null;
		}
	}

	/**
	 * Разрегистрирует все объекты не попадающие в указанный диапазон координат. Главный объект
	 * (игрока) не трогаем.
	 */
	public void unregisterNotInArea(IntRange rx, IntRange ry) {
		checkTurnInProgress();
		Iterator<EntityRec> it = m_entities.iterator();
		while (it.hasNext()) {
			EntityRec er = it.next();
			if (er.m_entity != m_player
					&& (!rx.containsInteger(er.m_entity.getX()) || !ry
							.containsInteger(er.m_entity.getY()))) {
				it.remove();
			}
		}
	}

	/**
	 * Разрегистрирует все объекты (при переходе на другую карту например). Главный объект
	 * (игрока) не трогаем.
	 */
	public void unregisterAll() {
		checkTurnInProgress();
		// Чистим всех.
		m_entities.clear();
		// Восстанавливаем игрока.
		if (m_player != null) {
			registerPlayer(m_player);
		}
	}

	/**
	 * Внутренняя функция для вышибания если идет обработка хода. Используется для недопущения
	 * использования методов прямой модификации списка объектов во время обработки хода.
	 */
	private void checkTurnInProgress() {
		if (m_turnInProgress) {
			throw new IllegalStateException(
					"Cannot modify time object list while turn in progress.");
		}
	}

	/**
	 * Внутренняя функция удаления объекта из списка.
	 */
	private void removeEntity(ITimedEntity entity) {
		Iterator<EntityRec> it = m_entities.iterator();
		while (it.hasNext()) {
			EntityRec er = it.next();
			if (er.m_entity == entity) {
				it.remove();
				break;
			}
		}
	}

	/**
	 * Внутренняя функция поиска объекта в списке.
	 */
	private boolean findEntity(ITimedEntity entity) {
		boolean ret = false;
		for (EntityRec er : m_entities) {
			if (er.m_entity == entity) {
				ret = true;
				break;
			}
		}
		return ret;
	}

}
