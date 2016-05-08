package hud;

import core.Display;

public final class HealthBar extends HUDElement {
	public int value;
	
	public void draw(Display display) {
		display.draw(0,0, value, width, height, height, pixels);
	}

	public HealthBar(String texture, int x, int y) {
		super(texture, x, y);
		value = 100;
	}
}
