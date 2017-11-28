package TankWar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.*;

public class Play extends JFrame implements ActionListener, KeyListener, Runnable {
	private static final long serialVersionUID = 1L;
	// 定义显示屏幕的宽度和高度
	public static final int screenwidth = 400;
	public static final int screenheight = 400;
	protected Tank myTankA;
	protected Tank myTankB;
	public Walls wall;
	public Accept acc;
	public boolean exit;
	public mainJP mainJ;
	public JPanel inform;
	public String ip;
	public String me;
	public char[] map;

	public Play(String ip, String me) {
		this.ip = ip;
		this.me = me;
		File f = new File("map.txt");
		map = this.readFile(f).toCharArray();
		int Ax = 0, Bx = 0, Ay = 0, By = 0, AHx = 0, BHx = 0, AHy = 0, BHy = 0;
		int countT = 0;
		int countH = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i] == '3' && countT == 0) {
				Ax = (int) (i % 20) * 20;
				Ay = (int) (i / 20) * 20;
				countT++;
			} else if (map[i] == '3') {
				Bx = (int) (i % 20) * 20;
				By = (int) (i / 20) * 20;
			}
			if (map[i] == '4' && countH == 0) {
				AHx = (int) (i % 20) * 20;
				AHy = (int) (i / 20) * 20;
				countH++;
			} else if (map[i] == '4') {
				BHx = (int) (i % 20) * 20;
				BHy = (int) (i / 20) * 20;
			}
		}
		this.setBounds(0, 0, screenwidth, screenheight + 100);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JFrame x = this;
		this.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void windowClosing(WindowEvent we) {
				setString("df");
				x.dispose();
				System.exit(0);
			}
		});
		this.setTitle("Tank");
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.addKeyListener(this);
		if (me == "A") {
			this.myTankA = new Tank(Ax, Ay, this, "A");
			this.myTankB = new Tank(Bx, By, this, "B");
			this.myTankA.setEnemy(myTankB);
			this.myTankB.setEnemy(myTankA);
			this.myTankA.setHome(AHx, AHy);
			this.myTankB.setHome(BHx, BHy);
		} else {
			this.myTankB = new Tank(Ax, Ay, this, "B");
			this.myTankA = new Tank(Bx, By, this, "A");
			this.myTankA.setEnemy(myTankB);
			this.myTankB.setEnemy(myTankA);
			this.myTankB.setHome(AHx, AHy);
			this.myTankA.setHome(BHx, BHy);
		}
		this.wall = new Walls(this);
		this.exit = false;
		this.mainJ = new mainJP(this);
		this.mainJ.setBounds(0, 0, screenwidth, screenheight);
		JLabel Ablood = new JLabel();
		JLabel Bblood = new JLabel();
		Ablood.setBounds(0, 0, 200, 50);
		Bblood.setBounds(200, 0, 200, 50);
		this.inform = new JPanel();
		this.inform.setBounds(0, 400, 400, 50);
		this.inform.setLayout(null);
		this.inform.add(Ablood);
		this.inform.add(Bblood);
		this.add(this.mainJ);
		this.add(this.inform);
		this.setVisible(true);
		acc = new Accept(this.myTankB, this);
		acc.start();
		// Image img1 = new ImageIcon("image/tankrt.jpg").getImage();
		// this.getGraphics().drawImage(img1, 0, 50, 40, 40, this);
	}

	public void run() {
		// 刷新界面
		while (!this.exit) {
			try {
				Thread.sleep(66);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.inform.removeAll();
			String Ainfo = "MBlood:";
			String Binfo = "EBlood:";
			JLabel Ablood = new JLabel(Ainfo + String.valueOf(this.myTankA.blood));
			JLabel Bblood = new JLabel(Binfo + String.valueOf(this.myTankB.blood));
			Ablood.setFont(new Font("Arial", Font.BOLD, 30));
			Bblood.setFont(new Font("Arial", Font.BOLD, 30));
			Ablood.setBounds(0, 0, 200, 50);
			Bblood.setBounds(200, 0, 200, 50);
			this.inform.add(Ablood);
			this.inform.add(Bblood);
			this.inform.validate();
			this.inform.repaint();
			this.mainJ.draw();
			if (!this.myTankB.isLive) {
				setString("df");
				JOptionPane.showMessageDialog(null, "胜利");
				break;
			}
			if (!this.myTankA.isLive) {
				setString("df");
				JOptionPane.showMessageDialog(null, "失败");
				break;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (this.myTankA.isLive && this.myTankB.isLive) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (setString("dw")) {
					myTankA.moveDirect("dw");
					myTankA.move();
				}
				// this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				// 设置我的坦克的方向
				if (setString("up")) {
					myTankA.moveDirect("up");
					myTankA.move();
				}
				// this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (setString("lt")) {
					myTankA.moveDirect("lt");
					myTankA.move();
				}
				// this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (setString("rt")) {
					myTankA.moveDirect("rt");
					myTankA.move();
				}
				// this.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (myTankA.Bullets.size() < 2) {
					if (setString("st")) {
						myTankA.shot();
					}
				}
			}
		}
		// 必须调用this.repaint()函数，来重新绘制界面
		this.mainJ.draw();
	}

	// public static void main(String[] args) {
	// Play that = new Play();
	// Thread t = new Thread(that);
	// t.start();
	// }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean setString(String xx) {

		Socket socket = null;
		String xinfo = null;
		try {
			socket = new Socket();
			// InetSocketAddress socketAddress = new InetSocketAddress(this.ip, 9999);
			InetSocketAddress socketAddress = new InetSocketAddress(this.ip, 5004);
			System.out.println(this.ip + ":" + 5004);
			socket.connect(socketAddress, 200);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String info = xx + "B";
			System.out.println(info);
			out.writeObject(info);
			xinfo = (String) in.readObject();
			in.close();
			out.close();
			socket.close();
		} catch (Exception e1) {
			// JOptionPane.showMessageDialog(null, "未连接，无法操作");
			xinfo = "0";
		}
		if (xinfo.equals("1")) {
			// JOptionPane.showMessageDialog(null, "未连接，无法操作");
			return true;
		} else {
			return false;
		}
	}

	private String readFile(File f) {
		FileInputStream out = null;
		int r;
		String read = "";
		try {
			out = new FileInputStream(f);
			while ((r = out.read()) != -1) {
				read += (char) r;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return read;
	}
}

class mainJP extends JPanel {
	private static final long serialVersionUID = 1L;
	Play v;

	public mainJP(Play v) {
		this.v = v;
	}

	public void draw() {
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {// paint方法自带双缓冲，不要随便复写paint方法
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);
		// g.clearRect(0, 0, 800, 850);
		v.myTankA.draw(g);
		v.myTankB.draw(g);
		v.wall.draw(g);
	}
}