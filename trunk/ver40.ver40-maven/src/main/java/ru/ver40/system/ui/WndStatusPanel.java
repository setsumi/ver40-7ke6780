package ru.ver40.system.ui;

import org.newdawn.slick.Color;

import ru.ver40.model.Player;
import ru.ver40.service.MapService;

/**
 * Панелька основных параметров персонажа и другой информации (справа).
 * 
 */
public class WndStatusPanel extends MockWindow {

	private static final int CTRL_LABEL_STRUCTURE = 10;
	private static final int CTRL_LABEL_ENERGY = 20;
	private static final int CTRL_LABEL_GHOST = 30;
	private static final int CTRL_LABEL_MEMORY = 40;
	private static final int CTRL_LABEL_TIME = 50;

	private static final int CTRL_LABEL_MAPLEVEL = 60;

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
		i += 2;
		addChild(CTRL_LABEL_MAPLEVEL, new CtrlLabel(this, 0, i, fc));
		setHeight(i + 1);
	}

	public void updateData(Player player, int time) {
		((CtrlLabel) getChild(CTRL_LABEL_STRUCTURE)).setText("STRUCT: "
				+ player.getStructure());
		((CtrlLabel) getChild(CTRL_LABEL_ENERGY)).setText("ENERGY: "
				+ player.getEnergy());
		((CtrlLabel) getChild(CTRL_LABEL_GHOST)).setText("GHOST : "
				+ player.getGhost());
		((CtrlLabel) getChild(CTRL_LABEL_MEMORY)).setText("MEMORY: "
				+ player.getMemory());

		((CtrlLabel) getChild(CTRL_LABEL_TIME)).setText("TIME  : " + time);
		((CtrlLabel) getChild(CTRL_LABEL_MAPLEVEL)).setText("LEVEL : " + "\n"
				+ MapService.getInstance().getLevelNum());
	}
}
