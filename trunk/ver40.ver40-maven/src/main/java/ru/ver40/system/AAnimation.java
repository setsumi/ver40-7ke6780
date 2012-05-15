package ru.ver40.system;

import org.newdawn.slick.Graphics;

/**
 * Базовый класс анимаций.
 * 
 */
public abstract class AAnimation {
	// Постоянное время фрейма или постоянное время всей анимации.
	protected static enum Type {
		FIXED_FRAME_TIME, FIXED_ANIMATION_TIME;
	}

	protected int m_numFrames; // Количество фреймов.
	protected int m_currFrame; // Номер текущего фрейма.

	private int m_frameTime; // Продолжительность одного фрейма.
	private int m_trackTime; // Счетчик времени текущего фрейма.
	private boolean m_infinite; // Флаг бесконечной анимации.
	private boolean m_stopped; // Флаг остановки анимации.

	/**
	 * Конструктор.
	 */
	public AAnimation(Type type, int numFrames, int duration, boolean infinite) {
		m_numFrames = numFrames;
		m_frameTime = type == Type.FIXED_FRAME_TIME ? duration : duration
				/ numFrames;
		m_currFrame = 0;
		m_trackTime = 0;
		m_infinite = infinite;
		m_stopped = true;
	}

	public void start() {
		m_trackTime = 0;
		m_stopped = false;
	}

	public void stop() {
		m_stopped = true;
	}

	public boolean isStopped() {
		return m_stopped;
	}

	public boolean isInfinite() {
		return m_infinite;
	}

	public void update(int delta) {
		if (!isStopped()) {
			m_trackTime += delta;
			if (m_trackTime >= m_frameTime) {
				m_trackTime = 0;
				m_currFrame++;
				if (m_currFrame == m_numFrames) {
					if (m_infinite) {
						start();
					} else {
						stop();
					}
				}
			}
		}
	}

	/**
	 * Вызов рендера анимации.
	 */
	public void draw(Graphics g) {
		if (!isStopped()) {
			onDraw(g);
		}
	}

	/**
	 * Рендер анимации.
	 */
	protected abstract void onDraw(Graphics g);

}
