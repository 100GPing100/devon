package game;

import physics.AABB;
import core.*;

public class Entity {
	protected final Game game;
	public Sprite sprite;
	public Vector2D velocity;
	public Vector2D location;
	public AABB aabb;
	public boolean onground;

	public void draw(Display display) {
		if (sprite != null) {
			sprite.draw(display, (int) (location.x - game.player.location.x), (int) (-location.y + game.player.location.y));
		}
	}

	public void tick(float dt) {
	}

	public void touched(float dt, Entity other, Vector2D vector) {
	}

	public final void delete() {
		game.entities.remove(this);
	}

	public void takeDamage(float amount) {
	}

	public void landed(int x, int y, Map map) {
	}

	public Entity(Game game) {
		this.game = game;
		game.entities.add(this);

		sprite = null;
		velocity = new Vector2D();
		location = new Vector2D();

		aabb = new AABB(0, 0, 64, 64);
		aabb.location = location;
		
		onground = false;
	}

	public Entity(Game game, Sprite sprite) {
		this.game = game;
		game.entities.add(this);

		this.sprite = sprite;
		velocity = new Vector2D();
		location = new Vector2D();

		aabb = new AABB(0, 0, 64, 64);
		aabb.location = location;

		onground = false;
	}
}
