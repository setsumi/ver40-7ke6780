package ru.ver40.model.time;

import java.io.Serializable;

/**
 * Задача занимающая некоторое количество игрового времени.
 * @author anon
 *
 */
public abstract class TimedTask implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2181403244159210890L;

	/**
	 * Длительность задачи
	 */
	private int duration;
	
	/**
	 * Конечность. Если задача бесконечна, после исполнения она будет перемещена 
	 * в конец стека задач и запланирвоана заранее.	
	 */
	private boolean finite;
	
	public TimedTask(int duration) {
		this(duration, true);
	}
	
	public TimedTask(int duration, boolean finite) {
		this.duration = duration;
		this.finite = finite;
	}
	
	/**
	 * Запланированное действие 
	 */
	public abstract void perform();
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isFinite() {
		return finite;
	}

	public void setFinite(boolean finite) {
		this.finite = finite;
	}
}
