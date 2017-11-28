package TankWar;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapMake extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int screenwidth = 420;
	public static final int screenheight = 440;
	private String read = null;
	private JPanel mainJ;
	private char[] readit;
	private int tanknum = 2;
	private int homenum = 2;

	public MapMake() {
		File f = new File("map.txt");
		mainJ = new JPanel();
		mainJ.setBounds(0, 0, screenwidth, screenheight);
		this.add(mainJ);
		this.toExists(f);
		if (this.readFile(f) == null || "".equals(this.readFile(f))) {
			this.initFile(f);
		}
		read = readFile(f);
		readit = read.toCharArray();
		for (int i = 0; i < readit.length; i++) {
			if (readit[i] == '3') {
				tanknum--;
			} else if (readit[i] == '4') {
				homenum--;
			}
		}
		System.out.println(tanknum+" "+homenum);
		this.setResizable(false);
		this.setBounds(0, 0, screenwidth, screenheight);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JFrame x = this;
		this.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void windowClosing(WindowEvent we) {
				if (tanknum != 0 || homenum != 0) {
					JOptionPane.showMessageDialog(null, "地图未制作完，不能保存");
				} else {
					FileOutputStream in = null;
					try {
						in = new FileOutputStream(f);
						System.out.println(readit.length);
						in.write(String.valueOf(readit).getBytes());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					x.dispose();
					System.exit(0);
				}
			}
		});
		this.setLayout(null);
		this.setVisible(true);
		this.mainJ.paintComponents(this.mainJ.getGraphics());
		this.mainJ.setBackground(Color.BLACK);
		for (int i = 0; i < read.length(); i++) {
			if (readit[i] == '1') {
				this.drawR(i);
			} else if (readit[i] == '2') {
				this.drawY(i);
			} else if (readit[i] == '3') {
				this.drawT(i);
			} else if (readit[i] == '4') {
				this.drawH(i);
			}
		}
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
					if (e.getClickCount() == 2) {
						int x = e.getX() / 20;
						int y = (e.getY() - 30) / 20;
						System.out.println(y * 20 + x);
						if (readit[y * 20 + x] == '3') {
							tanknum++;
						}
						if (readit[y * 20 + x] == '4') {
							homenum++;
						}
						readit[y * 20 + x] = '0';
						x = x * 20;
						y = y * 20;
						System.out.println(x + " " + y);
						mainJ.getGraphics().setColor(Color.BLACK);
						mainJ.getGraphics().fillRect(x, y, 20, 20);
						// mainJ.repaint();
					} else {
						int x = e.getX() / 20;
						int y = (e.getY() - 30) / 20;
						System.out.println(y * 20 + x);
						if (readit[y * 20 + x] == '3') {
							tanknum++;
						}
						if (readit[y * 20 + x] == '4') {
							homenum++;
						}
						readit[y * 20 + x] = '1';
						x = x * 20;
						y = y * 20;
						System.out.println(x + " " + y);
						Image imag = new ImageIcon("image/RWall.jpg").getImage();
						mainJ.getGraphics().drawImage(imag, x, y, 20, 20, null);
						// mainJ.repaint();
					}
				}
				if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
					int x = e.getX() / 20;
					int y = (e.getY() - 30) / 20;
					System.out.println(y * 20 + x);
					if (readit[y * 20 + x] == '3') {
						tanknum++;
					}
					if (readit[y * 20 + x] == '4') {
						homenum++;
					}
					if (tanknum != 0) {
						readit[y * 20 + x] = '3';
						tanknum--;
						x = x * 20;
						y = y * 20;
						System.out.println(x + " " + y);
						Image imag = new ImageIcon("image/tankup.jpg").getImage();
						mainJ.getGraphics().drawImage(imag, x, y, 20, 20, null);
					} else {
						JOptionPane.showMessageDialog(null, "坦克数量已达上限");
					}
				}
				if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
					if (e.getClickCount() == 2) {
						int x = e.getX() / 20;
						int y = (e.getY() - 30) / 20;
						System.out.println(y * 20 + x);
						if (readit[y * 20 + x] == '3') {
							tanknum++;
						}
						if (readit[y * 20 + x] == '4') {
							homenum++;
						}
						if (homenum != 0) {
							readit[y * 20 + x] = '4';
							homenum--;
							x = x * 20;
							y = y * 20;
							System.out.println(x + " " + y);
							Image imag = new ImageIcon("image/Home.jpg").getImage();
							mainJ.getGraphics().drawImage(imag, x, y, 20, 20, null);
							// mainJ.repaint();
						} else {
							JOptionPane.showMessageDialog(null, "基地数量已达上限");
						}
					} else {
						int x = e.getX() / 20;
						int y = (e.getY() - 30) / 20;
						System.out.println(y * 20 + x);
						if (readit[y * 20 + x] == '3') {
							tanknum++;
						}
						if (readit[y * 20 + x] == '4') {
							homenum++;
						}
						readit[y * 20 + x] = '2';
						x = x * 20;
						y = y * 20;
						System.out.println(x + " " + y);
						Image imag = new ImageIcon("image/YWall.jpg").getImage();
						mainJ.getGraphics().drawImage(imag, x, y, 20, 20, null);
					}
				}
				System.out.println(tanknum+" "+homenum);
			}
		});
	}

	public void run() {
		while (true) {

			char[] readit = read.toCharArray();
			for (int i = 0; i < read.length(); i++) {
				if (readit[i] == '1') {
					this.drawR(i);
				} else if (readit[i] == '2') {
					this.drawY(i);
				}
			}
			this.repaint();
			System.out.println("draw");
		}
	}

	public static void main(String[] args) {
//		MapMake m = 
		new MapMake();
	}

	private void toExists(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public void initFile(File f) {
		FileOutputStream in = null;
		try {
			in = new FileOutputStream(f);
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					if ((i == 15 && j == 12) || (i == 4 && j == 8)) {
						in.write("3".getBytes());
					} else if ((i == 19 && j == 1) || (i == 1 && j == 19)) {
						in.write("4".getBytes());
					} else {
						in.write("0".getBytes());
					}
				}
				// in.write("\n".getBytes());
			}
			System.out.println(tanknum + " " + homenum);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void drawR(int num) {
		Image imag = new ImageIcon("image/RWall.jpg").getImage();
		this.mainJ.getGraphics().drawImage(imag, (int) (num % 20) * 20, (int) (num / 20) * 20, 20, 20, null);
		System.out.println((int) (num % 20) * 20 + " " + (int) (num / 20) * 20);
		// this.repaint();
	}

	private void drawY(int num) {
		Image imag = new ImageIcon("image/YWall.jpg").getImage();
		this.mainJ.getGraphics().drawImage(imag, (int) (num % 20) * 20, (int) (num / 20) * 20, 20, 20, null);
		System.out.println((int) (num % 20) * 20 + " " + (int) (num / 20) * 20);
		// this.repaint();
	}

	private void drawT(int num) {
		Image imag = new ImageIcon("image/tankup.jpg").getImage();
		this.mainJ.getGraphics().drawImage(imag, (int) (num % 20) * 20, (int) (num / 20) * 20, 20, 20, null);
		System.out.println((int) (num % 20) * 20 + " " + (int) (num / 20) * 20);
		// this.repaint();
	}

	private void drawH(int num) {
		Image imag = new ImageIcon("image/Home.jpg").getImage();
		this.mainJ.getGraphics().drawImage(imag, (int) (num % 20) * 20, (int) (num / 20) * 20, 20, 20, null);
		System.out.println((int) (num % 20) * 20 + " " + (int) (num / 20) * 20);
		// this.repaint();
	}
}
