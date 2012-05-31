package ru.ver40.map.gen;

import ru.ver40.map.FloorMap;

/**
 * Для заменяемости одного алгоритма генерации на другой каждый алгоритм должен реализовывать
 * данный интерфейс
 * 
 */
public interface IMonsterGenerator {

	/**
	 * Вызывается для расстановки монстров.
	 */
	public void generate(FloorMap map);

}
