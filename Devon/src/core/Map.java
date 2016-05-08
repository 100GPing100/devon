package core;

import game.Coin;
import game.Enemy;
import game.Game;
import game.Trigger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import physics.AABB;

public final class Map {
	public int Width;
	public int Height;
	public int[] data;
	private String map_name;
	public ArrayList<AABB> aabbs;
	public ArrayList<Trigger> triggers;
	public ArrayList<Enemy> enemies;
	private static final Sprite s_coin = Sprite.load("/data/sprites/coin.png");
	private static final Sprite s_enemy = Sprite.load("/data/sprites/slime_blue.png");

	public void load(Game game, String file) {
		if (map_name.equals(file)) {
			return;
		}
		
		aabbs.clear();
		for (int i = 0; i < triggers.size(); i++) {
			triggers.get(i).delete();
		}
		triggers.clear();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).delete();
		}
		enemies.clear();

		try {
			BufferedImage img = ImageIO.read(Map.class.getResource(file));
			Width = img.getWidth();
			Height = img.getHeight();
			data = new int[Width * Height];
			img.getRGB(0, 0, Width, Height, data, 0, Width);
			map_name = file;

			for (int y = 0; y < Height; y++) {
				for (int x = 0; x < Width; x++) {
					if (data[x + y * Width] == 0xFFFFFF00) {
						data[x + y * Width] = 0xFFFFFFFF;
						Coin c = new Coin(game, s_coin);
						c.location.set(x * 64, -y * 64);
						continue;
					} else if (data[x + y * Width] == 0xFF00FF00) {
						data[x + y * Width] = 0xFFFFFFFF;
						Trigger t = new Trigger(game);
						t.location.set(x * 64, -y * 64);
						triggers.add(t);
					} else if (data[x + y * Width] == 0xFF0000FF) {
						data[x + y * Width] = 0xFFFFFFFF;
						Enemy e = new Enemy(game, s_enemy);
						e.location.set(x * 64, -y * 64);
						enemies.add(e);
					}

					if (Tile.isValidTile(data[x + y * Width])) {

						if ((x > 0 && !Tile.isValidTile(data[x - 1 + y * Width])) || (y > 0 && !Tile.isValidTile(data[x + (y - 1) * Width])) || (x < Width - 1 && !Tile.isValidTile(data[x + 1 + y * Width])) || (y < Height - 1 && !Tile.isValidTile(data[x + (y + 1) * Width])) || (x == Width - 1) || (y == Width - 1) || (x == 0) || (y == 0))
							aabbs.add(new AABB(x * 64, y * 64, x * 64 + 64, y * 64 + 64));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: crash
		}
	}

	public void set(int x, int y, int value) {
		if (x >= Width || y >= Height) {
			return;
		}

		data[x + y * Width] = value;
	}

	public void draw(Display display, int xoffset, int yoffset) {
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {
				int id = data[x + y * Width];

				if (id == Tile.Infected.id)
					display.draw(x * Tile.Width + xoffset, y * Tile.Height - yoffset, Tile.Width, Tile.Height, Tile.Infected.pixels);
				else if (id == Tile.Stone.id)
					display.draw(x * Tile.Width + xoffset, y * Tile.Height - yoffset, Tile.Width, Tile.Height, Tile.Stone.pixels);
				else if (id == Tile.Grass.id)
					display.draw(x * Tile.Width + xoffset, y * Tile.Height - yoffset, Tile.Width, Tile.Height, Tile.Grass.pixels);
				else if (id == Tile.Dirt.id)
					display.draw(x * Tile.Width + xoffset, y * Tile.Height - yoffset, Tile.Width, Tile.Height, Tile.Dirt.pixels);
				else if (id == Tile.Goat.id)
					display.draw(x * Tile.Width + xoffset, y * Tile.Height - yoffset, Tile.Width, Tile.Height, Tile.Goat.pixels);

			}
		}
	}

	public Map() {
		map_name = "";
		aabbs = new ArrayList<AABB>();
		triggers = new ArrayList<Trigger>();
		enemies = new ArrayList<Enemy>();
	}
}
