package TankWar;

import java.awt.Graphics;
import java.util.Vector;

public class Walls {
	public int[][] a = new int[20][20];
	public Vector<RWall> RWalls = new Vector<RWall>();
	public Vector<YWall> YWalls = new Vector<YWall>();
	private int height = 20;
	private int width = 20;
	private Play v;

	public Walls(Play x) {
		this.v = x;
//		for (int i = 0; i < 20; i++) {
//			for (int j = 0; j < 20; j++) {
//				if (i == 19 || i == 0 || j == 19 || j == 0)
//					YWalls.add(new YWall(i*width, j*height, v));
//				if (i == 10)
//					RWalls.add(new RWall(i*width, j*height, v));
//			}
//		}
		for (int i = 0; i < x.map.length; i++) {
			if (x.map[i] == '1') {
				YWalls.add(new YWall((int) (i % width) * width, (int) (i / height) * height, v));
			} else if (x.map[i] == '2') {
				RWalls.add(new RWall((int) (i % width) * width, (int) (i / height) * height, v));
			} 
		}
		// a[0][0] = 2;
	}

	public void draw(Graphics g) {
		// 画出普通墙
		for (int i = 0; i < this.RWalls.size(); i++) {
			RWall RWall = this.RWalls.get(i);
			// 如果普通墙存在状态为真就画出来
			if (RWall.isLive) {
				RWall.drawRWall(g);
			} else {
//				this.RWalls.remove(RWall);
			}
		}

		// 画出石墙
		// 外围石墙
		for (int i = 0; i < this.YWalls.size(); i++) {
			this.YWalls.get(i).drawYWall(g);
		}
	}
}
