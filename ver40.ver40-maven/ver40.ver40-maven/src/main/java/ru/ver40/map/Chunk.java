package ru.ver40.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	private transient FloorMap m_map;
	private List<MapCell> m_cells;

	public int m_posX, m_posY;

	/**
	 * Конструктор
	 * 
	 */
	public Chunk(FloorMap map, int x, int y) {
		m_map = map;
		m_posX = x;
		m_posY = y;
		m_cells = new ArrayList<MapCell>(2);// Constants.MAP_CHUNK_LENGTH);
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
	 * Вернуть полный путь файла чанка
	 * 
	 * @return
	 */
	public String getFile() {
		return m_map.getPath() + File.separator
				+ String.format("(%010d)(%010d).ch", m_posX, m_posY);
	}

	/**
	 * Сохранение чанка в файл
	 */
	public void save() {
		// TODO оптимизация сохранением только изменившихся клеток
		/*
		try (RandomAccessFile outFile = new RandomAccessFile(getFile(), "rw")) {
			int i = 0;
			while (i < Constants.MAP_CHUNK_LENGTH) {
				outFile.writeShort(m_cells.get(i).type);
				i++;
			}
			// for (Cell c : m_cells) {
			// outFile.writeShort(c.type);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		ObjectOutputStream oos = null;
		try {
			File file = new File(getFile(), "rw");
			oos = new ObjectOutputStream(new FileOutputStream(file));
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
		/*
		try (RandomAccessFile inFile = new RandomAccessFile(getFile(), "rw")) {
			int i = 0;
			while (i < Constants.MAP_CHUNK_LENGTH) {
				m_cells.get(i).type = inFile.readShort();
				i++;
			}
			// for (Cell c : m_cells) {
			// c.type = inFile.readShort();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		ObjectInputStream ois = null;
		try {
			File file = new File(getFile(), "rw");
			ois = new ObjectInputStream(new FileInputStream(file));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		try {
			save(); // сохраняем чанк
		} finally {
			super.finalize();
		}
	}
}
