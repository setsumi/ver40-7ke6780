package ru.ver40.system.ui;

import org.newdawn.slick.Color;

/**
 * Плавающее окошко со строчкой текста.
 */
public class WndTooltip extends MockWindow {

	private static final int INDENT_X = 3;
	private static final int INDENT_Y = 2;

	private static final int CTRL_LABEL_TEXT = 0;

	/**
	 * Конструктор.
	 */
	public WndTooltip(Color fc, Color bc) {
		super(null, 0, 0, 0, 0, fc, bc, "", "", false);
		//
		addChild(CTRL_LABEL_TEXT, new CtrlLabel(this, INDENT_X, INDENT_Y, fc));
		setText(" ");
	}

	public void setText(String text) {
		CtrlLabel label = (CtrlLabel) getChild(CTRL_LABEL_TEXT);
		label.setText(text);
		setWidth(label.getWidth() + INDENT_X * 2);
		setHeight(label.getHeight() + INDENT_Y * 2);
		setPosX(61 - m_width/*
							 * Constants.ASCII_SCREEN_WIDTH - getWidth() -
							 * INDENT_X
							 */);
		setPosY(1/* INDENT_Y */);
	}

}
