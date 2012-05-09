package ru.ver40.model;

import ru.ver40.util.Rng;

public class DamageEffect implements IEffect {
	
	public int dmg;
	
	@Override
	public void affect(MapCell target, Actor user) {
		
		for (Actor a : target.getPersons()) {
			a.damage(Rng.d(10));						
		}
	}	
}
