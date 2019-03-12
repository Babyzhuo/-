package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Egg {
	int row,col;
	int w = Yard.block_size;
	int h = Yard.block_size;
	private static Random r = new Random();
	private Color color = Color.GREEN;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

	public Egg(int row,int col) {
		this.row = row;
		this.col = col;
	}
	public Egg() {
		this(r.nextInt(Yard.rows-2)+2,r.nextInt(Yard.cols));
	}
	public void reAppear() {
		this.row = r.nextInt(Yard.rows-2)+2;
		this.col = r.nextInt(Yard.cols);
	}
	public Rectangle getRect() {
		return new Rectangle(Yard.block_size*col,Yard.block_size*row,w,h);
	}
	public void draw(Graphics g) {
		Color c= g.getColor();
		g.setColor(color);
		g.fillOval(Yard.block_size*col,Yard.block_size*row,w,h);
		g.setColor(c);
		if(color == Color.GREEN) {
			color = Color.RED;
		}
		else {
			color = Color.GREEN;
		}

	}
}
