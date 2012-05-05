package ru.ver40.model;

/**
 * 
 * Интерфейс, который должны реализовывать классы, которые 
 * являются поставщиками AI.
 * 
 * @author anon
 *
 */
public interface AIProvider {
	
	public int behave();
	
	public void setOwner(Actor actor);

}
