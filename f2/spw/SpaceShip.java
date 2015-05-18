package f2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 8;
        
        private BufferedImage image;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
//		g.setColor(Color.GREEN);
                try{
			image = ImageIO.read(getClass().getResourceAsStream("s2.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
               // Image img = Toolkit.getDefaultToolkit().getImage("thunder.gif");
//		g.fillRect(x, y, width, height);
                g.drawImage(image, x, y, width, height, null);
		
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
}
