package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	
	private Node n = new Node(20,30,Direction.Left);
	private Yard y;
	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case Left:
			node = new Node(tail.row,tail.col+1,tail.dir);
			break;
		case Right:
			node = new Node(tail.row,tail.col-1,tail.dir);
			break;
		case Upper:
			node = new Node(tail.row+1,tail.col,tail.dir);
			break;
		case Bottom:	
			node = new Node(tail.row-1,tail.col,tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node ;
		size++;
	}
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case Left:
			node = new Node(head.row,head.col-1,head.dir);
			break;
		case Right:
			node = new Node(head.row,head.col+1,head.dir);
			break;
		case Upper:
			node = new Node(head.row-1,head.col,head.dir);
			break;
		case Bottom:	
			node = new Node(head.row+1,head.col,head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size++;
		
	}
	public void draw(Graphics g) {
		if(size<=0) {
			return;
		}
		move();
		for(Node n = head;n!=null;n=n.next) {
			n.draw(g);
		}
		
	}
	
	private void move() {
		addToHead();
		deleteFromTail();
		checkDead();
		
	}

	private void checkDead() {
		if(head.row<2 || head.col<0 || head.row>Yard.rows || head.col>Yard.cols) {
			y.stop();
		}
		for(Node n = head.next;n!=null;n = n.next) {
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
		
	}
	private void deleteFromTail() {
		if(size==0) {
			return;
		}
		tail = tail.prev;
		tail.next = null;
	}

	private class Node{
		int w = Yard.block_size;
		int h = Yard.block_size;
		int row,col;
		Direction dir = Direction.Left;
		Node next = null;
		Node prev = null;
		Node(int row, int col,Direction dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		void draw(Graphics g) {
			Color c= g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(Yard.block_size*col,Yard.block_size*row,w,h);
			g.setColor(c);
		}

	}
	public void eat(Egg e) {
		if(this.getRect().intersects(e.getRect())) {
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore()+5);
		}
	}
	
	private Rectangle getRect() {
		return new Rectangle(Yard.block_size*head.col,Yard.block_size*head.row,head.w,head.h);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			if(head.dir != Direction.Right) 
			    head.dir = Direction.Left;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Direction.Left) 
			    head.dir = Direction.Right;
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Direction.Bottom) 
				head.dir = Direction.Upper;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Direction.Upper) 
				head.dir = Direction.Bottom;
			break;
			
		}
	}
}
