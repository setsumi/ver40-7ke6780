package ru.ver40.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ru.ver40.model.time.TimedTask;

/**
 * Сервис, реализующий очередь задач. 
 * Является серриализуемым.
 * @author anon
 *
 */
public class TimeService implements Serializable {
	
	private static final long serialVersionUID = -5970618929616925733L;
	
	private transient int sessionTicksLeft;
	
	private int totalTicksLeft;
	
	private LinkedList<TaskWrapper> tasks;
	
	private static transient TimeService instance;
	
	public static TimeService getInstance( ) {
		if (instance == null) {
			instance = new TimeService();
		}
		return instance;
	}
	
	private TimeService() {		
		tasks = new LinkedList<TaskWrapper>();
	}
	
	public void schedule(TimedTask task) {
		tasks.addLast(new TaskWrapper(task, task.getDuration()));
	}
	
	public void remove(TimedTask task) {
		Iterator<TaskWrapper> i = tasks.iterator();
		while (i.hasNext()) {
			TaskWrapper next = i.next();
			if (next.getTask() == task) {
				i.remove();
				break;
			}
			
		}
	}
	
	public List<TimedTask> getTasks() {
		List<TimedTask> ret = new ArrayList<TimedTask>();
		for (TaskWrapper w : tasks) {
			ret.add(w.getTask());
		}
		return Collections.unmodifiableList(ret);
	}
	
	public void tick() {
		sessionTicksLeft++;
		totalTicksLeft++;
		Iterator<TaskWrapper> i = tasks.iterator();
		while (i.hasNext()) {
			TaskWrapper next = i.next();
			if (next.isCompleted()) {
				next.getTask().perform();
				i.remove();
			}
		}
	}
	
	private class TaskWrapper {
		private TimedTask task;
		private int elapsed;
		
		public TaskWrapper(TimedTask task, int elapsed) {
			this.task = task;
			this.elapsed = elapsed;
		}
		
		public boolean isCompleted() {
			elapsed--;
			return elapsed == 0;
		}

		public TimedTask getTask() {
			return task;
		}		
	}

	public int getSessionTicksLeft() {
		return sessionTicksLeft;
	}

	public void setSessionTicksLeft(int sessionTicksLeft) {
		this.sessionTicksLeft = sessionTicksLeft;
	}

	public int getTotalTicksLeft() {
		return totalTicksLeft;
	}

	public void setTotalTicksLeft(int totalTicksLeft) {
		this.totalTicksLeft = totalTicksLeft;
	}

	public void setTasks(LinkedList<TaskWrapper> tasks) {
		this.tasks = tasks;
	}		
}
