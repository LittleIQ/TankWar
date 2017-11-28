package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.ImageIcon;

public class Tank {
	public int width = 20;
	public int height = 20;
	public int x;
	public int y;
	public Play v;
	public Tank enemy;
	public boolean isLive;
	public int blood = 3;
	public String ID = null;
	private String Direct = "rt";
	private Graphics vg;
	private int speed;
	public Home myhome;
	public Vector<Bullet> Bullets = new Vector<Bullet>();
	private int oldX;
	private int oldY;

	public Tank(int x, int y, Play v, String i) {
		this.x = x;
		this.y = y;
		this.v = v;
//		this.vg = v.mainJ.getGraphics();
		this.speed = 10;
		this.isLive = true;
		this.ID = i;
	}

	public void setHome(int posX,int posY){
		this.myhome = new Home(posX,posY,this);
	}

	public void setEnemy(Tank en) {
		this.enemy = en;
	}

	public void moveDirect(String d) {
		Direct = d;
		Image img1 = new ImageIcon("image/tank" + d + ".jpg").getImage();
		v.mainJ.getGraphics().drawImage(img1, x, y, width, height, v);
	}

	public void move() {
		if (isLive) {
			settoOldDirect();
//			Image img1 = new ImageIcon("image/tank" + Direct + ".jpg").getImage();
			switch (Direct) {
			case "up":
				if (y > 0 && !this.isTouchWall() && !this.isTouchTank()) {
					y -= speed;
					if (this.isTouchWall() || this.isTouchTank()) {
						this.changtoOldDirect();
					}
				}
				break;
			case "dw":
				if (y < 760 && !this.isTouchWall() && !this.isTouchTank()) {
					y += speed;
					if (this.isTouchWall() || this.isTouchTank()) {
						this.changtoOldDirect();
					}
				}
				break;
			case "lt":
				if (x > 0 && !this.isTouchWall() && !this.isTouchTank()) {
					x -= speed;
					if (this.isTouchWall() || this.isTouchTank()) {
						this.changtoOldDirect();
					}
				}
				break;
			case "rt":
				if (x < 760 && !this.isTouchWall() && !this.isTouchTank()) {
					x += speed;
					if (this.isTouchWall() || this.isTouchTank()) {
						this.changtoOldDirect();
					}
				}
				break;
			}
//			vg.drawImage(img1, x, y + 50, 40, 40, v);
		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < Bullets.size(); i++) {
			Bullet bullet = Bullets.get(i);
			if (bullet != null && bullet.islive == true) {
				bullet.draw(g);
			} else {
				Bullets.remove(i);
			}
		}
		if(this.myhome.isLive){
			this.myhome.HomeDraw(g);
		}
		if (this.isLive&&this.myhome.isLive) {
			Image img1 = new ImageIcon("image/tank" + Direct + ".jpg").getImage();
			g.drawImage(img1, x, y, width, height, v);
		}
	}

	public void shot() {
		if (this.isLive) {
			Bullet bu = new Bullet(this);
			Bullets.add(bu);
			bu.start();
		}
	}

	public boolean isTouchRWall() {
		for (int i = 0; i < v.wall.RWalls.size(); i++) {
			RWall rWall = v.wall.RWalls.get(i);
			if (this.getRect().intersects(rWall.getRect())) {
				return true;
			}
		}
		return false;
	}

	public boolean isTouchYWall() {
		for (int i = 0; i < v.wall.YWalls.size(); i++) {
			YWall yWall = v.wall.YWalls.get(i);
			if (this.getRect().intersects(yWall.getRect())) {
				return true;
			}
		}
		return false;
	}

	public boolean isTouchWall() {
		return this.isTouchRWall() || this.isTouchYWall();
	}

	public boolean isTouchHome() {
		if (this.getRect().intersects(v.myTankA.myhome.getRect())||this.getRect().intersects(v.myTankB.myhome.getRect())) {
			return true;
		}
		return false;
	}

	public boolean isTouchTank() {
		if (this.getRect().intersects(enemy.getRect()))
			return true;
		else
			return false;
	}

	public void settoOldDirect() {
		this.oldX = x;
		this.oldY = y;
	}

	public void changtoOldDirect() {
		x = oldX;
		y = oldY;
	}

	public void remove(Bullet x) {
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public Graphics getGraphics() {
		return vg;
	}

	public String getDirect() {
		return Direct;
	}
}