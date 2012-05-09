package ru.ver40.model;


/**
 * 
 * Дескриптор аттрибута.
 * 
 * @author anon
 *
 */
public class NamedAttribute {
	
	/**
	 * Наименование абилки
	 */
	private String name;
	
	/**
	 * Описание абилки
	 */
	private String desc;
	
	/**
	 * Численное значение абилки
	 */
	private int value;
	
	/*
	 * Геттеры и сеттеры
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}	

    /*
     * Статические методы создания внутриигровых абилок и аттрибутов
     */
    public static NamedAttribute createBlastAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("firearms, energy weapons, longe-range, explosives");
    	ret.setName("Blast");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createFightAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("melee attack and defence, hand weapons");
    	ret.setName("Fight");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createResistAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("armor, resist damage/poison/mindcontrol/conversion");
    	ret.setName("Resist");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createMoveAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("dodge, athletics, climb, run");
    	ret.setName("Move");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createHideAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("stealth, camouflage, evade, disguise");
    	ret.setName("Hide");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createFindAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("earch, track, detect, notice, perception");
    	ret.setName("Find");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createHackAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("computers, netsphere, reprogram, infect, mind transfer");
    	ret.setName("Hack");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createRecoverAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("regeneration, healing, memory, endurance");
    	ret.setName("Recover");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createBuildAbility() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("upgrade mods, repair things, change environment, take over bodies," +
    			" matter conversion, construct items");
    	ret.setName("Build");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createStructureAttribute() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("Physical damage capacity, size, mass, hit points");
    	ret.setName("Structure");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createEnergyAttribute() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("Each ability used needs energy to perform actions. More " +
    			"complex actions take more energy.");
    	ret.setName("Energy");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createGhostAttribute() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("Network status and presence. Mind damage. If your ghost is " +
    			"erased your memories are gone.");
    	ret.setName("Ghost");
    	ret.setValue(0);
    	return ret;
    }
    
    public static NamedAttribute createMemoryAttribute() {
    	NamedAttribute ret = new NamedAttribute();
    	ret.setDesc("Unit of value, generic currency in the City");
    	ret.setName("Memory");
    	ret.setValue(0);
    	return ret;
    }   
}
