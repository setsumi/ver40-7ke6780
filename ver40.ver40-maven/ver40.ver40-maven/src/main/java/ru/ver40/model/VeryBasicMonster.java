package ru.ver40.model;

/**
 * Да, это та самая красная M которая бьет на 1 хит
 * @author anon
 *
 */
public class VeryBasicMonster extends GObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279838019560874964L;
	
	public VeryBasicMonster() {
		getSymbol().setBgColor(0xBD0000);
		getSymbol().setSymbol('M');
	}

}
