package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	private Image imgBackground;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		imgBackground = Toolkit.getDefaultToolkit().getImage("bg.jpg");
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		big.drawImage(imgBackground, 0, 0, 400, 600,null);
	}

	public void updateGameUI(GameReporter reporter){
		//big.clearRect(0, 0, 400, 600);
		big.drawImage(imgBackground, 0, 0, 400, 600,null);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);
		big.drawString(String.format("Life: %d", reporter.getLife()), 310, 40);
        big.drawString(String.format("HP "), 290, 60);
        big.setColor(Color.YELLOW);
		big.drawRect(315, 50, (int) (100 * 0.8), 8);
		big.fillRect(315, 50, (int) (reporter.getHP() * 0.8), 8);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
