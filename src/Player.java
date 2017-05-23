import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject{
	protected static final int RAIDUS = 50;
	protected boolean inAir;
	protected Player(int x, int y) {
		super(x, y);
	}

	@Override
	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, RAIDUS, RAIDUS);
	}
	void jump(){
		if(inAir){
			return;
		}
		Thread jumping = new Thread(new Runnable(){
			public void run(){
				for(int index = 0; index < 20; index++){
					y-=15;
					try{
						Thread.sleep(5);
					}catch(Exception e) { }
				}
			}
		});
		jumping.start();
	}
}
