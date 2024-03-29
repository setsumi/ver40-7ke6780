package ru.ver40.model;

import java.io.Serializable;

/**
 * Репрезентация графической информаии об игровом объекте 
 * 
 * @author anon
 *
 */
public class Symbol implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7038322624384390091L;

	private char symbol;
	private int bgColor, fgColor;
	
	public Symbol() {
		this('.', 0x000000, 0x808080);
	}
	
	public Symbol(char symbol) {
		this(symbol, 0x000000, 0x808080);
	}
	
	public Symbol(char symbol, int bgColor) {
		this(symbol, bgColor, 0x808080);
	}

	public Symbol(char c, int bgColor, int fgColor) {
		this.symbol = c;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public int getFgColor() {
		return fgColor;
	}

	public void setFgColor(int fgColor) {
		this.fgColor = fgColor;
	}		
	
}