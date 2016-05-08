package game;

import core.Display;
import core.Sound;
import core.Sprite;
import core.Vector2D;

public class Enemy extends Entity {
	private static final Sound alert = Sound.load("/data/sounds/alert.wav");
	float speed;
	float damage;
	float sight;
	boolean balert;
	boolean dead;

	@Override
	public void draw(Display display) {
		if (dead) {
			sprite.frame = 2;
		}
		super.draw(display);
	}

	@Override
	public void touched(float dt, Entity other, Vector2D vector) {
		if (dead) {
			return;
		}

		if (other == game.player) {
			if (vector.y > 0 && Math.abs(vector.y) < Math.abs(vector.x)) {
				sprite.frame = 2;
				velocity.set(0, 0);
				dead = true;
				other.velocity.y += 1400;
			} else {
				other.takeDamage(damage);
				other.velocity.y += 1750;
			}
		}
	}

	@Override
	public void tick(float dt) {
		if (dead) {
			sprite.frame = 2;
			return;
		}

		if (location.dist(game.player.realLocation()) < sight) {
			sprite.frame = 1;

			Vector2D loc = game.player.realLocation();

			if (loc.x - location.x < -10) {
				velocity.x = -speed;
				if (onground) {
					velocity.y = 2;
				}
			} else if (loc.x - location.x > 32) {
				velocity.x = speed;
				if (onground) {
					velocity.y = 2;
				}
			}
			if (loc.y > location.y && onground) {
				velocity.y = 350;
				onground = false;
			}

			if (balert == false) {
				alert.play();
				balert = true;
			}
		} else {
			sprite.frame = 0;
			balert = false;
		}
	}

	public Enemy(Game game, Sprite sprite) {
		super(game, sprite);

		aabb.min.set(13, 40);
		aabb.max.set(13 + 35, 40 + 24);

		speed = 250;
		damage = 25;
		sight = 200;
		balert = dead = false;
	}
}
