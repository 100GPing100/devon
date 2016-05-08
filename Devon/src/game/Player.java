package game;

import hud.HealthBar;

import java.awt.event.KeyEvent;

import physics.AABB;

import core.*;

public final class Player extends Entity {
	private static final Sound hurt = Sound.load("/data/sounds/hurt.wav");
	private static final Sound jump = Sound.load("/data/sounds/jump.wav");
	private static final Sound infect = Sound.load("/data/sounds/infect.wav");
	private int display_width = 0, display_height = 0;
	public HealthBar healthBar;

	public float speed;
	public float health;

	@Override
	public void tick(float dt) {
	}
	
	@Override
	public void touched(float dt, Entity other, Vector2D vector) {
	}

	@Override
	public void draw(Display display) {
		sprite.draw(display, display.Width / 2 - 32, display.Height / 2 - 32);
		display_width = display.Width;
		display_height = display.Height;
	}

	@Override
	public void takeDamage(float amount) {
		health -= amount;
		healthBar.value = (int) health;

		hurt.play();
		
		if (health <= 0) {
			game.end(false);
		}
	}

	@Override
	public void landed(int x, int y, Map map) {
		if (map.data[x + y * map.Width] == Tile.Grass.id) {
			map.set(x, y, Tile.Infected.id);
			infect.play();
		}
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			velocity.x -= speed;
			sprite.frame = 1;
			break;
		case KeyEvent.VK_D:
			velocity.x += speed;
			sprite.frame = 2;
			break;
		case KeyEvent.VK_W:
		case KeyEvent.VK_SPACE:
			if (onground) {
				velocity.y += 1750;
				onground = false;
				jump.play();
			}
			break;
		}
	}

	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			velocity.x += speed;
			break;
		case KeyEvent.VK_D:
			velocity.x -= speed;
			break;
		}
	}

	public Vector2D realLocation() {
		return new Vector2D(location.x + display_width / 2, location.y - display_height / 2);
	}

	public Player(Game game, Sprite sprite) {
		super(game, sprite);
		aabb = new AABB(800 / 2 - 32, 600 / 2 - 32, 800 / 2 + 32, 600 / 2 + 32);
		aabb.location = new Vector2D();

		speed = 350;
		health = 100;
	}
}
