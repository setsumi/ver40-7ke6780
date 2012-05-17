package ru.ver40.action;

import java.util.Map;

/**
 * 
 * Базовый интерфейс, описывающий действие, 
 * которое игрок или моб могут запланировать.
 *  
 * Не знаю, стоит ли это выносить в модель? 
 * 
 * @author anon
 *
 */
public interface IAction {
	
	/**
	 * Выполнить запланированное действие.
	 * @param src - выполняющий жействие объект
	 * @param tgt - объект, над которым проводится действие
	 * @param param - дополнительные параметры, или null
	 * @return - время на выполнение действия
	 */
	public void act(Object src, Object tgt, Map<String, String> param);
	
	/**
	 * 
	 * @return
	 */
	public int getEnergyCost();

}
