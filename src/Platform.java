import java.awt.Color;
import java.awt.Graphics;

public class Platform extends GameObject{
	protected static final int H = 10;
	protected static final int W = 300;
	protected Platform(int x, int y) {
		super(x, y);
	}

	@Override
	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, W, H);
	}

}
