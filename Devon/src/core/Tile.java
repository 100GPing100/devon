package core;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Tile {
	// static stuff
	private static int[] tile_set;
	public static final int Width = 64, Height = 64;
	public static Tile Infected = new Tile(0, 0, 178, 45, 255);
	public static Tile Stone = new Tile(64, 0, 150, 150, 150);
	public static Tile Grass = new Tile(128, 0, 165, 255, 48);
	public static Tile Dirt = new Tile(192, 0, 130, 23, 0);
	public static Tile Goat = new Tile(256, 0, 0, 0, 0);

	public static void loadTileSet(String file) {
		try {
			BufferedImage img = ImageIO.read(Tile.class.getResource(file));
			tile_set = new int[512 * 512];
			img.getRGB(0, 0, 512, 512, tile_set, 0, 512);

			loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: crash.
		}
	}

	private static void loadTiles() {
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {
				Infected.pixels[x + y * Width] = tile_set[(Infected.x + x) + (Infected.y + y) * 512];
				Stone.pixels[x + y * Width] = tile_set[(Stone.x + x) + (Stone.y + y) * 512];
				Grass.pixels[x + y * Width] = tile_set[(Grass.x + x) + (Grass.y + y) * 512];
				Dirt.pixels[x + y * Width] = tile_set[(Dirt.x + x) + (Dirt.y + y) * 512];
				Goat.pixels[x + y * Width] = tile_set[(Goat.x + x) + (Goat.y + y) * 512];
			}
		}
	}

	public static boolean isValidTile(int code) {
		return (code == Infected.id || code == Stone.id || code == Grass.id || code == Dirt.id || code == Goat.id);
	}

	private static int RGB_to_INT(int r, int g, int b) {
		return (0xFF << 24) | (r << 16) | (g << 8) | b;
	}

	// non-static
	public final int x, y;
	public final int[] pixels;
	public final int id;

	public Tile(int x, int y, int r, int g, int b) {
		this.x = x;
		this.y = y;
		pixels = new int[Width * Height];
		id = RGB_to_INT(r, g, b);
	}
}
