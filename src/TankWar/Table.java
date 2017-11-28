package TankWar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Table extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Button start = new Button("开始");
	int[] xx = new int[100];
	int[] yy = new int[100];
	int[] fs1 = new int[100];
	Font fs = null;
	Image offScreen = null;

	public Table() {
		super("雪花飞");
		setSize(1200, 800);
		// setBackground(Color.black);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new FlowLayout());
		add(start);
		start.addActionListener(this);
		validate();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start)
			begin();
	}

	public void begin() {
		for (int i = 0; i < xx.length; i++) {
			xx[i] = (int) (1200 * Math.random());
			yy[i] = (int) (800 * Math.random());
			fs1[i] = (int) (20 * Math.random() + 12);
		}
		new Thread() {
			public void run() {
				while (true) {
					for (int i = 0; i < xx.length; i++) {
						if ((yy[i] > 800) || (yy[i] < 0))
							yy[i] = 0;
						yy[i]++;
					}
					repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						System.err.println("Thread interrupted");
					}
				}
			}
		}.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1200, 800);
		Image img = new ImageIcon("image/RWall.jpg").getImage();
		g.drawImage(img, 0, 0, 1200, 800, null);
		for (int i = 0; i < xx.length; i++) {
			Font fs = new Font("宋体", Font.BOLD, fs1[i]);
			g.setColor(Color.white);
			g.setFont(fs);
			g.drawString("*", xx[i], yy[i]);// 画雪
			g.drawImage(img, xx[i], yy[i], 5, 5, null);
		}
	}

	public void update(Graphics g) {
		if (offScreen == null) {
			offScreen = createImage(1200, 800);
		}
		Graphics gOffScreen = offScreen.getGraphics();
		paint(gOffScreen);
		g.drawImage(offScreen, 0, 0, null);
	}

	public static void main(String args[]) {
		new Table();
	}
}