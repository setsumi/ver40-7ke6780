package ru.ver40.map;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import ru.ver40.util.Constants;

/**
 * Куски карты, которыми идет подгрузка.
 * 
 */
public class Chunk {
	private FloorMap m_map;
	private List<Cell> m_cells;

	public int m_posX, m_posY;

	/**
	 * Конструктор
	 * 
	 */
	public Chunk(FloorMap map, int x, int y) {
		m_map = map;
		m_posX = x;
		m_posY = y;
		m_cells = new ArrayList<Cell>(2);// Constants.MAP_CHUNK_LENGTH);
		for (int i = 0; i < Constants.MAP_CHUNK_LENGTH; i++)
			m_cells.add(new Cell());

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
	public Cell getCell(int index) {
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
		}
	}

	/**
	 * Загрузка чанка из файла
	 */
	public void load() {
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
