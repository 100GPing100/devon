package hud;

import game.Game;

import java.util.ArrayList;

import core.Display;

public final class HUD {
	public ArrayList<HUDElement> elements;
	
	public void draw(Display display) {
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(display);
		}
	}
	
	public void init(Game game) {
		HUDElement.hud = this;
		HUDElement.game = game;
	}
	
	public HUD() {
		elements = new ArrayList<HUDElement>();
	}
}
