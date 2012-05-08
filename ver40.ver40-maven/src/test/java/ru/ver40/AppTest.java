package ru.ver40;

import java.util.Calendar;

import ru.ver40.map.Chunk;
import ru.ver40.map.FloorMap;
import ru.ver40.map.gen.FeatureGenerator;
import ru.ver40.map.gen.FeatureGenerator.Point;
import ru.ver40.map.gen.FeatureGenerator.Position;
import ru.ver40.model.MapCell;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		/*
		long t1 = System.nanoTime();
		FloorMap map = new FloorMap("map/test");
		new Chunk(map, 0, 0);
		new Chunk(map, 1, 0);
		new Chunk(map, 0, 1);
		new Chunk(map, 1, 1);
		map.SaveChunks();
		long t2 = System.nanoTime() - t1;
		System.out
				.println("4 chunk serrizalization is " + t2 / 1000000 + " ms");
		*/
	}
	
	public void testGetPointLEFT() {
		FeatureGenerator gen = new FeatureGenerator();
		MapCell[][] cells = new MapCell[3][3];
		for (int r = 0; r < cells.length; ++r) {
			for (int c = 0; c < cells[r].length; ++c) {
				cells[r][c] = new MapCell();
			}
		}
		Point s = gen.getPoint(10, 10, cells[0].length, 
				cells.length, Position.LEFT, true);
		System.out.println(s);
		assertEquals(s.x, 7);
		assertEquals(s.y, 9);
		Point e = gen.getPoint(10, 10, cells[0].length, 
				cells.length, Position.LEFT, false);
		System.out.println(s);
		assertEquals(e.x, 9);
		assertEquals(e.y, 11);
	}
}
