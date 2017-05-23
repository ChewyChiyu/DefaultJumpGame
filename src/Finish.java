import java.awt.Color;
import java.awt.Graphics;

public class Finish extends GameObject{
	protected static final int RAIDUS = 30;
	protected Finish(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y,RAIDUS , RAIDUS);
	}

}
