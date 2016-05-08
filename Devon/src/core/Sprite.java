package core;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Sprite {
	public int frame;
	public int[][] frames;

	public static Sprite load(String file) {
		try {
			BufferedImage img = ImageIO.read(Sprite.class.getResource(file));
			int[] pixels = new int[256 * 256];
			img.getRGB(0, 0, 256, 256, pixels, 0, 256);

			Sprite sprite = new Sprite();

			int i;
			for (int yy = 0; yy < 4; yy++) {
				for (int xx = 0; xx < 4; xx++) {
					i = xx + yy * 4;
					sprite.frames[i] = new int[64 * 64];

					for (int y = 0; y < 64; y++) {
						for (int x = 0; x < 64; x++) {
							sprite.frames[i][x + y * 64] = pixels[(xx * 64 + x) + (yy * 64 + y) * 256];
						}
					}
				}
			}

			return sprite;
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: crash
		}
		return null;
	}

	public void draw(Display display, int x, int y) {
		if (frame < 0) {
			frame = 0;
		}
		if (frame > 15) {
			frame = 15;
		}
		
		display.draw(x, y, 64, 64, frames[frame]);
	}

	public Sprite() {
		frame = 0;
		frames = new int[16][];
	}
}
