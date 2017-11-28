package TankWar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;

import javax.swing.*;

public class MainSet extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	public String zIp;
	public MainSet m;
	public boolean stop = true;
	public String con;
	JDialog x;
	JDialog y;

	public MainSet() {
		this.m = this;
		JLabel title = new JLabel("Tank War");
		title.setBounds(70,5,200,20);
		JButton wait = new JButton("等待连接");
		wait.setBounds(25,30,150,20);
		JButton setIP = new JButton("连接他人");
		setIP.setBounds(25,60,150,20);
		this.setBounds(100,100,200,150);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(title);
		this.add(wait);
		this.add(setIP);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		wait.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("等待连接");
				x =  new JDialog();
				x.setSize(250,70);
				x.setLocationRelativeTo(null);
				x.setResizable(false);
				x.setLayout(null);
				JLabel JL1;
				JL1 = new JLabel("你的IP为："+ MainSet.getLANAddressOnWindows().toString().substring(1));
				JL1.setBounds(30,0,220,35);
				System.out.println(MainSet.getLANAddressOnWindows().toString().substring(1));
				x.add(JL1);
				JLabel JL2 = new JLabel("正在等待...");
				JL2.setBounds(30,35,220,35);
				x.add(JL2);
				x.setVisible(true);
				con = "b";
				stop = false;
				new Thread(m).start();
			}
		});
		setIP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("输入IP");
				y = new JDialog();
				y.setSize(350,78);
				y.setResizable(false);
				y.setLocationRelativeTo(null);
				y.setLayout(null);
				JLabel JL = new JLabel("IP地址:");
				JL.setBounds(10,10,50,35);
				y.add(JL);
				JTextField JT = new JTextField();
				JT.setBounds(60,10,210,35);
				y.add(JT);
				JButton JB = new JButton("确认");
				JB.setBounds(270,10,60,35);
				y.add(JB);
				y.setVisible(true);
				JB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						zIp = JT.getText();
						stop = false;
						con = "z";
						new Thread(m).start();
					}
				});
			}
		});
	}

	public static void main(String[] args) {
		new MainSet();
//		Play b = new Play("127.0.0.1");
//		new Thread(b).start();
	}

	@Override
	public void run() {
		while (!stop){
			System.out.println("run"+con);
			if(con.equals("z")){
				Socket socket;
				String xinfo = null;
				try {
					socket = new Socket();
					InetSocketAddress socketAddress = new InetSocketAddress(zIp, 9999);
//					System.out.println(zIp);
					socket.connect(socketAddress, 200);
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					String info = MainSet.getLANAddressOnWindows().toString().substring(1);
					out.writeObject(info);
					xinfo = (String) in.readObject();
					in.close();
					out.close();
					socket.close();
				} catch (Exception e1) {
					xinfo = null;
					stop = true;
				}
				if (!"".equals(xinfo) && (xinfo != null)) {
					// JOptionPane.showMessageDialog(null, "未连接，无法操作");
					y.dispose();
					m.dispose();
					stop = true;
					Play that = new Play(xinfo,"A");
					Thread t = new Thread(that);
					t.start();
				}else {
					JOptionPane.showMessageDialog(null, "无法连接");
				}
			} else if (con.equals("b")){
				String theIp = null;
				ServerSocket ss = null;
				try {
					ss = new ServerSocket(9999);
					if (ss.isBound()) {
						Socket s = null;
						try {
							s = ss.accept();
							ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
							ObjectInputStream in = new ObjectInputStream(s.getInputStream());
							theIp = (String) in.readObject();
							String output = MainSet.getLANAddressOnWindows().toString().substring(1);
							out.writeObject(output);
							System.out.println("aaa");
							out.close();
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (s != null) {
								try {
									s.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (ss != null) {
						try {
							ss.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if(!"".equals(theIp)&&(theIp!=null)) {
					System.out.println("对方ip:"+theIp);
					x.dispose();
					m.dispose();
					stop = true;
					Play that = new Play(theIp,"B");
					Thread t = new Thread(that);
					t.start();
				}
			}
		}
	}

	public static InetAddress getLANAddressOnWindows() {
		try {
			Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
			while (nifs.hasMoreElements()) {
				NetworkInterface nif = nifs.nextElement();

				if (nif.getName().startsWith("wlan")) {
					Enumeration<InetAddress> addresses = nif.getInetAddresses();

					while (addresses.hasMoreElements()) {

						InetAddress addr = addresses.nextElement();
						if (addr.getAddress().length == 4) { // 速度快于 instanceof
							return addr;
						}
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace(System.err);
		}
		return null;
	}
}
