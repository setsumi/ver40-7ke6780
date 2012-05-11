package ru.ver40.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import ru.ver40.model.time.ITimedEntity;
import ru.ver40.system.util.GameLog;

/**
 * Персонаж игрового мира
 * @author anon
 *
 */
public abstract class Actor extends GObject implements ITimedEntity {	
	
	/**
	 * Поддержка изменения пропертей
	 */
	private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
	
	// Аттрибуты
	//
	private NamedAttribute structure = NamedAttribute.createStructureAttribute();
	
	private NamedAttribute energy    = NamedAttribute.createEnergyAttribute();
	
	private NamedAttribute ghost     = NamedAttribute.createGhostAttribute();
	
	private NamedAttribute memory    = NamedAttribute.createMemoryAttribute();
	
	//Абилки
	//
	private NamedAttribute blast   = NamedAttribute.createBlastAbility();
	 
	private NamedAttribute fight   = NamedAttribute.createFightAbility();
	
	private NamedAttribute resist  = NamedAttribute.createResistAbility();
	
	private NamedAttribute move    = NamedAttribute.createMoveAbility();
	
	private NamedAttribute hide    = NamedAttribute.createHideAbility();
	
	private NamedAttribute find    = NamedAttribute.createFindAbility();
	
	private NamedAttribute recover = NamedAttribute.createRecoverAbility();
	
	private NamedAttribute build   = NamedAttribute.createBuildAbility();
	
	// Поддерживаемые пропертЯ
	//
	public static final String PROP_STRUCTURE = "STRUCTURE";
	public static final String PROP_ENERGY    = "ENERGY";
	public static final String PROP_GHOST     = "GHOST";
	public static final String PROP_MEMORY    = "MEMORY";
	
	public static final String PROP_BLAST     = "BLAST";
	public static final String PROP_FIGHT     = "FIGHT";
	public static final String PROP_RESIST    = "RESIST";
	public static final String PROP_MOVE      = "MOVE";
	public static final String PROP_HIDE      = "HIDE";
	public static final String PROP_FIND      = "FIND";
	public static final String PROP_RECOVER   = "RECOVER";
	public static final String PROP_BUILD     = "BUILD";
	
	/**
	 * Координаты на игровой карте
	 */
	private int x, y;
	
	/**
	 * Относительная скорость
	 * 10 - значение по умолчанию
	 */
	private int speed = 10;

	private int actionPoints;	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	private static final long serialVersionUID = 8472085921088558199L;
	
	public Actor() {
		getSymbol().setSymbol('@');
		structure.setValue(10);
		setPassable(false);
	}

	public int getStructure() {
		return structure.getValue();
	}

	public void setStructure(int hp) {
		if (hp <= 0) {
			die();
		}
		mPcs.firePropertyChange(PROP_STRUCTURE, getStructure(), hp);
		structure.setValue(hp);
	}
	
	public void damage(int amount) {
		if (getStructure() - amount <= 0) {
			die();
		}
		mPcs.firePropertyChange(PROP_STRUCTURE, getStructure(), getStructure() - amount);
		structure.setValue(getStructure() - amount);
	}	
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	protected void die() {
		GameLog.getInstance().log(this.getClass().getName() + " dies");
	}	
	
	public MapCell getCell() {
		return (MapCell) getParent();
	}

	@Override
	public abstract int performTimedAction();

	@Override
	public int getActionPoints() {
		return actionPoints;
	}

	@Override
	public void setActionPoints(int ap) {
		this.actionPoints = ap;
		
	}

	public int getEnergy() {
		return energy.getValue();
	}

	public void setEnergy(int energy) {
		mPcs.firePropertyChange(PROP_ENERGY, getEnergy(), energy);
		this.energy.setValue(energy);
	}

	public int getGhost() {
		return ghost.getValue();
	}

	public void setGhost(int ghost) {
		mPcs.firePropertyChange(PROP_GHOST, getGhost(), ghost);
		this.ghost.setValue(ghost);
	}

	public int getMemory() {
		return memory.getValue();
	}

	public void setMemory(int memory) {
		mPcs.firePropertyChange(PROP_MEMORY, getMemory(), memory);
		this.memory.setValue(memory);
	}

	public int getBlast() {
		return blast.getValue();
	}

	public void setBlast(int blast) {
		mPcs.firePropertyChange(PROP_BLAST, getBlast(), blast);
		this.blast.setValue(blast);
	}

	public int getFight() {
		return fight.getValue();
	}

	public void setFight(int fight) {
		mPcs.firePropertyChange(PROP_FIGHT, getFight(), fight);
		this.fight.setValue(fight);
	}

	public int getResist() {
		return resist.getValue();
	}

	public void setResist(int resist) {
		mPcs.firePropertyChange(PROP_FIGHT, getResist(), resist);
		this.resist.setValue(resist);
	}

	public int getMove() {
		return move.getValue();
	}

	public void setMove(int move) {
		mPcs.firePropertyChange(PROP_MOVE, getMove(), move);
		this.move.setValue(move);
	}

	public int getHide() {
		return hide.getValue();
	}

	public void setHide(int hide) {
		mPcs.firePropertyChange(PROP_HIDE, getHide(), hide);
		this.hide.setValue(hide);
	}

	public int getFind() {
		return find.getValue();
	}

	public void setFind(int find) {
		mPcs.firePropertyChange(PROP_FIND, getFind(), find);
		this.find.setValue(find);
	}

	public int getRecover() {
		return recover.getValue();
	}

	public void setRecover(int recover) {
		mPcs.firePropertyChange(PROP_FIGHT, getRecover(), recover);
		this.recover.setValue(recover);
	}

	public int getBuild() {
		return build.getValue();
	}

	public void setBuild(int build) {
		mPcs.firePropertyChange(PROP_BUILD, getBuild(), build);
		this.build.setValue(build);
	}	
	
	/*
	 * Поддержка свойств
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
}
