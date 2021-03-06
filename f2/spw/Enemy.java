package f2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 5;
	private boolean alive = true;
	
	public Enemy(int x, int y) {
		super(x, y, 30, 30);
		
	}

	@Override
	public void draw(Graphics2D g) {
		 Image img = Toolkit.getDefaultToolkit().getImage("enemy.png");
                g.drawImage(img, x, y, width, height, null);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
        public boolean destroy(){
            alive = false;
            return alive;
        }
}