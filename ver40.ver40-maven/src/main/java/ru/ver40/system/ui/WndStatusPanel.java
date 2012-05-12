package ru.ver40.system.ui;

import org.newdawn.slick.Color;

import ru.ver40.model.Player;

/**
 * Отображение основных параметров персонажа в игре.
 * 
 */
public class WndStatusPanel extends MockWindow {

	private static final int CTRL_LABEL_STRUCTURE = 0;
	private static final int CTRL_LABEL_ENERGY = 1;
	private static final int CTRL_LABEL_GHOST = 2;
	private static final int CTRL_LABEL_MEMORY = 3;
	private static final int CTRL_LABEL_TIME = 4;

	/**
	 * Конструктор.
	 */
	public WndStatusPanel(int x, int y, Color fc, Color bc) {
		super(null, x, y, 17, 0, fc, bc, "", "", false);
		//
		int i = 0;
		addChild(CTRL_LABEL_STRUCTURE, new CtrlLabel(this, 0, i, fc));
		i += 1;
		addChild(CTRL_LABEL_ENERGY, new CtrlLabel(this, 0, i, fc));
		i += 1;
		addChild(CTRL_LABEL_GHOST, new CtrlLabel(this, 0, i, fc));
		i += 1;
		addChild(CTRL_LABEL_MEMORY, new CtrlLabel(this, 0, i, fc));
		i += 2;
		addChild(CTRL_LABEL_TIME, new CtrlLabel(this, 0, i, fc));
		setHeight(i + 1);
	}

	public void updateData(Player player, int time) {
		((CtrlLabel) getChild(CTRL_LABEL_STRUCTURE)).setText("STRUCT: "
				+ Integer.toString(player.getStructure()));
		((CtrlLabel) getChild(CTRL_LABEL_ENERGY)).setText("ENERGY: "
				+ Integer.toString(player.getEnergy()));
		((CtrlLabel) getChild(CTRL_LABEL_GHOST)).setText("GHOST : "
				+ Integer.toString(player.getGhost()));
		((CtrlLabel) getChild(CTRL_LABEL_MEMORY)).setText("MEMORY: "
				+ Integer.toString(player.getMemory()));
		((CtrlLabel) getChild(CTRL_LABEL_TIME)).setText("TIME  : " + time);
	}
}
