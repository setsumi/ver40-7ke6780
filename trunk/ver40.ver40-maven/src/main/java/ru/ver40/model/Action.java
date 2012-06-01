package ru.ver40.model;

/**
 * Действия, совершаемые актерами.
 * 
 */
public class Action {

	/**
	 * Базовая продолжительность одного движения.
	 */
	public static final int BASE_VALUE = 10;

	/**
	 * Идентификаторы действий.
	 */
	public static final int WAIT = 0;
	public static final int MOVE = 10;
	public static final int USEOBJECT = 20;
	public static final int HIT = 30;
	public static final int KICK = 40;
	public static final int SHOOT = 50;

	private int m_action; // идентификатор действия
	private int m_duration; // продолжительность выполнения действия
	private int m_cooldown; // продолжительность бездействия после совершения действия

	/**
	 * Конструктор по умолчанию.
	 */
	public Action() {
		this(WAIT, BASE_VALUE, 0);
	}

	/**
	 * Конструктор.
	 * 
	 * @param action - идентификатор действия
	 * @param duration - продолжительность выполнения действия
	 * @param cooldown - продолжительность бездействия после совершения действия
	 */
	public Action(int action, int duration, int cooldown) {
		m_action = action;
		m_duration = duration;
		m_cooldown = cooldown;
	}

	/**
	 * Уменьшение продолжительности на единицу как результат прошедшего кванта времени.
	 */
	public void Tick() {
		m_duration--;
	}

	/**
	 * Присвоить все параметры из другого класса действия.
	 */
	public void assign(Action action) {
		m_action = action.getAction();
		m_duration = action.getDuration();
		m_cooldown = action.getCooldown();
	}

	// ==============================================
	// Геттеры и сеттеры.
	// ==============================================
	public int getAction() {
		return m_action;
	}

	public void setAction(int action) {
		m_action = action;
	}

	public int getDuration() {
		return m_duration;
	}

	public void setDuration(int duration) {
		m_duration = duration;
	}

	public int getCooldown() {
		return m_cooldown;
	}

	public void setCooldown(int cooldown) {
		m_cooldown = cooldown;
	}

}
