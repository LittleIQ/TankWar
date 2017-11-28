package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Bullet extends Thread {
	public final static int width = 5;
	public final static int height = 5;
	private int x;
	private int y;
	private JFrame v;
	public boolean islive;
	private Tank t;
	private String Direct = "rt";
	private int speed;
	
	public Bullet(Tank t) {
		this.x = t.x;
		this.y = t.y;
		this.islive = true;
		this.t = t;
		this.Direct = t.getDirect();
		switch(Direct)
		{
		case "up":
			x = t.x + t.width/2;
			break;
		case "dw":
			x=t.x+ t.width/2;
			y=t.y+t.width;
			break;
		case "lt":
			y=t.y+t.width/2;
			break;
		case "rt":
			x=t.x+t.width;
			y=t.y+t.width/2;
			break;
		}
		this.speed = 20;
	}

	public void move() {
		switch(Direct)
		{
		case "up":
			if(y>0)
			{
				y-=speed;
			}
			break;
		case "dw":
			if(y<760)
			{
				y+=speed;
			}
			break;
		case "lt":
			if(x>0)
			{
				x-=speed;
			}
			break;
		case "rt":
			if(x<760)
			{
				x+=speed;
			}
			break;
		}
	}
	
	public void draw(Graphics g) {
		Image img1 = new ImageIcon("image/RWall.jpg").getImage();
		g.drawImage(img1, x, y, 5, 5, v);
	}
	
	public boolean isTouchRWall() {
		for (int i = 0; i < t.v.wall.RWalls.size(); i++) {
			RWall rWall = t.v.wall.RWalls.get(i);
			if (this.getRect().intersects(rWall.getRect())) {
				rWall.isLive=false;
				t.v.wall.RWalls.remove(i);//因墙数组IO阻塞问题（闪烁）将wall移除语句移至此处
				return true;
			}
		}
		return false;
	}

	public boolean isTouchYWall() {
		for (int i = 0; i < t.v.wall.YWalls.size(); i++) {
			YWall yWall = t.v.wall.YWalls.get(i);
			if (this.getRect().intersects(yWall.getRect())) {
				return true;
			}
		}
		return false;
	}

	public boolean isTouchTank() {
		if (this.getRect().intersects(t.enemy.getRect())) {
			if(t.enemy.blood>0){
				t.enemy.blood--;
			}
			if(t.enemy.blood==0) {
				t.enemy.isLive = false;
			}
			return true;
		}
		else
			return false;
	}

	public boolean ismyHome() {
		if (this.getRect().intersects(t.myhome.getRect())) {
			t.isLive = false;
			return true;
		}
		else
			return false;
	}

	public boolean isenemyHome() {
		if (this.getRect().intersects(t.enemy.myhome.getRect())) {
			t.enemy.isLive = false;
			return true;
		}
		else
			return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(66);
			} catch (Exception e) {
				e.printStackTrace();
			}
			move();
			if(this.isTouchYWall()||this.isTouchRWall()||this.isTouchTank()||this.ismyHome()||this.isenemyHome()) {
				this.islive = false;
				break;
			}
		}
	}
}
