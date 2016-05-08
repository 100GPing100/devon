package game;

import core.Vector2D;

public final class Trigger extends Entity {
	@Override
	public void touched(float dt, Entity other, Vector2D vector) {
		if (other == game.player) {
			game.nextMap();
		}
	}
	
	public Trigger(Game game) {
		super(game);
	}
	
}
