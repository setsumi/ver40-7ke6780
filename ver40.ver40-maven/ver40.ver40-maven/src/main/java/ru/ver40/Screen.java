package ru.ver40;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ru.ver40.util.AsciiDraw;
import ru.ver40.util.Constants;

/**
 * Основной экран игры.
 * Паттерн сингтон.
 * @author anon
 *
 */
public class Screen extends BasicGame {
	
	/**
	 * Инстанс текущего игрового экрана.
	 */
	private static Screen instance;
	
	/**
	 * Хелпер, отрисовывающий ASCII на графике.
	 * TODO: Вероятно стоит перенести сюда?
	 */
	private AsciiDraw ascii;	
	
	private Tile[] tiles;
	
	/**
	 * Стандартный метод получения текущего игрового экрана.
	 * @return
	 */
	public static Screen getCurrent() {
		if (instance == null) {
			instance = new Screen("ver.40-7KE.6780");
		}
		return instance;
	}
	
	/**
	 * Получение инстанса данного класса доступно только из 
	 * @param title
	 */
	private Screen(String title) {
		super(title);
		ascii = new AsciiDraw();
		tiles = new Tile[Constants.ASCII_SCREEN_WIDTH*40];
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		for (int y = 0; y < 40; ++y) {
			for (int x = 0; x < Constants.ASCII_SCREEN_WIDTH; ++x) {
				int p = x * y;
				 ascii.draw(Character.toString(tiles[p].getSymbol()), x, y, Color.white, Color.black, g);
			}				
		}		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, int state) throws SlickException {
		// TODO Auto-generated method stub		
	}
	
	public class Tile {
		private char symbol;
		private int color;
		
		public Tile(char symbol) {
			this(symbol, 000000);
		}
		
		public Tile(char symbol, int color) {
			this.symbol = symbol;
			this.color = color;
		}
		
	
		public char getSymbol() {
			return symbol;
		}

		public void setSymbol(char symbol) {
			this.symbol = symbol;
		}

		public int getColor() {
			return color;
		}
		
		public void setColor(int color) {
			this.color = color;
		}		
	}
}
