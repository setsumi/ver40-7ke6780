package ru.ver40.util;

import java.util.Random;

/**
 * Обертка для создания единого генератора случайных чисел чтобы 
 * не плодить объекты Random.
 * 
 * @author anon
 *
 */
public class Rng {
	
	private static Random rng;
	static {
		rng = new Random();
	}
	
	/**
	 * Вернет результат броска кубика, 1d(d);
	 * @param d - число граней кубика
	 * @return - результат броска
	 */
	public static int d(int d) {
		return rng.nextInt(d) + 1;
	}
	
	/**
	 * Вернет результат броска типа xd(d)
	 * @param x - число бросков
	 * @param d - число граней кубика
	 * @return - результат броска
	 */
	public static int d(int x, int d) {
		int ret = 0;
		for (int i = 0; i < x; i++) {
			ret += d(d);
		}
		return ret;
	}
	
	/**
	 * Вернет результат броска кубика типа xd(d) + m
	 * @param x - число бросков
	 * @param d - число граней кубика
	 * @param mf - модификатор
	 * @return - результат броска
	 */
	public static int d(int x, int d, int mf) {
		return d(x, d) + mf;
	}
	
	/**
	 * Возвращает случайной число между 1 и 0.
	 * @return - случайное число
	 */
	public double random() {
		return rng.nextDouble();
	}
	
	/**
	 * Возвращает, 'сбылось' ли событие, указанное в процентах.
	 * @param percent - процент - 1 - 100
	 * @return 
	 */
	public static boolean percent(int percent) {
		return d(100) <= percent;		
	}
}
