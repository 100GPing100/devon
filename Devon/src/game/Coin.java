package game;

import physics.AABB;
import core.Sound;
import core.Sprite;
import core.Vector2D;

public class Coin extends Entity {
	private static Sound sound0 = Sound.load("/data/sounds/coin.wav");
	private static Sound sound1 = Sound.load("/data/sounds/coin.wav");
	private float time = 0;

	@Override
	public void tick(float dt) {
		time += dt * 100;
		if (time >= 5) {
			++sprite.frame;
			if (sprite.frame == 10) {
				sprite.frame = 0;
			}
			time = 0;
		}
	}

	@Override
	public void touched(float dt, Entity e, Vector2D vector) {
		if (e == game.player) {
			System.out.println("+1");
			if (!sound0.isPlaying()) {
				sound0.play();
			} else if (!sound1.isPlaying()) {
				sound1.play();
			}
			delete();
		}
	}

	public Coin(Game game, Sprite sprite) {
		super(game, sprite);

		aabb = new AABB(25, 25, 25+15, 25+15);
		aabb.location = location;
		aabb.bStatic = true;
	}

}
