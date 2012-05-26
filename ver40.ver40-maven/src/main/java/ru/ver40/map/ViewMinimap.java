package ru.ver40.map;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ru.ver40.model.Building;
import ru.ver40.model.DoorBehaviour;
import ru.ver40.model.MapCell;
import ru.ver40.service.MapService;
import ru.ver40.system.util.AsciiDraw;
import ru.ver40.system.util.FilterableImage;
import ru.ver40.util.Constants;

/**
 * Рисует миникарту.
 * 
 */
public class ViewMinimap {

	private class ImageLocation {
		public int m_chunkX, m_chunkY; // координаты чанка
		public FilterableImage m_image; // картинка с картой чанка

		public ImageLocation(int chunkX, int chunkY, FilterableImage image) {
			m_chunkX = chunkX;
			m_chunkY = chunkY;
			m_image = image;
		}
	}

	private Point m_scrPos; // положение на экране (левого верхнего угла)
	private int m_cellWidth, m_cellHeight; // размер (в клетках карты)
	private int m_scrWidth, m_scrHeight; // размер на экране (в пикселах)

	private Point m_mapPos; // положение вьюпорта на карте
	private Point m_cellTop; // верхний левый угол вьюпорта на карте (в клетках)
	private LinkedList<ImageLocation> m_images; // кеш картинок миникарт чанков
	private Point m_chunkTop, m_chunkBottom; // уголы вьюпорта на карте (в
												// чанках)
	private int m_zoom; // увеличение карты
	private Color m_color; // цвет карты

	/**
	 * Конструктор.
	 * 
	 * @param sx
	 *            положение на экране (посимвольное)
	 * @param sy
	 *            положение на экране (посимвольное)
	 * @param w
	 *            ширина (посимвольное)
	 * @param h
	 *            высота (посимвольное)
	 * @param mx
	 *            положение на карте
	 * @param my
	 *            положение на карте
	 * @param zoom
	 *            увеличение
	 * @param col
	 *            цвет
	 */
	public ViewMinimap(int sx, int sy, int w, int h, int mx, int my, int zoom,
			Color col) {
		AsciiDraw ascii = AsciiDraw.getInstance();
		m_scrPos = new Point(sx * ascii.getWidth(), sy * ascii.getHeight());
		m_mapPos = new Point(mx, my);
		m_scrWidth = w * ascii.getWidth();
		m_scrHeight = h * ascii.getHeight();
		m_cellTop = new Point();
		m_images = new LinkedList<ImageLocation>();
		m_color = col;
		m_zoom = zoom;
	}

	/**
	 * Инициализация миникарты.
	 * 
	 * @param x
	 *            положение на карте
	 * @param y
	 *            положение на карте
	 */
	public void init(int x, int y) {
		m_images.clear();
		m_mapPos.x = x;
		m_mapPos.y = y;
		setZoom(m_zoom);
	}

	/**
	 * Проходима ли клетка в принципе (без учета текущей обстановки).
	 */
	private boolean isCellPassable(MapCell cell) {
		boolean passable = true;
		Building building = cell.getBuilding();
		if (building != null && !(building.getBeh() instanceof DoorBehaviour)) {
			passable = false;
		}
		return cell.getFloor().isPassable() && passable;
	}

	/**
	 * Сформировать изображение миникарты чанка.
	 */
	private FilterableImage getChunkImage(int chunkX, int chunkY) {
		Chunk chunk = MapService.getInstance().getMap()
				.getChunkPassive(chunkX, chunkY);
		FilterableImage img = null;
		Graphics g = null;
		try {
			img = new FilterableImage(Constants.MAP_CHUNK_SIZE,
					Constants.MAP_CHUNK_SIZE, Image.FILTER_NEAREST);
			g = img.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		g.setBackground(Color.black);
		g.clear();
		if (chunk == null) {
			g.setColor(Color.gray);
			Random rnd = new Random();
			for (int i = 0; i < 512; i++) {
				int x = rnd.nextInt(250);
				int y = rnd.nextInt(250);
				int len = rnd.nextInt(20) + 1;
				if (x > 70 || y > 20) {
					g.drawLine(x, y, x + len, y);
				}
			}
			g.setColor(m_color);
			g.drawString("No data", 5, 1);
		} else {
//			g.drawString(Integer.toString(loc.m_chunk.getIndex()), 1, 1);
			g.setColor(m_color);
			int ci = 0;
			for (int i = 0; i < Constants.MAP_CHUNK_SIZE; i++) {
				for (int j = 0; j < Constants.MAP_CHUNK_SIZE; j++) {
					if (isCellPassable(chunk.getCell(ci))) {
						g.fillRect(j, i, 1, 1);
					}
					ci++;
				}
			}
		}
		g.flush();
		return img;
	}

	/**
	 * Установить масштаб (коэффициент увеличения).
	 */
	public void setZoom(int zoom) {
		m_zoom = zoom;
		m_cellWidth = m_scrWidth / zoom;
		m_cellHeight = m_scrHeight / zoom;
		moveTo(m_mapPos.x, m_mapPos.y);
	}

	/**
	 * Переместить вьюпорт в указанную точку на карте.
	 */
	public void moveTo(int x, int y) {
		m_mapPos.x = FloorMap.normalizePos(x);
		m_mapPos.y = FloorMap.normalizePos(y);
		m_cellTop.x = FloorMap.getViewRectX(m_mapPos.x, m_cellWidth);
		m_cellTop.y = FloorMap.getViewRectY(m_mapPos.y, m_cellHeight);

		m_chunkTop = FloorMap.getChunkXY(m_cellTop.x, m_cellTop.y);
		m_chunkBottom = FloorMap.getChunkXY(m_cellTop.x + m_cellWidth - 1,
				m_cellTop.y + m_cellHeight - 1);
		// заносим изображения чанков в кэш
		for (int i = m_chunkTop.x; i <= m_chunkBottom.x; i++) {
			for (int j = m_chunkTop.y; j <= m_chunkBottom.y; j++) {
				if (!isImageInCache(i, j)) {
					if (m_images.size() == Constants.MINIMAP_IMAGE_CACHE_SIZE) {
						m_images.remove(0);
					}
					m_images.add(new ImageLocation(i, j, getChunkImage(i, j)));
				}
			}
		}
	}

	/**
	 * Проверка наличия изображения в кэше.
	 */
	private boolean isImageInCache(int chunkX, int chunkY) {
		boolean found = false;
		for (ImageLocation i : m_images) {
			if (i.m_chunkX == chunkX && i.m_chunkY == chunkY) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * Подразумевается, что соответствующее изображение уже есть в кэше.
	 */
	private Image getImageFromCache(int chunkX, int chunkY) {
		Image image = null;
		for (ImageLocation i : m_images) {
			if (i.m_chunkX == chunkX && i.m_chunkY == chunkY) {
				image = i.m_image;
				break;
			}
		}
		return image;
	}

	/**
	 * Вернуть точку взгляда вьюпорта миникарты.
	 * 
	 * @return new Point()
	 */
	public Point getMapPos() {
		return new Point(m_mapPos);
	}

	/**
	 * Вернуть увеличение.
	 */
	public int getZoom() {
		return m_zoom;
	}

	/**
	 * Получить экранную координату X, соответствующую координате на карте во
	 * вьюпорте.
	 */
	public int transMapToScrX(int mapX) {
		return (mapX - m_cellTop.x) * m_zoom + m_scrPos.x;
	}

	/**
	 * Получить экранную координату Y, соответствующую координате на карте во
	 * вьюпорте.
	 */
	public int transMapToScrY(int mapY) {
		return (mapY - m_cellTop.y) * m_zoom + m_scrPos.y;
	}

	/**
	 * Рендер миникарты.
	 */
	public void draw(Graphics g) {
		g.setClip(m_scrPos.x, m_scrPos.y, m_scrWidth, m_scrHeight);
		// миникарта
		int deltaX = m_cellTop.x % Constants.MAP_CHUNK_SIZE;
		int deltaY = m_cellTop.y % Constants.MAP_CHUNK_SIZE;
		for (int i = m_chunkTop.x; i <= m_chunkBottom.x; i++) {
			for (int j = m_chunkTop.y; j <= m_chunkBottom.y; j++) {
				Image image = getImageFromCache(i, j);
				int size = Constants.MAP_CHUNK_SIZE;
				int scrX = m_scrPos.x - deltaX * m_zoom + (i - m_chunkTop.x)
						* size * m_zoom;
				int scrY = m_scrPos.y - deltaY * m_zoom + (j - m_chunkTop.y)
						* size * m_zoom;
				g.drawImage(image, scrX, scrY, scrX + size * m_zoom, scrY
						+ size * m_zoom, 0, 0, size, size);
			}
		}
		// указатель положения
		g.setColor(Color.white);
		g.fillRect(transMapToScrX(m_mapPos.x), transMapToScrY(m_mapPos.y), 2, 2);

		g.clearClip();
	}

}
