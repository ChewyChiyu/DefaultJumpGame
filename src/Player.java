import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends GameObject{
	protected static final int RAIDUS = 50;
	protected boolean inAir;
	protected boolean auto;
	protected Player(int x, int y, boolean auto) {
		super(x, y);
		this.auto = auto;
		Thread automated = new Thread(new Runnable(){
			public void run(){
				while(Screen.isRunning){
					think();
					try{
						Thread.sleep(10);
					}catch(Exception e) { }
				}
			}
		});
		if(auto)
			automated.start();
	}
	void think() {
		synchronized(Screen.sprites){
			Platform currentPlat = null;
			boolean  overHeadPlat = false;
			for(int index = 0; index < Screen.sprites.size(); index++){
				GameObject o = Screen.sprites.get(index);
				if(o instanceof Platform){
					if((y+RAIDUS)- o.getY() < 3 && o.getX() < x && o.getX() + Platform.W > x){
						currentPlat = ((Platform) o);
					}
					if(!inAir&& (o.getY() - (y-50))>-10 && (o.getY() - (y-50))<50 && o.getX()-RAIDUS*2 < x+RAIDUS/2 && o.getX() + Platform.W + RAIDUS*2 > x+RAIDUS/2){
						overHeadPlat = true;
					}
				}

			}		
			if(x < Screen.fin.getX() - RAIDUS/2 ){
				dx = 3;
			}
			if( x > Screen.fin.getX() + RAIDUS/2) {
				dx = -3;
			}
			//on edge of platform
			if(currentPlat==null){return;}
			if(overHeadPlat){
				jump();
			}

		}
	}


	@Override
	void draw(Graphics g) {
		if(!auto)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.GRAY);
		g.fillOval(x, y, RAIDUS, RAIDUS);
	}
	void jump(){
		if(inAir){
			return;
		}
		Thread jumping = new Thread(new Runnable(){
			public void run(){
				for(int index = 0; index < 20; index++){
					y-=12;
					try{
						Thread.sleep(5);
					}catch(Exception e) { }
				}
			}
		});
		jumping.start();
	}
}
