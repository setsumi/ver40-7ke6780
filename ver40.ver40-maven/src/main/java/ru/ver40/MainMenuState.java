package ru.ver40;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import ru.ver40.util.AsciiDraw;
import ru.ver40.util.Constants;
import ru.ver40.util.ResourceManager;

/**
 * Стейт главного меню.
 * 
 */
public class MainMenuState extends BasicGameState {

	int m_stateID = -1;

	/**
	 * Конструктор
	 */
	public MainMenuState(int stateID) {
		m_stateID = stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Загрузка ресурсов.
		try {
			ResourceManager.loadResources(Constants.RESOURCE_FILE);
		} catch (IOException e) {
			Log.error("failed to load resource file '"
					+ Constants.RESOURCE_FILE + "': " + e.getMessage());
			throw new SlickException("Resource loading failed!");
		}
		// Включение повтора нажатых клавиш.
		Input input = container.getInput();
		input.enableKeyRepeat();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// Крутая заставка.
		AsciiDraw.getInstance().draw(
				Constants.GAME_NAME,
				Constants.ASCII_SCREEN_WIDTH / 2 - Constants.GAME_NAME.length()
						/ 2, 10, Color.green);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Запускаем игру по пробелу или вводу.
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_SPACE)
				|| input.isKeyPressed(Input.KEY_ENTER)) {
			TheGame.getInstance().getGameplay().show();
		}
		input.clearKeyPressedRecord();
	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
