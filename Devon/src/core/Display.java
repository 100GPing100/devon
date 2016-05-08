package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public final class Display extends Canvas {
	private static final long serialVersionUID = 1L;
	public final int Width, Height;
	public final String Title;
	private JFrame frame;

	private BufferedImage out_img;
	private int[] pixels;
	
	public void drawCircle(int x, int y, int radius) {
		Graphics g = out_img.createGraphics();
		g.setColor(Color.RED);
		g.drawOval(x, y, radius, radius);
		g.dispose();
	}

	public void drawBox(int xmin, int ymin, int xmax, int ymax, int colour) {
		if (xmax < 0 || ymax < 0 || xmin >= Width || ymin >= Height) {
			return;
		}
		
		for (int x = xmin; x < xmax; x++) {
			if (x > -1 && x < Width) {
				if (ymin > -1) {
					pixels[x + ymin * Width] = colour;
				}
				if (ymax < Height) {
					pixels[x + ymax * Width] = colour;
				}
			}
		}
		for (int y = ymin; y < ymax; y++) {
			if (y > -1 && y < Height) {
				if (xmin > -1) {
					pixels[xmin + y * Width] = colour;
				}
				if (xmax < Width) {
					pixels[xmax + y * Width] = colour;
				}
			}
		}
	}

	public void draw(int x, int y, int w, int h, int[] data) {
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (x + xx >= Width || y + yy >= Height || x + xx < 0 || y + yy < 0) {
					continue;
				}

				if ((data[xx + yy * w] >> 24 & 0xFF) == 255 && data[xx + yy * w] != 0xFFFF00FF)
					pixels[(x + xx) + (y + yy) * Width] = data[xx + yy * w];
			}
		}
	}
	
	public void draw(int x, int y, int w, int rw, int h, int rh, int[] data) {
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (x + xx >= Width || y + yy >= Height || x + xx < 0 || y + yy < 0) {
					continue;
				}

				if ((data[xx + yy * rw] >> 24 & 0xFF) == 255 && data[xx + yy * rw] != 0xFFFF00FF)
					pixels[(x + xx) + (y + yy) * Width] = data[xx + yy * rw];
			}
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFF6495ED;
		}
	}

	public void flush() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(out_img, 0, 0, null);
		g.dispose();
		bs.show();
	}

	public boolean isCloseRequested() {
		return !frame.isVisible();
	}

	public void init(Input input) {
		frame = new JFrame();
		frame.add(this);
		frame.setSize(Width, Height);
		frame.setTitle(Title);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);

		out_img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) out_img.getRaster().getDataBuffer()).getData();

		addKeyListener(input);
	}

	public void free() {
		frame.dispose();
	}

	public Display(int w, int h, String title) {
		Width = w;
		Height = h;
		Title = title;
	}
}
