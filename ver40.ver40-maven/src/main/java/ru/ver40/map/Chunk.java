package ru.ver40.map;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import ru.ver40.model.MapCell;
import ru.ver40.util.Constants;

/**
 * Куски карты, которыми идет подгрузка.
 * 
 */
public class Chunk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7430659870673366934L;
	
	/**
	 * Ссылка на карту, которой принадлежит чанк
	 */
	private transient FloorMap m_map;
	/**
	 * Список клеток чанка
	 */
	private ArrayList<MapCell> m_cells;
	/**
	 * Координаты чанка на карте
	 */
	public int m_posX, m_posY;

	/**
	 * Конструктор
	 */
	public Chunk(FloorMap map, int x, int y) {
		m_map = map;
		m_posX = x;
		m_posY = y;
		m_cells = new ArrayList<MapCell>();
		m_cells.ensureCapacity(Constants.MAP_CHUNK_LENGTH);
		for (int i = 0; i < Constants.MAP_CHUNK_LENGTH; i++)
			m_cells.add(new MapCell());

		File f = new File(getFile());
		if (f.exists()) {
			load();
		}
	}

	/**
	 * Вернуть клетку по её индексу в массиве клеток
	 * 
	 * @return
	 */
	public MapCell getCell(int index) {
		return m_cells.get(index);
	}

	/**
	 * Присвоить клетку по её индексу в массиве клеток
	 */
	public void setCell(MapCell cell, int index) {
		m_cells.set(index, cell);
	}

	/**
	 * Вернуть полный путь файла чанка
	 * 
	 * @return
	 */
	public String getFile() {
		return m_map.getPath() + File.separator
				+ String.format("%d_%d.ch", m_posX, m_posY);
	}

	/**
	 * Сохранение чанка в файл
	 */
	public void save() {
		ObjectOutputStream oos = null;
		try {
			// File file = new File(getFile(), "rw");
			oos = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(getFile())));
			oos.writeObject(this);			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Загрузка чанка из файла
	 */
	public void load() {
		ObjectInputStream ois = null;
		try {
			// File file = new File(getFile(), "rw");
			ois = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(getFile())));
			Chunk c = (Chunk) ois.readObject();
			this.m_cells = c.m_cells;
			this.m_posX = c.m_posX;
			this.m_posY = c.m_posY;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
