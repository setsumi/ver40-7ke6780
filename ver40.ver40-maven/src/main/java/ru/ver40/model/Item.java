package ru.ver40.model;

import java.util.Map;

/**
 * Игровой предмет.
 * 
 * @author anon
 *
 */
public class Item {
	
	/**
	 * Аттрибут, который проверяет при использовании предемета
	 */
	private String attribute;
	
	/**
	 * Одноразовый ли?
	 */
	private boolean onTime;
	
	/**
	 * Вес в инвентаре
	 */
	private int weight = 10;
	
	/**
	 * Эффект при использовании
	 */
	private IEffect onUseEffect;
	
	/**
	 * Занимаемый слот
	 */	
	private ItemSlot slot = ItemSlot.WEAPON;
	
	/**
	 * Бонусы к статам
	 */
	private Map<String, Integer> statBunuces;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public boolean isOnTime() {
		return onTime;
	}

	public void setOnTime(boolean onTime) {
		this.onTime = onTime;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public IEffect getOnUseEffect() {
		return onUseEffect;
	}

	public void setOnUseEffect(IEffect onUseEffect) {
		this.onUseEffect = onUseEffect;
	}

	public ItemSlot getSlot() {
		return slot;
	}

	public void setSlot(ItemSlot slot) {
		this.slot = slot;
	}
	
	
	public Map<String, Integer> getStatBunuces() {
		return statBunuces;
	}

	public void setStatBunuces(Map<String, Integer> statBunuces) {
		this.statBunuces = statBunuces;
	}
	
	public void addStatBonus(String stat, int bonus) {
		this.statBunuces.put(stat, bonus);
	}


	public enum ItemSlot {
		WEAPON, ARMOR, USABLE, IMPLANT
	}
}
