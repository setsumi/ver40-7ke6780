package ru.ver40.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import org.newdawn.slick.util.DefaultLogSystem;

/**
 * Перехватчик сликовых логов.
 * 
 * Отправляет логи в файл и вьювер в игре. Синглтон.
 */
public class MyLogSystem extends DefaultLogSystem {

	private static MyLogSystem m_instance = null;
	private static PrintStream m_out = null;

	public static MyLogSystem getInstance() {
		if (m_instance == null) {
			try {
				m_out = new PrintStream(new FileOutputStream(
						Constants.DEBUG_LOG_FILE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			m_instance = new MyLogSystem();
		}
		return m_instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		super.info(message);

		m_out.println(Calendar.getInstance().getTime() + " INFO:" + message);
		DebugLog.getInstance().log("I " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#debug(java.lang.String)
	 */
	@Override
	public void debug(String message) {
		super.debug(message);

		m_out.println(Calendar.getInstance().getTime() + " DEBUG:" + message);
		DebugLog.getInstance().log("D " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#error(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void error(String message, Throwable e) {
		super.error(message, e);

		m_out.println(Calendar.getInstance().getTime() + " ERROR:" + message
				+ "; " + e.getMessage());
		DebugLog.getInstance().log("E " + message + "; " + e.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#error(java.lang.Throwable)
	 */
	@Override
	public void error(Throwable e) {
		super.error(e);

		m_out.println(Calendar.getInstance().getTime() + " ERROR:"
				+ e.getMessage());
		DebugLog.getInstance().log("E " + e.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#error(java.lang.String)
	 */
	@Override
	public void error(String message) {
		super.error(message);

		m_out.println(Calendar.getInstance().getTime() + " ERROR:" + message);
		DebugLog.getInstance().log("E " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#warn(java.lang.String)
	 */
	@Override
	public void warn(String message) {
		super.warn(message);

		m_out.println(Calendar.getInstance().getTime() + " WARNING:" + message);
		DebugLog.getInstance().log("W " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#warn(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void warn(String message, Throwable e) {
		super.warn(message, e);

		m_out.println(Calendar.getInstance().getTime() + " WARNING:" + message
				+ "; " + e.getMessage());
		DebugLog.getInstance().log("W " + message + "; " + e.getMessage());
	}

}
