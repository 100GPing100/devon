package physics;

import core.Display;
import core.Tile;
import core.Vector2D;
import game.Entity;
import game.Game;

public final class Physics {
	private Game game;

	public Vector2D gravity;

	public void tick(float dt, Display display) {
		Entity e = null;
		for (int i = 0; i < game.entities.size(); i++) {
			e = game.entities.get(i);
			if (e.aabb.bStatic) {
				continue;
			}

			// apply gravity
			e.velocity.x += gravity.x * dt;
			e.velocity.y += gravity.y * dt;

			// terminal velocity
			if (e.velocity.y < -350) {
				e.velocity.y = -350;
			}

			// calculate new location
			e.location.x += e.velocity.x * dt;
			e.location.y += e.velocity.y * dt;

			// check collision with map
			AABB aabb = null;
			int a = 0;
			for (int y = 0; y < game.map.Height; y++) {
				for (int x = 0; x < game.map.Width; x++) {
					if (Tile.isValidTile(game.map.data[x + y * game.map.Width])) {

						if ((x > 0 && !Tile.isValidTile(game.map.data[x - 1 + y * game.map.Width])) || (y > 0 && !Tile.isValidTile(game.map.data[x + (y - 1) * game.map.Width])) || (x < game.map.Width - 1 && !Tile.isValidTile(game.map.data[x + 1 + y * game.map.Width])) || (y < game.map.Height - 1 && !Tile.isValidTile(game.map.data[x + (y + 1) * game.map.Width])) || (x == game.map.Width - 1) || (y == game.map.Width - 1) || (x == 0) || (y == 0)) {

							aabb = game.map.aabbs.get(a++);

							aabb.location.x -= e.location.x;
							aabb.location.y += e.location.y;

							Vector2D point = e.aabb.collides(aabb);
							if (point != null && !(point.x == 0 && point.y == 0)) {
								aabb.location.x += e.location.x;
								aabb.location.y -= e.location.y;

								if (Math.abs(point.x) < Math.abs(point.y)) {
									e.location.x += point.x;
								} else {
									e.location.y += point.y;
									if (point.y > 0) {
										e.landed(x, y, game.map);
										e.onground = true;
									}
								}
							}

							if (point == null || (point.x == 0 && point.y == 0)) {
								aabb.location.x += e.location.x;
								aabb.location.y -= e.location.y;
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < game.entities.size(); i++) {
			e = game.entities.get(i);
			if (e == game.player) {
				continue;
			}

			e.location.y = -e.location.y;
			e.location.x -= game.player.location.x;
			e.location.y += game.player.location.y;

			Vector2D point = game.player.aabb.collides(e.aabb);
			if (point != null && !(point.x == 0 && point.y == 0)) {
				e.location.x += game.player.location.x;
				e.location.y -= game.player.location.y;
				e.location.y = -e.location.y;

				/*
				 * if (Math.abs(point.x) < Math.abs(point.y)) {
				 * game.player.location.x += point.x; } else {
				 * game.player.location.y += point.y; }
				 */

				game.player.touched(dt, e, point);
				e.touched(dt, game.player, point);
			}

			if (point == null || (point.x == 0 && point.y == 0)) {
				e.location.x += game.player.location.x;
				e.location.y -= game.player.location.y;
				e.location.y = -e.location.y;
			}
		}

		/*
		 * game.entities.get(1).location.y = -game.entities.get(1).location.y;
		 * game.entities.get(1).location.x -= game.player.location.x;
		 * game.entities.get(1).location.y += game.player.location.y;
		 * 
		 * Vector2D point =
		 * game.player.aabb.collides(game.entities.get(1).aabb); if (point !=
		 * null && !(point.x == 0 && point.y == 0)) {
		 * game.entities.get(1).location.x += game.player.location.x;
		 * game.entities.get(1).location.y -= game.player.location.y;
		 * game.entities.get(1).location.y = -game.entities.get(1).location.y;
		 * 
		 * if (Math.abs(point.x) < Math.abs(point.y)) { game.player.location.x
		 * += point.x; } else { game.player.location.y += point.y; }
		 * 
		 * game.player.touched(game.entities.get(1));
		 * game.entities.get(1).touched(game.player); }
		 * 
		 * if (point == null || (point.x == 0 && point.y == 0)) {
		 * game.entities.get(1).location.x += game.player.location.x;
		 * game.entities.get(1).location.y -= game.player.location.y;
		 * game.entities.get(1).location.y = -game.entities.get(1).location.y; }
		 */
		return;
	}

	public void init() {
		gravity = new Vector2D(0, -7500);
	}

	public void free() {
	}

	public Physics(Game game) {
		this.game = game;
	}
}
