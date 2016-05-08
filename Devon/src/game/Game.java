package game;

import hud.HUD;
import hud.HealthBar;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.*;

public final class Game {
	public ArrayList<Entity> entities;
	private Engine engine;
	public Player player;
	public Map map;
	int currentmap = -1;
	int numlevels = 2;
	private HUD hud;

	private int[] won;
	boolean end_win = false;
	private int[] lost;
	boolean end_lose = false;

	public boolean isKeyDown(int keyCode) {
		return engine.isKeyDown(keyCode);
	}

	public void end(boolean win) {
		if (win) {
			end_win = true;
		} else {
			end_lose = true;
		}
	}

	float time = 0;

	public void tick(float dt) {
		if (end_win || end_lose) {
			time += dt * 10;
			if (time >= 25) {
				engine.gameend = true;
				end_win = end_lose = false;
			}
		} else {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick(dt);
			}
		}
	}

	public void draw(Display display) {
		if (end_win) {
			display.draw(0, 0, 800, 600, won);
		} else if (end_lose) {
			display.draw(0, 0, 800, 600, lost);
		} else {
			map.draw(display, (int) -player.location.x, (int) -player.location.y);
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).draw(display);
			}
			hud.draw(display);
		}
	}

	public void nextMap() {
		++currentmap;
		if (currentmap < numlevels) {
			map.load(this, "/data/maps/map" + currentmap + ".bmp");
			player.location.y = 300;
			player.location.x = -128 - 64;
		} else {
			end(true);
		}
	}

	public void init() {
		// gameplay
		player = new Player(this, Sprite.load("/data/sprites/player.png"));
		player.location.y += 300;
		player.location.x = -128 - 64;

		// hud
		hud = new HUD();
		hud.init(this);
		player.healthBar = new HealthBar("/data/hud/healthbar.png", 10, 10);

		map = new Map();
		nextMap();
	}

	public Game(Engine engine) {
		this.engine = engine;
		entities = new ArrayList<Entity>();

		try {
			BufferedImage img = ImageIO.read(Sprite.class.getResource("/data/hud/win.png"));
			won = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), won, 0, img.getWidth());
			
			img = ImageIO.read(Sprite.class.getResource("/data/hud/lose.png"));
			lost = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), lost, 0, img.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
