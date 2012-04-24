package ver40.game.map;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import ver40.util.Constants;

/**
 * Куски карты, которыми идет подгрузка.
 * 
 */
public class Chunk {
	Map map;
	long index;
	ArrayList<Cell> cells;

	/**
	 * Конструктор
	 * 
	 * @param index
	 */
	public Chunk(Map map, long index) {
		this.map = map;
		this.index = index;
		cells = new ArrayList<Cell>(Constants.MAP_CHUNK_SIZE
				* Constants.MAP_CHUNK_SIZE);
		for (int i = 0; i < Constants.MAP_CHUNK_SIZE * Constants.MAP_CHUNK_SIZE; i++)
			cells.add(new Cell());
	}

	/**
	 * Вернуть клетку по её координатам в чанке
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell getCell(int x, int y) {
		return cells.get(y * Constants.MAP_CHUNK_SIZE + x);
	}

	/**
	 * Присвоить клетку по её координатам в чанке
	 * 
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setCell(int x, int y, Cell c) {
		cells.set(y * Constants.MAP_CHUNK_SIZE + x, c);
	}

	/**
	 * Вернуть полный путь файла чанка
	 * 
	 * @return
	 */
	public String getFile() {
		return new String(map.getPath() + File.separator + Long.toString(index)
				+ ".ch");
	}

	/**
	 * Сохранение чанка в файл
	 */
	public void save() {
		// TODO оптимизация сохранением только изменившихся клеток
		try (RandomAccessFile outFile = new RandomAccessFile(getFile(), "w")) {
			for (Cell c : cells) {
				outFile.writeInt(c.type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Загрузка чанка из файла
	 */
	public void load() {
		try (RandomAccessFile inFile = new RandomAccessFile(getFile(), "r")) {
			for (int i = 0; i < Constants.MAP_CHUNK_SIZE
					* Constants.MAP_CHUNK_SIZE; i++) {
				cells.get(i).type = inFile.readInt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
