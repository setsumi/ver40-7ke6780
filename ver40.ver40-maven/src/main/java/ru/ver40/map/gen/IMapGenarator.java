package ru.ver40.map.gen;

import ru.ver40.map.FloorMap;

/**
 * Для заменяемости одного алгоритма генерации на другой
 * каждый алгоритм должен реализовывать данный интерфейс
 * 
 * @author anon
 *
 */
public interface IMapGenarator {
	
	void generate(FloorMap map);

}
