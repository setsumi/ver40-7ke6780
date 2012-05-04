package ru.ver40.model.time;
/**
 * 
 * Интерфейс, который должен реализовать любой объект,
 * чтобы участвовать в потоке событий.
 * 
 * @author anon
 *
 */
public interface ITimedEntity {
		
	int performTimedAction();
	
	int getSpeed();
	
	void setSpeed(int speed);
	
	int getActionPoints();
	
	void setActionPoints(int ap);
}
