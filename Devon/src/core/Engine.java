package core;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import physics.Physics;
import game.Game;

public final class Engine {
	private Display display;
	private Input input;
	private Physics physics;
	private Game game;

	private boolean exit = false;
	private boolean enter = false;
	private boolean inmenu = true;
	private int[] pixels;
	
	public boolean gameend = false;

	public void mainLoop() {
		init();
		
		try {
			BufferedImage img = ImageIO.read(Sprite.class.getResource("/data/hud/menu.png"));
			pixels = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		while (!exit && !display.isCloseRequested()) {
			display.clear();
			display.draw(0, 0, 800, 600, pixels);
			display.flush();

			if (enter) {
				enter = false;
				inmenu = false;
				gameend = false;
				long last_tick = System.currentTimeMillis();
				float delta_time = 0;
				while (!display.isCloseRequested() && !gameend) {
					delta_time = (System.currentTimeMillis() - last_tick) * 0.001f;
					last_tick = System.currentTimeMillis();
	
					display.clear();
					game.tick(delta_time);
					physics.tick(delta_time, display);
	
					game.draw(display);
					display.flush();
				}
				inmenu = true;
			}
		}

		free();
	}

	public void keyPressed(int keyCode) {
		if (inmenu) {
			if (keyCode == KeyEvent.VK_ESCAPE) {
				exit = true;
			} else if (keyCode == KeyEvent.VK_ENTER) {
				enter = true;
			}
		} else {
			game.player.keyPressed(keyCode);
		}
	}

	public void keyReleased(int keyCode) {
		if (inmenu) {
		} else {
			game.player.keyReleased(keyCode);
		}
	}

	public boolean isKeyDown(int keyCode) {
		return input.isKeyDown(keyCode);
	}

	private void init() {
		display = new Display(800, 600, "Devon");
		input = new Input(this);
		game = new Game(this);
		physics = new Physics(game);

		game.init();
		display.init(input);
		physics.init();
		Tile.loadTileSet("/data/tilesets/default.png");
	}

	private void free() {
		display.free();
	}

	public static void main(String[] args) {
		new Engine().mainLoop();
	}
}
