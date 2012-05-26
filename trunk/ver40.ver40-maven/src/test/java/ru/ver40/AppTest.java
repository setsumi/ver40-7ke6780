package ru.ver40;

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
//		FeatureGenerator gen = new FeatureGenerator();
//		MapCell[][] cells = new MapCell[3][3];
//		fill(cells);
//		Point s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.LEFT, true);
//		Point e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.LEFT, false);
//		assertEquals(s.x, 7);
//		assertEquals(s.y, 9);
//		assertEquals(e.x, 9);
//		assertEquals(e.y, 11);	
//		
//		cells = new MapCell[1][5];
//		fill(cells);
//		s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.LEFT, true);
//		e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.LEFT, false);
//		assertEquals(s.x, 5);
//		assertEquals(s.y, 10);		
//		assertEquals(e.x, 9);
//		assertEquals(e.y, 10);				
	}
	
	public void testGetPointRIGHT() {
//		FeatureGenerator gen = new FeatureGenerator();
//		MapCell[][] cells = new MapCell[3][3];
//		fill(cells);
//		Point s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.RIGHT, true);
//		Point e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.RIGHT, false);
//		assertEquals(s.x, 11);
//		assertEquals(s.y, 9);
//		assertEquals(e.x, 13);
//		assertEquals(e.y, 11);	
//		
//		cells = new MapCell[1][5];
//		fill(cells);
//		s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.RIGHT, true);
//		e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.RIGHT, false);
//		assertEquals(s.x, 11);
//		assertEquals(s.y, 10);		
//		assertEquals(e.x, 15);
//		assertEquals(e.y, 10);				
	}
	
	public void testGetPointTOP() {
//		FeatureGenerator gen = new FeatureGenerator();
//		MapCell[][] cells = new MapCell[3][3];
//		fill(cells);
//		Point s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.TOP, true);
//		Point e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.TOP, false);
//		assertEquals(s.x, 9);
//		assertEquals(s.y, 7);		
//		assertEquals(e.x, 11);
//		assertEquals(e.y, 9);
//		
//		cells = new MapCell[1][5];
//		fill(cells);
//		s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.TOP, true);
//		e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.TOP, false);
//		assertEquals(s.x, 8);
//		assertEquals(s.y, 9);		
//		assertEquals(e.x, 12);
//		assertEquals(e.y, 9);				
	}
	
	public void testGetPointBOTTOM() {
//		FeatureGenerator gen = new FeatureGenerator();
//		MapCell[][] cells = new MapCell[3][3];
//		fill(cells);
//		Point s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.BOTTOM, true);
//		Point e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.BOTTOM, false);
//		System.out.println(s + " " + e);
//		assertEquals(s.x, 9);
//		assertEquals(s.y, 11);		
//		assertEquals(e.x, 11);
//		assertEquals(e.y, 13);
//		
//		cells = new MapCell[1][5];
//		fill(cells);
//		s = gen.getPoint(10, 10, cells[0].length, cells.length, Position.BOTTOM, true);
//		e = gen.getPoint(10, 10, cells[0].length, cells.length, Position.BOTTOM, false);
//		System.out.println(s + " " + e);
//		assertEquals(s.x, 8);
//		assertEquals(s.y, 11);		
//		assertEquals(e.x, 12);
//		assertEquals(e.y, 11);				
	}
	
//	private void fill(MapCell[][] cells) {
//		for (int r = 0; r < cells.length; ++r) {
//			for (int c = 0; c < cells[r].length; ++c) {
//				cells[r][c] = new MapCell();
//			}
//		}
//	}
}
