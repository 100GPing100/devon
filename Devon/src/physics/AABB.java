package physics;

import core.Display;
import core.Vector2D;

public final class AABB {
	public Vector2D location;
	public Vector2D min, max;
	public boolean bStatic;

	public void debug_draw(Display display, int colour) {
		if (display == null) {
			return;
		}
		display.drawBox((int) (location.x + min.x), (int) (location.y + min.y), (int) (location.x + max.x), (int) (location.y + max.y), colour);
	}

	public Vector2D collides(AABB other) {
		float x, y;
		
		max.subtract(other.location);
		min.subtract(other.location);

		if (max.x < other.min.x || other.max.x < min.x) {
			max.add(other.location);
			min.add(other.location);
			return null;
		} else {
			float center = (min.x + max.x) / 2.0f;
			float other_center = (other.min.x + other.max.x) / 2.0f;

			if (center < other_center) {
				x = max.x - other.min.x;
				x *= -1;
			} else {
				x = other.max.x - min.x;
			}
		}

		if (max.y < other.min.y || other.max.y < min.y) {
			max.add(other.location);
			min.add(other.location);
			return null;
		} else {
			float center = (min.y + min.y) / 2.0f;
			float other_center = (other.min.y + other.max.y) / 2.0f;

			if (center < other_center) {
				y = max.y - other.min.y;     
			} else {
				y = other.max.y - min.y;
				y *= -1;
			}
		}
		
		max.add(other.location);
		min.add(other.location);

		return new Vector2D(x, y);
	}

	public AABB(float xmin, float ymin, float xmax, float ymax) {
		min = new Vector2D(xmin, ymin);
		max = new Vector2D(xmax, ymax);
		location = new Vector2D(0, 0);
	}
}
