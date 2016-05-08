package hud;

import game.Game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Display;

public class HUDElement {
	public static HUD hud;
	public static Game game;
	
	protected int width, height;
	protected int[] pixels;
	public int x, y;
	
	public void draw(Display display) {
		display.draw((int)game.player.location.x + x, (int)game.player.location.y - y, width, height, pixels);
	}
	
	public final void delete() {
		hud.elements.remove(this);
	}
	
	public HUDElement(String texture, int x, int y) {
		try {
			BufferedImage img = ImageIO.read(HUDElement.class.getResource(texture));
			width = img.getWidth();
			height = img.getHeight();
			pixels = new int[width * height];
			img.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: crash
		}
		
		hud.elements.add(this);
	}
}
