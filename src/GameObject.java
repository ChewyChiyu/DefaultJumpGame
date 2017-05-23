import java.awt.Graphics;

public abstract class GameObject {
	int x, y, dx, dy;
	protected GameObject(int x, int y){
		this.x = x;
		this.y = y;
	}
	int getX(){
		return x;
	}
	int getY(){
		return y;
	}
	int getDX(){
		return dx;
	}
	int getDY(){
		return dy;
	}
	void setX(int inc){
		x += inc;
	}
	void setY(int inc){
		y += inc;
	}
	void setDX(int inc){
		dx = inc;
	}
	void setDY(int inc){
		dy = inc;
	}
	abstract void draw(Graphics g);
}
