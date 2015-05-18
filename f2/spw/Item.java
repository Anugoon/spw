/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Anugoon
 */
public class Item extends Sprite{
    public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 3;
	private boolean alive = true;
	
	public Item(int x, int y) {
		super(x, y, 30, 30);
		
	}

	@Override
	public void draw(Graphics2D g) {
		 Image img = Toolkit.getDefaultToolkit().getImage("fire.gif");
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
