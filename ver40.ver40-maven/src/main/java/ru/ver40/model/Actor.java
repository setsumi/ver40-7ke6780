package ru.ver40.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import ru.ver40.map.FloorMap;
import ru.ver40.service.ITimedEntity;
import ru.ver40.system.AAnimation;
import ru.ver40.system.util.GameLog;

/**
 * Базовый абстрактный класс кричеров на карте.
 * 
 */
public abstract class Actor extends GObject implements ITimedEntity {	

	/**
	 * Список действий и их параметров.
	 */
	private HashMap<Integer, Action> m_actions;

	/**
	 * Текущее запланированное действие.
	 */
	private Action m_currAction;
	
	@Override
	public int getActDuration() {
		return m_currAction.getDuration();
	}

	@Override
	public void actionTick() {
		m_currAction.tick();
	}

	/**
	 * Получить текущее запланированное действие.
	 */
	protected Action getCurrAction() {
		return m_currAction;
	}

	/**
	 * Хелпер-функция инициализации действий актера.
	 * 
	 * @param id - идентификатор действия
	 * @param act - параметры действия
	 */
	protected void loadAction(int id, Action act) {
		m_actions.put(id, act);
	}

	// ===========================================
	// Планирование действий.
	// ===========================================
	/**
	 * Координаты цели для действий.
	 */
	protected int m_actTargetX, m_actTargetY;

	/**
	 * Анимация действия (опционально).
	 */
	private AAnimation m_actAnimation = null;

	/**
	 * Вернуть анимацию текущего запланированного действия и очистить её (т.к. следующее
	 * действие может не иметь анимации).
	 */
	protected AAnimation getActAnimation() {
		AAnimation ret = m_actAnimation;
		m_actAnimation = null;
		return ret;
	}

	/**
	 * Запланировать. Тянуть время. Продолжительность по умолчанию.
	 */
	public void action_wait() {
		m_currAction.assign(m_actions.get(Action.WAIT));
	}

	/**
	 * Запланировать. Тянуть время. Указанная продолжительность.
	 */
	public void action_wait(int duration) {
		m_currAction.assign(m_actions.get(Action.WAIT));
		m_currAction.setDuration(duration);
	}

	/**
	 * Запланировать. Передвижение в точку.
	 */
	public void action_moveTo(int x, int y) {
		m_currAction.assign(m_actions.get(Action.MOVE));
		m_actTargetX = x;
		m_actTargetY = y;
	}

	/**
	 * Запланировать. Использование объекта на карте.
	 */
	public void action_useObject(int x, int y) {
		m_currAction.assign(m_actions.get(Action.USEOBJECT));
		m_actTargetX = x;
		m_actTargetY = y;
	}

	/**
	 * Запланировать. Ударить место на карте.
	 */
	public void action_hit(int x, int y) {
		m_currAction.assign(m_actions.get(Action.HIT));
		m_actTargetX = x;
		m_actTargetY = y;
	}

	/**
	 * Запланировать. Пнуть место на карте.
	 */
	public void action_kick(int x, int y) {
		m_currAction.assign(m_actions.get(Action.KICK));
		m_actTargetX = x;
		m_actTargetY = y;
	}

	/**
	 * Запланировать. Выстрелить в точку на карте.
	 */
	public void action_shoot(int x, int y, AAnimation anim) {
		m_currAction.assign(m_actions.get(Action.SHOOT));
		m_actTargetX = x;
		m_actTargetY = y;
		m_actAnimation = anim;
	}

	// =============================================
	// Выполнение действий.
	// =============================================
	/**
	 * Выполнить. Передвижение в точку.
	 */
	protected abstract void do_moveTo(FloorMap map, int x, int y);

	/**
	 * Выполнить. Использование объекта на карте.
	 */
	protected abstract void do_useObject(FloorMap map, int x, int y);

	/**
	 * Выполнить. Ударить место на карте.
	 */
	protected abstract void do_hit(FloorMap map, int x, int y);

	/**
	 * Выполнить. Пнуть место на карте.
	 */
	protected abstract void do_kick(FloorMap map, int x, int y);

	/**
	 * Выполнить. Выстрелить в точку на карте.
	 */
	protected abstract void do_shoot(FloorMap map, int x, int y, AAnimation anim);

	// ===============================================
	// Прочие свойства и методы.
	// ===============================================
	/**
	 * Поддержка изменения пропертей
	 */
	private PropertyChangeSupport m_mPcs = new PropertyChangeSupport(this);
	
	// Аттрибуты
	//
	private NamedAttribute m_structure = NamedAttribute.createStructureAttribute();
	
	private NamedAttribute m_energy    = NamedAttribute.createEnergyAttribute();
	
	private NamedAttribute m_ghost     = NamedAttribute.createGhostAttribute();
	
	private NamedAttribute m_memory    = NamedAttribute.createMemoryAttribute();
	
	//Абилки
	//
	private NamedAttribute m_blast   = NamedAttribute.createBlastAbility();
	 
	private NamedAttribute m_fight   = NamedAttribute.createFightAbility();
	
	private NamedAttribute m_resist  = NamedAttribute.createResistAbility();
	
	private NamedAttribute m_move    = NamedAttribute.createMoveAbility();
	
	private NamedAttribute m_hide    = NamedAttribute.createHideAbility();
	
	private NamedAttribute m_find    = NamedAttribute.createFindAbility();
	
	private NamedAttribute m_recover = NamedAttribute.createRecoverAbility();
	
	private NamedAttribute m_build   = NamedAttribute.createBuildAbility();
	
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
	 * Имя кричера.
	 */
	protected String m_name;

	/**
	 * Координаты на игровой карте.
	 */
	private int m_x, m_y;
	
	/**
	 * Относительная скорость
	 * 10 - значение по умолчанию
	 */
	private int m_speed = 10;

	
	public int getSpeed() {
		return m_speed;
	}

	public void setSpeed(int speed) {
		m_speed = speed;
	}

	private static final long serialVersionUID = 8472085921088558199L;

	/**
	 * Конструктор.
	 * 
	 * В наследниках должны переинициализироваться списки конкретными параметрами.
	 */
	public Actor() {
		m_actions = new HashMap<Integer, Action>();
		m_currAction = new Action();

		// Параметры действий по умолчанию.
		loadAction(Action.WAIT, new Action(Action.WAIT, Action.BASE_VALUE, 0));
		loadAction(Action.MOVE, new Action(Action.MOVE, Action.BASE_VALUE, 0));
		loadAction(Action.USEOBJECT, new Action(Action.USEOBJECT, Action.BASE_VALUE, 0));
		loadAction(Action.HIT, new Action(Action.HIT, Action.BASE_VALUE, 0));
		loadAction(Action.KICK, new Action(Action.KICK, Action.BASE_VALUE, 0));
		loadAction(Action.SHOOT, new Action(Action.SHOOT, Action.BASE_VALUE, 0));

		// По умолчанию ждем.
		action_wait();

		getSymbol().setSymbol('A');
		m_structure.setValue(10);
		setPassable(false);
		m_name = getClass().getName();
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}

	public String toString() {
		return m_name;
	}

	public int getStructure() {
		return m_structure.getValue();
	}

	public void setStructure(int hp) {
		if (hp <= 0) {
			die();
		}
		m_mPcs.firePropertyChange(PROP_STRUCTURE, getStructure(), hp);
		m_structure.setValue(hp);
	}
	
	public void damage(int amount) {
		if (getStructure() - amount <= 0) {
			die();
		}
		m_mPcs.firePropertyChange(PROP_STRUCTURE, getStructure(), getStructure() - amount);
		m_structure.setValue(getStructure() - amount);
	}	

	@Override
	public int getX() {
		return m_x;
	}

	@Override
	public int getY() {
		return m_y;
	}

	public void setX(int x) {
		m_x = x;
	}

	public void setY(int y) {
		m_y = y;
	}

	protected void die() {
		GameLog.getInstance().log(GameLog.Type.IMPORTANT, m_name + " dies");
	}	
	
	public MapCell getCell() {
		return getParent();
	}

	public int getEnergy() {
		return m_energy.getValue();
	}

	public void setEnergy(int energy) {
		m_mPcs.firePropertyChange(PROP_ENERGY, getEnergy(), energy);
		m_energy.setValue(energy);
	}

	public int getGhost() {
		return m_ghost.getValue();
	}

	public void setGhost(int ghost) {
		m_mPcs.firePropertyChange(PROP_GHOST, getGhost(), ghost);
		m_ghost.setValue(ghost);
	}

	public int getMemory() {
		return m_memory.getValue();
	}

	public void setMemory(int memory) {
		m_mPcs.firePropertyChange(PROP_MEMORY, getMemory(), memory);
		m_memory.setValue(memory);
	}

	public int getBlast() {
		return m_blast.getValue();
	}

	public void setBlast(int blast) {
		m_mPcs.firePropertyChange(PROP_BLAST, getBlast(), blast);
		m_blast.setValue(blast);
	}

	public int getFight() {
		return m_fight.getValue();
	}

	public void setFight(int fight) {
		m_mPcs.firePropertyChange(PROP_FIGHT, getFight(), fight);
		m_fight.setValue(fight);
	}

	public int getResist() {
		return m_resist.getValue();
	}

	public void setResist(int resist) {
		m_mPcs.firePropertyChange(PROP_FIGHT, getResist(), resist);
		m_resist.setValue(resist);
	}

	public int getMove() {
		return m_move.getValue();
	}

	public void setMove(int move) {
		m_mPcs.firePropertyChange(PROP_MOVE, getMove(), move);
		m_move.setValue(move);
	}

	public int getHide() {
		return m_hide.getValue();
	}

	public void setHide(int hide) {
		m_mPcs.firePropertyChange(PROP_HIDE, getHide(), hide);
		m_hide.setValue(hide);
	}

	public int getFind() {
		return m_find.getValue();
	}

	public void setFind(int find) {
		m_mPcs.firePropertyChange(PROP_FIND, getFind(), find);
		m_find.setValue(find);
	}

	public int getRecover() {
		return m_recover.getValue();
	}

	public void setRecover(int recover) {
		m_mPcs.firePropertyChange(PROP_FIGHT, getRecover(), recover);
		m_recover.setValue(recover);
	}

	public int getBuild() {
		return m_build.getValue();
	}

	public void setBuild(int build) {
		m_mPcs.firePropertyChange(PROP_BUILD, getBuild(), build);
		m_build.setValue(build);
	}	
	
	/*
	 * Поддержка свойств
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        m_mPcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        m_mPcs.removePropertyChangeListener(listener);
    }
}
