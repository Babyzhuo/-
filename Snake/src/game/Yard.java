package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Yard extends Frame {
	PaintThread paintThread = new PaintThread();
	private boolean gameOver = false;
	public  static final int rows = 30;
    public  static final int cols = 30;
	public static final int block_size = 15;
	private Font fontGameOver = new Font("宋体",Font.BOLD,50);
	private int score = 0;
	Snake s = new Snake(this);
	Egg e = new Egg();
	Image offScreenImage = null;
	public void launch() {
		this.setLocation(200,200);
		this.setSize(cols*block_size, rows*block_size);
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		new Thread(paintThread).start();;
	}
	
	public static void main(String[] args) {
		Yard yard = new Yard();
		yard.launch();
		yard.setTitle("Gluttonous Snake");
	}
	public void stop() {
		gameOver = true;
	}
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, cols*block_size, rows*block_size);
		g.setColor(Color.DARK_GRAY);
		//画出横线
		for(int i=1;i<rows;i++) {
			g.drawLine(0, block_size*i, cols*block_size,block_size*i );
		}
		for(int i=1;i<cols;i++) {
			g.drawLine( block_size*i,0, block_size*i,block_size*rows);
		}
		g.setColor(Color.YELLOW);
		g.drawString("Score:"+score, 10, 60);
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("游戏结束!", 120, 180);
			paintThread.gameOver();
		}
		
		g.setColor(c);
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
	}
	@Override
	public void update(Graphics g) {
		if(offScreenImage ==null) {
			offScreenImage = this.createImage(cols*block_size,rows*block_size);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage,0,0,null);
	}
	private class PaintThread implements Runnable{
		private boolean running = true;
		@Override
		public void run() {
			while(running) {
				repaint();
				try {
					Thread.sleep(100);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public void gameOver() {
			running = false;
		}
		
	}
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
