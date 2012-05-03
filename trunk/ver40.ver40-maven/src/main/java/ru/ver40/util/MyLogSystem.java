/**
 * 
 */
package ru.ver40.util;

import org.newdawn.slick.util.DefaultLogSystem;

/**
 * @author Setsumi
 *
 */
public class MyLogSystem extends DefaultLogSystem {

	private static MyLogSystem m_instance = null;

	public static MyLogSystem getInstance() {
		if (m_instance == null) {
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

		DebugLog.getInstance().log("E Exception. " + e.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.util.DefaultLogSystem#error(java.lang.String)
	 */
	@Override
	public void error(String message) {
		super.error(message);

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

		DebugLog.getInstance().log("W " + message + "; " + e.getMessage());
	}

}
