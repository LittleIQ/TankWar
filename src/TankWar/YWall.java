package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class YWall {
	// 定义墙的大小
	public final static int width = 20;
	public final static int height = 20;
	// 定义坐标
	int x;
	int y;
	JFrame v;
	// 定义普通墙的存在状态--无敌的
	boolean isLive = true;

	// 画出石墙
	public void drawYWall(Graphics g) {
		Image img1 = new ImageIcon("image/YWall.jpg").getImage();
		g.drawImage(img1, x, y, width, height, v);
	}

	// 构造函数
	public YWall(int x, int y, JFrame v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}
}
