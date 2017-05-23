import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable{
	Thread loop;
	boolean isRunning;
	final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width/2;
	final int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
	ArrayList<GameObject> sprites = new ArrayList<GameObject>(); 
	Player thing = new Player((int)(screenW*.1),(int)(screenH*.7));
	protected Screen(){
		panel();
		platforms();
		keys();
		start();
	}
	void platforms(){
		int xBuffer = 0;
		int yBuffer = screenH-300;
		sprites.add(new Platform(0,screenH-200));
		for(int index = 0; index < 7; index++){
			if(index==6){
				sprites.add(new Finish(xBuffer+Platform.W/2,yBuffer-Finish.RAIDUS));
			}
			sprites.add(new Platform(xBuffer,yBuffer));
			switch((int)(Math.random()*4)){
			case 0:
				if(xBuffer<=screenW-300-Platform.W){
					xBuffer+=300;
				}else{
					xBuffer-=100;
				}
				yBuffer-=50;
				break;
			case 1:
				if(xBuffer>=300){
					xBuffer-=300;
				}else{
					xBuffer+=100;
				}
				yBuffer-=90;
				break;
			case 2:
				if(xBuffer>=200){
					xBuffer-=200;
				}else{
					xBuffer+=100;
				}
				yBuffer-=90;
				break;
			case 3:
				if(xBuffer<=screenW-200-Platform.W){
					xBuffer+=200;
				}else{
					xBuffer-=100;
				}
				yBuffer-=50;
				break;
			}
		}
	}
	void keys(){
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");
		getInputMap().put(KeyStroke.getKeyStroke("released A"), "rA");
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "rD");
		getInputMap().put(KeyStroke.getKeyStroke("W"), "W");

		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thing.setDX(-5);
		}});
		getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thing.setDX(0);
		}});
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thing.setDX(5);
		}});
		getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thing.setDX(0);
		}});
		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thing.jump();
		}});


	}
	synchronized void start(){
		sprites.add(thing);

		loop = new Thread(this);
		isRunning = true;
		loop.start();
		manageThreads();
	}
	void manageThreads(){
		Thread boundCheck = new Thread(new Runnable(){
			public void run(){
				while(isRunning){
					//walls
					if(thing.getX()<=0){
						thing.setX(5);
					}
					if(thing.getX()>=screenW-Player.RAIDUS){
						thing.setX(-5);
					}
					//win / lose
					for(int index = 0; index < sprites.size(); index++){
						GameObject o = sprites.get(index);
						if(o instanceof Player){
							for(int index2 = 0; index2 < sprites.size(); index2++){
								GameObject o2 = sprites.get(index2);
								if(o2 instanceof Finish){
									int o2x1 = o2.getX(), o2x2 = o2.getX() + Finish.RAIDUS;
									int o2y1 = o2.getY(), o2y2 = o2.getY() + Finish.RAIDUS;
									if(o.getX() > o2x1 && o.getX() < o2x2 && o.getY()+Player.RAIDUS/2 > o2y1 && o.getY()+Player.RAIDUS/2 < o2y2){
										reset();
									}
								}
							}
						}
					}
					if(thing.getY()>screenH){
						reset();
					}
					try{
						Thread.sleep(1);
					}catch(Exception e) { }
				}
			}
		});
		boundCheck.start();
	}
	void reset(){
		sprites.clear();
		platforms();
		thing = new Player((int)(screenW*.1),(int)(screenH*.5));
		sprites.add(thing);
	}
	void panel(){
		JFrame frame = new JFrame("DEFAULT GAME");
		frame.add(this);
		frame.setPreferredSize(new Dimension(screenW,screenH));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	@Override
	public void run() {
		while(isRunning){
			update();
			try{
				Thread.sleep(10);
			}catch(Exception e) { }
		}
	}
	void update(){
		physics();
		move();
		repaint();
	}
	void physics(){
		//gravity of player
		for(int index = 0; index < sprites.size(); index++){
			GameObject o = sprites.get(index);
			if(o instanceof Player){
				((Player)o).inAir = true;
				for(int index2 = 0; index2 < sprites.size(); index2++){
					GameObject o2 = sprites.get(index2);
					if(o2 instanceof Platform){
						int o2x1 = o2.getX(), o2x2 = o2.getX() + Platform.W;
						int o2y1 = o2.getY(), o2y2 = o2.getY() + Platform.H;
						if(o.getX()+Player.RAIDUS/2 > o2x1 && o.getX()+Player.RAIDUS/2 < o2x2 && o.getY()+Player.RAIDUS > o2y1 && o.getY()+Player.RAIDUS < o2y2){
							((Player)o).inAir = false;
						}
					}
				}
				if(((Player)o).inAir){
					o.setDY(8);
				}else{
					o.setDY(0);
				}
				
			}
		}
	}
	void move(){
		for(int index = 0; index < sprites.size(); index++){
			GameObject o = sprites.get(index);
			o.setX(o.getDX());
			o.setY(o.getDY());
		}
	}
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);
		for(int index = 0; index < sprites.size(); index++){
			GameObject o = sprites.get(index);
			o.draw(g);
		}
	}
}
