package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Input implements KeyListener {
	private Engine engine;
	private boolean[] key_state = new boolean[256];
	
	public boolean isKeyDown(int keyCode) {
		return (keyCode < 256 && keyCode > -1 && key_state[keyCode]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (key_state[e.getKeyCode()] == false) {
			engine.keyPressed(e.getKeyCode());
			key_state[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (key_state[e.getKeyCode()] == true) {
			engine.keyReleased(e.getKeyCode());
			key_state[e.getKeyCode()] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public Input(Engine engine) {
		this.engine = engine;
	}
}
