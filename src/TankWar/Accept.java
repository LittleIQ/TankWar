package TankWar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Accept extends Thread {
	Tank x;
	Play e;
	
	public Accept(Tank that, Play exit) {
		this.x = that;
		this.e = exit;
	}

	@Override
	public void run() {

		while (true) {
			if(acceptString()) {
				break;
			}
		}
	}

	public boolean acceptString() {
		boolean sign = false;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5003);
			String info = null;
			if (ss.isBound()) {
				Socket s = null;
				String xinfo = null;
				try {
					s = ss.accept();
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					xinfo = (String) in.readObject();
					String output = null;
					System.out.print(x);
//					if (!xinfo.substring(2).equals("")) 
					if(true){
						output = "1";
						info = xinfo.substring(0, 2);
						if (!info.equals(null)) {
							System.out.println(info);
							if(info.equals("st")) {
								x.shot();
							}
							else if(info.equals("df")){
								JOptionPane.showMessageDialog(null, "失败");
								e.exit = true;
								e.setString("wn");
								sign = true;
							}
							else if(info.equals("wn"))
							{
								sign = true;
							}
							else {
								x.moveDirect(info);
								x.move();
							}
							e.mainJ.draw();
						}
					}
//					else {
//						output = "2";
//					}
					out.writeObject(output);
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
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sign;
	}
}
