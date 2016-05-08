package core;

public final class Vector2D {
	public float x, y;

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public void normalize() {
		float len = length();
		x /= len;
		y /= len;
	}
	
	public void scale(float s) {
		x *= s;
		y *= s;
	}
	
	public void add(Vector2D v) {
		x += v.x;
		y += v.y;
	}
	
	public void subtract(Vector2D v) {
		x -= v.x;
		y -= v.y;
	}
	
	public float dot(Vector2D v) {
		return (x * v.x + y * v.y);
	}
	
	public float dist(Vector2D v) {
		float xx = x - v.x;
		float yy = y - v.y;
		return (float) Math.sqrt(xx * xx + yy * yy);
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public void print() {
		System.out.println("(" + x + ", " + y + ")");
	}

	public Vector2D() {
		x = y = 0;
	}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D v) {
		x = v.x;
		y = v.y;
	}
}
