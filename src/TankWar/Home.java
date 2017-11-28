package TankWar;

import javax.swing.*;
import java.awt.*;

public class Home {
    public boolean isLive;
    private int width = 20;
    private int height = 20;
    private int posX;
    private int posY;
    private Tank mainT;
    public Home(int x, int y, Tank t){
        this.posX = x;
        this.posY = y;
        this.mainT = t;
        this.isLive = true;
    }

    public void HomeDraw(Graphics g) {
        Image img1 = new ImageIcon("image/Home.jpg").getImage();
        g.drawImage(img1, this.posX, this.posY, this.width, this.height, this.mainT.v);
    }

    public Rectangle getRect() {
        return new Rectangle(posX, posY, width, height);
    }
}
